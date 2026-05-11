package orm;

import orm.Annotations.Column;
import orm.Annotations.Entity;
import orm.Annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager <E> implements  DataBaseContext <E>{
    private final Connection connection;
    private static  final String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s);";
    private static  final String  UPDATE_WITH_WHERE_TEMPLATE = "UPDATE %s SET %s WHERE %s;";
    private static  final String SELECT_WITH_WHERE_PLACEHOLDER_TEMPLATE = "SELECT %s FROM %s %s;";
    private static  final  String CREATE_TABLE_TEMPLATE = "CREATE TABLE %s (%s);";
    private static  final  String ALTER_TABLE_TEMPLATE = "ALTER TABLE %s  %s;";
    private static  final  String SELECT_ALL_EXISTING_COLUMNS_TEMPLATE = "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'mini_orm' AND TABLE_NAME = '%s'";
    private static  final  String DELETE_ROW_TEMPLATE= "DELETE FROM %s  WHERE id = ?";
    public  EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
         Field idColumn = getIdColumn(entity);

         idColumn.setAccessible(true);
         Object idValue = idColumn.get(entity);

         if(idValue == null || (long)idValue == 0) {
             return doInsert(entity);
         }

        return doUpdate(entity,idColumn,idValue);

    }



    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return baseFind(table,null,null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return baseFind(table, where,null);
    }




    @Override
    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<E> result = baseFind(table, null, 1);
        if(result.isEmpty()) return null;
        return result.getFirst();
    }

    @Override
    public  E findFirst(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<E> result = baseFind(table, where, 1);
        if(result.isEmpty()) return null;
        return result.getFirst();
    }

    @Override
    public void doCreate(Class<E> table) throws SQLException {
        String tableName = getTableName(table);
        String formatted = CREATE_TABLE_TEMPLATE.formatted(tableName, getAllFieldsAndDataTypes(table));
        PreparedStatement preparedStatement = connection.prepareStatement(formatted);
        preparedStatement.execute();


    }

    @Override
    public void doAlter(E entity) throws SQLException {
        String newColumns=getColumnsNotExistingInTable(entity);
        String tabledName = getTableName(entity);
        String format= ALTER_TABLE_TEMPLATE.formatted(tabledName, getColumnsNotExistingInTable(entity));
        PreparedStatement preparedStatement = connection.prepareStatement(format);
        preparedStatement.execute();

    }

    @Override
    public boolean doDelete(E entity) throws Exception {
        String tableName = getTableName(entity);
        String formatted = DELETE_ROW_TEMPLATE.formatted(tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(formatted);
        long id = getEntityId(entity);
        preparedStatement.setLong(1,id);
        return preparedStatement.execute();



    }

    private long getEntityId(E entity) throws Exception {
       // Field field = Arrays.stream(entity.getClass().getDeclaredFields()).filter(f -> f.isAnnotationPresent(Id.class)).findFirst().get();
        return  Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .map(field -> getFieldValue(field, entity))
                .orElseThrow(() -> new Exception("No @Id field found"));




    }
    private long getFieldValue(Field field, Object entity) throws RuntimeException {
        try {
            field.setAccessible(true);
            return field.getLong(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private String getColumnsNotExistingInTable(E entity) throws SQLException {
        List<String> existingColumnsList = getExistingColumnsList(entity);
        return  Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> (!existingColumnsList.contains(f.getAnnotation(Column.class).name())))
                .map(f -> String.format("ADD COLUMN %s %s",getFieldName(f),getFieldType(f)))
                 .collect(Collectors.joining(", "));


    }

    private List<String> getExistingColumnsList(E entity) throws SQLException {
        List<String> result=new ArrayList<>();
        String tableName = getTableName(entity);
        String statement= SELECT_ALL_EXISTING_COLUMNS_TEMPLATE.formatted(tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
         result.add(resultSet.getString(1));

        }
        return result;
    }

    private String getAllFieldsAndDataTypes(Class<E> table) {
        List <String> result = new ArrayList<>();
        Field[] fields = table.getDeclaredFields();

        for (Field field : fields) {

            String format = String.format("%s %s", getFieldName(field), getFieldType(field));
            if (field.isAnnotationPresent(Id.class)){
               format = format + " PRIMARY KEY AUTO_INCREMENT";
            }

            result.add(format);

        }

        return String.join(", ",result);
    }

    private String getFieldName(Field field) {
        field.setAccessible(true);
       return field.getAnnotation(Column.class).name();
    }
    private String getFieldType(Field field) {
        field.setAccessible(true);

       return switch (field.getType().getSimpleName()){
            case "int","Integer"-> "INT";
            case "long","Long" -> "BIGINT";
            case "String" -> "VARCHAR(255)";
            case "double", "Double" -> "DOUBLE";
            case "LocalDate" -> "DATE";

           default -> throw new IllegalStateException("Unexpected value: " + field.getType().getSimpleName());
       };
    }



    private List<E> baseFind(Class<E> table, String where,Integer limit) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String fieldList = "*";
        String tableName = getTableName(table);
        String whereClause = (where == null ? "" : "WHERE " + where) ;
        String limitClause = (limit == null ? "" : " LIMIT " + limit);


        String selectStatement = String.format(SELECT_WITH_WHERE_PLACEHOLDER_TEMPLATE, fieldList, tableName, whereClause + limitClause);

        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery();

        List <E> result = new ArrayList<>();

        while (resultSet.next()){
            E  current = generateEntity(table,resultSet);
            result.add(current);
        }

        return result;
    }
    private E generateEntity(Class<E> table, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        // Create Object
        E result = table.getDeclaredConstructor().newInstance(); // new User(); new E();
        // Fill Object with Data
        Field[] fields = table.getDeclaredFields();

        for (Field field : fields) {
            fillField(result,field,resultSet);
        }
        return result;
    }

    private void fillField(E result, Field field, ResultSet resultSet) throws SQLException, IllegalAccessException {
        field.setAccessible(true);

        String dbFieldName = field.getAnnotation(Column.class).name(); //name of the field
        //dbFieldType
        Class<?> javaType = field.getType(); //Generic type of the field

        //Checking for the appropriate generic type
        if (javaType == int.class || javaType == Integer.class){
            int value = resultSet.getInt(dbFieldName);
            field.setInt(result,value);
            return;
        } else if (javaType == long.class){
            long value = resultSet.getLong(dbFieldName);
            field.setLong(result,value);
            return;
        } else if (javaType == String.class){
            String value = resultSet.getString(dbFieldName);
            field.set(result,value);
            return;
        } else if (javaType == LocalDate.class){
            LocalDate value = resultSet.getObject(dbFieldName,LocalDate.class);
            field.set(result,value);
            return;
        }else if (javaType == double.class) {
            double value = resultSet.getDouble(dbFieldName);
            field.set(result, value);
            return;
        }
        throw new RuntimeException("Unsupported type "+ javaType);

    }

    private boolean doInsert(E entity) throws IllegalAccessException, SQLException {

        //Generate insert
        //Get table name
        //Collect columns without id
        //Collect values without id
        String tableName = getTableName(entity);

        List<String> columnList = getColumnsWithoutId(entity);

        List<String> columnValuesList = getColumnValuesWithoutId(entity);

        String formatedInsert= String.format(INSERT_TEMPLATE,tableName,
                String.join(",",columnList),
                String.join(",",columnValuesList) );


        //execute
        PreparedStatement preparedStatement = connection.prepareStatement(formatedInsert);
        int changedRows = preparedStatement.executeUpdate();

        //parse the result
        return changedRows == 1;
    }
    private boolean doUpdate(E entity, Field idColumn, Object idValue) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);
        List<String> columns = getColumnsWithoutId(entity);
        List<String> values= getColumnValuesWithoutId(entity);

        List <String> columnsWithValues = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {

           String s = columns.get(i) + "=" + values.get(i);

           columnsWithValues.add(s);
        }

        String idCondition = String.format("%s = %s",idColumn.getName(),idValue.toString());


        String updateQuery = String.format(UPDATE_WITH_WHERE_TEMPLATE,
                tableName,
                String.join(",", columnsWithValues),
                idCondition+";");

        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        int updatedColumns = preparedStatement.executeUpdate();


        return  updatedColumns == 1;
    }

    private String  getTableName(E entity) {
        Entity annotation = entity.getClass().getAnnotation(Entity.class);

        if (annotation == null) throw new RuntimeException("No Entity annotation present");

       return annotation.name();
    }
    private String getTableName(Class <E> clazz){
        Entity annotation = clazz.getAnnotation(Entity.class);

        if (annotation == null) throw new RuntimeException("No Entity annotation present");

        return annotation.name();


    }

    private List<String> getColumnsWithoutId(E entity) {
        List <String> result = new ArrayList<>();
        Field[] declaredField = entity.getClass().getDeclaredFields();

        for (Field field : declaredField) {
            if(field.isAnnotationPresent(Id.class)) continue;

            Column column = field.getAnnotation(Column.class);
            if(column == null) continue;

            result.add(column.name());
        }

       return result;
    }

    private List<String> getColumnValuesWithoutId(E entity) throws IllegalAccessException {
        List <String> result = new ArrayList<>();

        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {

            if(field.isAnnotationPresent(Id.class)) continue;

            if(!field.isAnnotationPresent(Column.class)) continue;

            field.setAccessible(true);
            Object fieldValue = field.get(entity);

            result.add("'" + fieldValue.toString() + "'");
        }

        return result;
    }

    private Field getIdColumn(E entity) {
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {

            if(field.isAnnotationPresent(Id.class)) return field;

        }
        throw  new RuntimeException("Entity has no Id column");

    }

}
