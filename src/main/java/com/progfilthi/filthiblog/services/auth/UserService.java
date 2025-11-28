package com.progfilthi.filthiblog.services.auth;

import com.progfilthi.filthiblog.enums.Roles;
import com.progfilthi.filthiblog.globalExceptionHandler.ResourceConflictException;
import com.progfilthi.filthiblog.globalExceptionHandler.ResourceNotFoundException;
import com.progfilthi.filthiblog.mappers.IUserMapper;
import com.progfilthi.filthiblog.models.User;
import com.progfilthi.filthiblog.models.dto.auth.AuthResponseDto;
import com.progfilthi.filthiblog.models.dto.user.CreateUserDto;
import com.progfilthi.filthiblog.models.dto.user.LoginUserDto;
import com.progfilthi.filthiblog.models.dto.user.UserResponseDto;
import com.progfilthi.filthiblog.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {


    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserMapper userMapper;
    private final JwtService jwtService;

    @Transactional
    public AuthResponseDto registerUser(CreateUserDto dto){
        if(userRepository.existsByEmail(dto.email())){
            throw new ResourceConflictException("Email already exists");
        }

        User user = userMapper.toUserEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.password()));

        User savedUser = userRepository.save(user);

        return getUserResponseDto(savedUser);


    }


    @Transactional
    public AuthResponseDto loginUser(LoginUserDto dto){
        User user = userRepository.findByEmail(dto.email()).orElseThrow(
                ()-> new ResourceNotFoundException("Email doesn't exist.")
        );

        if(!passwordEncoder.matches(dto.password(), user.getPassword())){
            throw new RuntimeException("Invalid password.");
        }

        return  getUserResponseDto(user);
    }



    private AuthResponseDto getUserResponseDto(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("Email", user.getEmail());

        String token = jwtService.generateToken(claims, user.getEmail());

        return new AuthResponseDto(
                token,
                userMapper.toUserResponseDto(user)
        );
    }


    @Transactional(readOnly = true)
    public Page<UserResponseDto> getAllUsers(Pageable pageable){
        return userRepository.findAll(pageable).map(userMapper::toUserResponseDto);
    }




}
