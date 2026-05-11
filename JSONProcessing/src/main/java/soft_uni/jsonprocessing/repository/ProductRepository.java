package soft_uni.jsonprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soft_uni.jsonprocessing.entities.Product;
import soft_uni.jsonprocessing.entities.User;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal min, BigDecimal max);

    List<Product> findBySellerAndBuyerIsNotNull(User seller);
}
