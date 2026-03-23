package soft_uni.booksystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.booksystem.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
