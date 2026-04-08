package soft_uni.jsonprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soft_uni.jsonprocessing.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT u FROM User AS u " +
            "JOIN FETCH u.soldProducts AS sp " +
            "JOIN sp.buyer AS b " +
            "WHERE b IS NOT NULL " +
            "ORDER BY u.lastName,u.firstName")

    List<User> findWithSoldProductsOrderByLastName();

     @Query("SELECT u FROM User AS u " +
            "JOIN FETCH u.soldProducts AS sp " +
             "GROUP BY u.id,sp.id " +
            "HAVING count(sp) > 0 " +
            "ORDER BY count(sp) DESC, u.lastName")
    List<User> findAllWithListedProducts();
}
