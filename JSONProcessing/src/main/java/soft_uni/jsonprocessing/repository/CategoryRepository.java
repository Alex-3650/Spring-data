package soft_uni.jsonprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soft_uni.jsonprocessing.dtos.CategoryStatsDto;
import soft_uni.jsonprocessing.entities.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query( "SELECT new soft_uni.jsonprocessing.dtos.CategoryStatsDto" +
            "(c.name, count(cp), avg(cp.price), sum(cp.price)) " +
            "FROM Category AS c " +
            "JOIN c.products AS cp " +
            "GROUP BY c.id, c.name " +
            "ORDER BY count(cp)"
    )
    List<CategoryStatsDto> findCategoryStats();
}
