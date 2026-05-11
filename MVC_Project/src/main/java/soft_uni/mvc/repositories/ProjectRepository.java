package soft_uni.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soft_uni.mvc.entities.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
