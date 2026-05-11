package soft_uni.mvc.services.project;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import soft_uni.mvc.dto.CompanyDto;
import soft_uni.mvc.dto.ProjectDto;
import soft_uni.mvc.dto.ProjectInputDto;
import soft_uni.mvc.dto.ProjectRelationsDto;
import soft_uni.mvc.dto.imports.CompanyNameDto;
import soft_uni.mvc.dto.imports.ImportProjectDto;
import soft_uni.mvc.dto.imports.ImportRootProjectsDto;
import soft_uni.mvc.services.company.CompanyService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImportProjectServiceImpl implements ImportProjectService {
    public static final Path IMPORT_DATA_PATH = Path.of("src/main/resources/files/xmls/projects.xml");

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;
    private final Unmarshaller importParser;

    public ImportProjectServiceImpl(CompanyService companyService, ProjectService projectService, ModelMapper modelMapper) throws JAXBException {
        this.companyService = companyService;
        this.projectService = projectService;
        this.modelMapper = modelMapper;
        JAXBContext jaxbContext = JAXBContext.newInstance(ImportRootProjectsDto.class);
        this.importParser = jaxbContext.createUnmarshaller();
    }

    @Override
    public boolean isDataImported() {
        return this.projectService.count() > 0;
    }

    @Override
    public String getXmlFile() throws IOException {
            List<String> lines = Files.readAllLines(IMPORT_DATA_PATH);
            return String.join("\n", lines);

    }

    @Override
    public List<ProjectDto> importProjectsData() throws IOException, JAXBException {
        ImportRootProjectsDto parsed = (ImportRootProjectsDto) this.importParser.unmarshal(Files.newInputStream(IMPORT_DATA_PATH));
        List<ImportProjectDto> projects = parsed.getProjects();
        List<CompanyDto> allReferencedCompanies = this.companyService.findManyByName(projects.stream().map(p->p.getCompany().getName()).collect(Collectors.toList()));
        Map<String,CompanyDto> companiesMap=new HashMap<>();

        for (CompanyDto allReferencedCompany : allReferencedCompanies) {
            companiesMap.put(allReferencedCompany.getName(), allReferencedCompany);
        }

        List<ProjectDto> result = new ArrayList<>();
        for (ImportProjectDto importDto : projects) {
            try {
                ProjectInputDto inputDto = modelMapper.map(importDto, ProjectInputDto.class);
                ProjectRelationsDto relationsDto = new ProjectRelationsDto();
                String companyName = importDto.getCompany().getName();
                CompanyDto referencedCompany = companiesMap.get(companyName);

                if (referencedCompany == null) {
                    continue;  // skip this project — company doesn't exist in DB
                }

                relationsDto.setCompanyId(referencedCompany.getId());
                ProjectDto projectDto = this.projectService.create(inputDto, relationsDto);
                result.add(projectDto);
            } catch (Exception e) {
                //Swallow all exceptions
            }

        }
        return result;
    }
}

