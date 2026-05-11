package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DataBaseContext <E> {

    boolean persist(E entity) throws SQLException, IllegalAccessException;
    Iterable<E> find(Class <E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    Iterable<E> find(Class <E> table,String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    E findFirst(Class<E> table,String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    void doCreate(Class<E> table) throws SQLException;
    void doAlter(E entity) throws SQLException;
    boolean doDelete(E entity) throws Exception;
}
