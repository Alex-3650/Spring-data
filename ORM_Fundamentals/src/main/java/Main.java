import Entities.Product;
import Entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {

       MyConnector.createConnection("root","alex2007@1234","mini_orm");
        Connection connection = MyConnector.getConnection();

        EntityManager <User> entityManager = new EntityManager<>(connection);
        User ivan = new User("Pesho", 34, LocalDate.now());
        ivan.setId(1);
        entityManager.persist(ivan);

        Iterable<User> users = entityManager.find(User.class, "age > 20");
        System.out.println();

        User first = entityManager.findFirst(User.class);
        System.out.println();


//        Product pen=new Product("pen",2.35);
//        EntityManager <Product> productManager = new EntityManager<>(connection);
//        productManager.persist(pen);


    }
}
