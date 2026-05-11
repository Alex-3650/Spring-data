package soft_uni.mvc.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import soft_uni.mvc.dto.UserLoginDto;

@Controller
public class UserController {


    @GetMapping("users/login")
    public String loginView(){
        return "user/login";
    }

    @PostMapping("users/login")
    public String doLogin(@Valid UserLoginDto userLoginDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "user/login";
        }
        return  "redirect:/home";
    }
}
