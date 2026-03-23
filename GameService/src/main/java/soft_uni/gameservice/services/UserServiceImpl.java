package soft_uni.gameservice.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import soft_uni.gameservice.dtos.User.UserDto;
import soft_uni.gameservice.dtos.User.UserLoginDto;
import soft_uni.gameservice.dtos.User.UserRegisterDto;
import soft_uni.gameservice.entities.User;
import soft_uni.gameservice.repositories.UserRepository;



@Service
@Validated
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDto ensureAdmin(UserRegisterDto userRegisterDto) {
        User admin = modelMapper.map(userRegisterDto, User.class);
        admin.setAdmin(true);
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        try {
            this.userRepository.save(admin);
            return this.modelMapper.map(admin, UserDto.class)   ;
        } catch (DataIntegrityViolationException e) {
            return null;
        }

    }

    @Override
    public UserDto register(UserRegisterDto registerDto) {

        User user = modelMapper.map(registerDto, User.class);
        user.setAdmin(false);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return modelMapper.map(user, UserDto.class);

    }

    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        User user = this.userRepository.findByEmail(userLoginDto.getEmail());
        if (user == null) return null;

        if (!bCryptPasswordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return null;
        }

        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getRequired(Long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
