package soft_uni.mvc.services.project;

import soft_uni.mvc.dto.ProjectDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface ImportProjectService  {
    boolean isDataImported();

    String getXmlFile() throws IOException;

    List<ProjectDto> importProjectsData() throws IOException, JAXBException;

}
