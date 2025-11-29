package com.progfilthi.filthiblog.services.auth;

import com.progfilthi.filthiblog.enums.Roles;
import com.progfilthi.filthiblog.globalExceptionHandler.AccessDeniedException;
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
import org.springframework.security.core.context.SecurityContextHolder;
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
                ()-> new ResourceNotFoundException("User with that email doesn't exist.")
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


    @Transactional
    public UserResponseDto promoteUserToAdmin(Long id){
        User currentUser = getCurrentUser();

        if(!currentUser.getRole().equals(Roles.ADMIN)){
            throw new AccessDeniedException("Only admins can promote regular users to admins.");
        }

        User userToPromote = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User with id " + id + " not found")
        );

        if(userToPromote.getRole().equals(Roles.ADMIN)){
            throw new ResourceConflictException("User is already an admin.");
        }

        userToPromote.setRole(Roles.ADMIN);

        User promotedUser = userRepository.save(userToPromote);

        return userMapper.toUserResponseDto(promotedUser);
    }


    public UserResponseDto demoteAdminToUser(Long id){
        User currentUser = getCurrentUser();

        if(!currentUser.getRole().equals(Roles.ADMIN)){
            throw new AccessDeniedException("Only admins can demote admins to regular users.");
        }

        User userToDemote = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User with id " + id + " not found")
        );

        if(userToDemote.getRole().equals(Roles.USER)){
            throw new ResourceConflictException("Admin is already a regular user.");
        }

        userToDemote.setRole(Roles.USER);

        User demotedUser = userRepository.save(userToDemote);

        return userMapper.toUserResponseDto(demotedUser);

    }


    private User getCurrentUser(){
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || auth.getPrincipal() == null){
            throw new ResourceNotFoundException("No authenticated user found.");
        }

        return (User) auth.getPrincipal();
    }


    public UserResponseDto getCurrentAuthenticatedUser(){
        User currentUser = getCurrentUser();
        return userMapper.toUserResponseDto(currentUser);
    }




}
