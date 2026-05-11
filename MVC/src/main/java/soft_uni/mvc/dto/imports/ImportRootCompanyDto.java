package soft_uni.mvc.dto.imports;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "companies")
public class ImportRootCompanyDto {

    @XmlElement(name = "company")
    private List<ImportCompanyDto> companies;

    public ImportRootCompanyDto() {
    }

    public List<ImportCompanyDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<ImportCompanyDto> companies) {
        this.companies = companies;
    }
}
