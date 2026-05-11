package soft_uni.mvc.services.company;

import soft_uni.mvc.dto.CompanyDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface ImportCompanyService {

    boolean isDataImported();

    String getXmlFile() throws IOException;

    List<CompanyDto> importCompaniesData() throws IOException, JAXBException;


}
