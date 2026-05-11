package soft_uni.gameservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;
import org.springframework.stereotype.Repository;
import soft_uni.gameservice.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
