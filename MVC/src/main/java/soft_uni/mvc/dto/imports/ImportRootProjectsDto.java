package soft_uni.mvc.dto.imports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "projects")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportRootProjectsDto {

    @XmlElement(name = "project")
    private List<ImportProjectDto> projects;

    public ImportRootProjectsDto() {
        projects = new ArrayList<>();
    }

    public List<ImportProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ImportProjectDto> projects) {
        this.projects = projects;
    }
}
