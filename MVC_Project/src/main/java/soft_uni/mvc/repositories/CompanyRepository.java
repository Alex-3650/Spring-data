package soft_uni.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import soft_uni.mvc.dto.CompanyDto;
import soft_uni.mvc.entities.Company;

import java.util.Collection;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findAllByNameIn(Collection<String> names);

}
