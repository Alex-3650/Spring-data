package soft_uni.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import soft_uni.mvc.dto.CompanyDto;
import soft_uni.mvc.dto.ProjectDto;
import soft_uni.mvc.services.company.ImportCompanyService;
import soft_uni.mvc.services.project.ImportProjectService;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Controller
public class ImportController {

   private final ImportCompanyService importCompanyService;
   private final ImportProjectService importProjectService;

    public ImportController(ImportCompanyService importCompanyService, ImportProjectService importProjectService) {
        this.importCompanyService = importCompanyService;
        this.importProjectService = importProjectService;
    }

    @GetMapping("import/xml")
    public ModelAndView importHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("xml/import-xml");
        boolean[] areImported = {
                this.importCompanyService.isDataImported(),
                this.importProjectService.isDataImported(),
                false};

        modelAndView.addObject("areImported", areImported);

        return modelAndView;
    }

    @GetMapping("import/companies")
    public ModelAndView  importCompaniesView() throws IOException {
        String companiesXmlContent = importCompanyService.getXmlFile();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("xml/import-companies");
        modelAndView.addObject("companies", companiesXmlContent);

        return modelAndView;
    }
    @PostMapping("import/companies")
    public String  importCompanies(RedirectAttributes attributes) throws JAXBException, IOException {
        List<CompanyDto> importedCompanies = List.of();

        try {
            importedCompanies = importCompanyService.importCompaniesData();
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }

        String message = importedCompanies.isEmpty()
                ? "No companies were imported!"
                : "Imported %d companies!".formatted(importedCompanies.size());

        attributes.addFlashAttribute("message", message);
        return "redirect:/import/xml";
    }
    @GetMapping("import/projects")
    public ModelAndView  importProjectsView() throws IOException {
        String companiesXmlContent = importProjectService.getXmlFile();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("xml/import-projects");
        modelAndView.addObject("projects", companiesXmlContent);

        return modelAndView;
    }
    @PostMapping("import/projects")
    public String  importProjects(RedirectAttributes attributes) throws JAXBException, IOException {
          List<ProjectDto> projects = this.importProjectService.importProjectsData();

          String message = projects.isEmpty() ? "No projects were imported!":"Imported %d projects!".formatted(projects.size());


        attributes.addFlashAttribute("message", message);
        return "redirect:/import/xml";
    }

}
