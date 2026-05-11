package soft_uni.mvc.services.project;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import soft_uni.mvc.dto.CompanyDto;
import soft_uni.mvc.dto.ProjectDto;
import soft_uni.mvc.dto.ProjectInputDto;
import soft_uni.mvc.dto.ProjectRelationsDto;

import java.util.List;

@Validated
public interface ProjectService {

    ProjectDto create(@NotNull @Valid ProjectInputDto projectInputDto,@NotNull @Valid ProjectRelationsDto projectRelationsDto);

    long count();

}
