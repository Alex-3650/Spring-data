import Entities.Product;
import Entities.User;
import orm.EntityManager;
import orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {

       MyConnector.createConnection("root","your pass","mini_orm");
        Connection connection = MyConnector.getConnection();

//        EntityManager <User> entityManager = new EntityManager<>(connection);

//        ivan.setId(1);
//        entityManager.persist(ivan);
//
//
//        Iterable<User> users = entityManager.find(User.class, "age > 20");
//        System.out.println();
//
//        User first = entityManager.findFirst(User.class);
//        System.out.println();
//
//        EntityManager<Product> product = new EntityManager<>(connection);
//         product.doCreate(Product.class);
        EntityManager <User> entityManager = new EntityManager<>(connection);
        User user = new User("Mitko",23,LocalDate.now(),"ivan@abv.bg",2300,"Bulgaria");
        entityManager.persist(user);
        User first = entityManager.findFirst(User.class, "username = 'Mitko'");
        System.out.println();
        entityManager.doDelete(first);

//        Product pen=new Product("pen",2.35);
//        EntityManager <Product> productManager = new EntityManager<>(connection);
//        productManager.persist(pen);


    }
}
