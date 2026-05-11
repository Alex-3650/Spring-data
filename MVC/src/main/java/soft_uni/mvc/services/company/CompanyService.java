package soft_uni.mvc.services.company;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import soft_uni.mvc.dto.CompanyDto;
import soft_uni.mvc.dto.InputCompanyDto;
import soft_uni.mvc.entities.Company;

import java.util.Collection;
import java.util.List;

@Validated
public interface CompanyService {

    CompanyDto create(@NotNull @Valid InputCompanyDto company);
    long count();
    Company getReferenceById(long id);

    List<CompanyDto> findManyByName(Collection<String> names);
}
