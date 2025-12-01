package com.progfilthi.filthiblog.services;

import com.progfilthi.filthiblog.globalExceptionHandler.ResourceConflictException;
import com.progfilthi.filthiblog.mappers.IUserMapper;
import com.progfilthi.filthiblog.models.dto.user.CreateUserDto;
import com.progfilthi.filthiblog.repositories.IUserRepository;
import com.progfilthi.filthiblog.services.auth.JwtService;
import com.progfilthi.filthiblog.services.auth.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private  IUserRepository userRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private  IUserMapper userMapper;

    @Mock
    private  JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_shouldThrowConflict_whenEmailExists(){
        CreateUserDto dto = new CreateUserDto("Emma", "emma@gmail.com", "password");

        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(ResourceConflictException.class, ()-> userService.registerUser(dto));

        verify(userRepository).existsByEmail(dto.email());

        verifyNoMoreInteractions(userRepository, userMapper, jwtService);
    }
}
