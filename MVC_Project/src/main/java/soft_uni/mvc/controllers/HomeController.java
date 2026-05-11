package soft_uni.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import soft_uni.mvc.services.company.ImportCompanyService;

@Controller
public class HomeController {

    private final ImportCompanyService importCompanyService;

    @Autowired
    public HomeController(ImportCompanyService importCompanyService) {
        this.importCompanyService = importCompanyService;
    }

    @GetMapping("/home")
    public ModelAndView indexPage() {

        boolean isImported = importCompanyService.isDataImported();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("areImported", isImported);
        return modelAndView;
    }

}
