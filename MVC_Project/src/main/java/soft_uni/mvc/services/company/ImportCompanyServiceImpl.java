package soft_uni.mvc.services.company;

import jakarta.validation.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import soft_uni.mvc.dto.CompanyDto;
import soft_uni.mvc.dto.InputCompanyDto;
import soft_uni.mvc.dto.imports.ImportCompanyDto;
import soft_uni.mvc.dto.imports.ImportRootCompanyDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Validated
@Service
public class ImportCompanyServiceImpl implements ImportCompanyService {

    public static final Path IMPORT_DATA_PATH = Path.of("src/main/resources/files/xmls/companies.xml");
    private final CompanyService companyService;

    private final Unmarshaller importParser;
    private final ModelMapper modelMapper;

    public ImportCompanyServiceImpl(CompanyService companyService, ModelMapper modelMapper) throws JAXBException {
        this.companyService = companyService;
        this.modelMapper = modelMapper;
        JAXBContext jaxbContext = JAXBContext.newInstance(ImportRootCompanyDto.class);
        this.importParser = jaxbContext.createUnmarshaller();
    }

    public boolean isDataImported(){
        return this.companyService.count() > 0;
    }

    public String getXmlFile() throws IOException {
        List<String> lines = Files.readAllLines(IMPORT_DATA_PATH);
        return String.join("\n", lines);
    }

    @Override
    public List<CompanyDto> importCompaniesData() throws IOException, JAXBException {
        ImportRootCompanyDto parsed = (ImportRootCompanyDto) this.importParser.unmarshal(Files.newInputStream(IMPORT_DATA_PATH));
        List<ImportCompanyDto> toImport = parsed.getCompanies();

        List<CompanyDto> resul = new ArrayList<>();

        for (ImportCompanyDto companyImportDto : toImport){
            try {
                InputCompanyDto inputCompany = this.modelMapper.map(companyImportDto, InputCompanyDto.class);
                CompanyDto dto = this.companyService.create(inputCompany);
                resul.add(dto);
            } catch (ConstraintViolationException e) {

            }
        }
        return resul;
    }

}
