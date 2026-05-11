package soft_uni.mvc.dto.imports;

import soft_uni.mvc.configuration.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportProjectDto {

        @XmlElement(name = "name")
        private String name;

        @XmlElement(name = "description")
        private String description;

        @XmlElement(name = "start-date")
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;

        @XmlElement(name = "is-finished")
        private Boolean isFinished;

        @XmlElement(name = "payment")
        private BigDecimal payment;

        @XmlElement(name = "company")
        private CompanyNameDto company;

    public ImportProjectDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

   public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public CompanyNameDto getCompany() {
        return company;
    }

    public void setCompany(CompanyNameDto company) {
        this.company = company;
    }
}
