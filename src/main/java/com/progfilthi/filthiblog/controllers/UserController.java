package com.progfilthi.filthiblog.controllers;

import com.progfilthi.filthiblog.models.dto.auth.AuthResponseDto;
import com.progfilthi.filthiblog.models.dto.user.CreateUserDto;
import com.progfilthi.filthiblog.models.dto.user.LoginUserDto;
import com.progfilthi.filthiblog.models.dto.user.UserResponseDto;
import com.progfilthi.filthiblog.services.auth.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@Valid @RequestBody CreateUserDto dto){
        AuthResponseDto response = userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@Valid @RequestBody LoginUserDto dto){
        AuthResponseDto response = userService.loginUser(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public Page<UserResponseDto> getAllUsers(@PageableDefault(size = 5, sort = "username", direction = Sort.Direction.ASC) Pageable pageable){
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentAuthenticatedUser(){
        UserResponseDto response = userService.getCurrentAuthenticatedUser();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{user_id}/promote-admin")
    public ResponseEntity<UserResponseDto> promoteUserToAdmin(@PathVariable Long user_id){
        UserResponseDto response = userService.promoteUserToAdmin(user_id);
        return ResponseEntity.ok().body(response);
    }


    @PutMapping("/{user_id}/demote-admin")
    public ResponseEntity<UserResponseDto> demoteAdminToUser(@PathVariable Long user_id){
        UserResponseDto response = userService.demoteAdminToUser(user_id);
        return ResponseEntity.ok().body(response);
    }

}
