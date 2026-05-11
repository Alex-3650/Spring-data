package soft_uni.gameservice.services;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import soft_uni.gameservice.dtos.User.UserDto;
import soft_uni.gameservice.dtos.User.UserLoginDto;
import soft_uni.gameservice.dtos.User.UserRegisterDto;
import soft_uni.gameservice.entities.User;

@Validated
public interface UserService {

    UserDto ensureAdmin(@Valid UserRegisterDto userRegisterDto);
    UserDto register(@Valid UserRegisterDto registerDto);
    UserDto login(@Valid UserLoginDto userLoginDto);
    User getRequired(@Valid Long id);
}
