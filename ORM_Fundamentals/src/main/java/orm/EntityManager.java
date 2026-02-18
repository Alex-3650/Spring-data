package orm;

import com.mysql.cj.x.protobuf.MysqlxCrud;
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
import java.util.Date;
import java.util.List;

public class EntityManager <E> implements  DataBaseContext <E>{
    private final Connection connection;
    private static  final String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s);";
    private  static  final String  UPDATE_WITH_WHERE_TEMPLATE = "UPDATE %s SET %s WHERE %s";
    private static  final String SELECT_WITH_WHERE_PLACEHOLDER_TEMPLATE = "SELECT %s FROM %s %s";
    public  EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
         Field idColumn = getIdColum(entity);

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

        String dbFieldName = field.getAnnotation(Column.class).name();
        //dbFieldType
        Class<?> javaType = field.getType();


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

    private String getTableName(E entity) {
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

    private Field getIdColum(E entity) {
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {

            if(field.isAnnotationPresent(Id.class)) return field;

        }

        throw  new RuntimeException("Entity has no Id column");

    }

}
