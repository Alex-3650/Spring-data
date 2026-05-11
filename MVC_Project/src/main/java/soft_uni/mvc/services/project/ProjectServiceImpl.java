package soft_uni.mvc.services.project;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import soft_uni.mvc.dto.CompanyDto;
import soft_uni.mvc.dto.ProjectDto;
import soft_uni.mvc.dto.ProjectInputDto;
import soft_uni.mvc.dto.ProjectRelationsDto;
import soft_uni.mvc.entities.Company;
import soft_uni.mvc.entities.Project;
import soft_uni.mvc.repositories.ProjectRepository;
import soft_uni.mvc.services.company.CompanyService;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
     private final CompanyService companyService;
     private final ProjectRepository projectRepository;
     private final ModelMapper modelMapper;

    public ProjectServiceImpl(CompanyService companyService, ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.companyService = companyService;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ProjectDto create( ProjectInputDto projectInputDto, ProjectRelationsDto projectRelationsDto) {

        Project project = modelMapper.map(projectInputDto, Project.class);
        Company company = this.companyService.getReferenceById(projectRelationsDto.getCompanyId());
        project.setCompany(company);

        this.projectRepository.save(project);

        return this.modelMapper.map(project, ProjectDto.class);
    }

    @Override
    public long count() {
        return this.projectRepository.count();
    }

}
