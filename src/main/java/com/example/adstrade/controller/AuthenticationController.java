package com.example.adstrade.controller;

import com.example.adstrade.model.requests.LoginResponse;
import com.example.adstrade.model.requests.RegisterUserRequest;
import com.example.adstrade.model.User;
import com.example.adstrade.model.dto.UserDto;
import com.example.adstrade.model.requests.UsernameAndPasswordAuthRequest;
import com.example.adstrade.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthenticationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @ApiOperation(value = "Login", notes = "Existing users can login with their credentials.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@NotNull @RequestBody UsernameAndPasswordAuthRequest request){
        UserDto userDto = userService.findUserByEmailDto(request.getEmail());

        if(userDto == null || !passwordEncoder.matches(request.getPassword(), userDto.getPassword())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LoginResponse response = new LoginResponse();
        response.setUserDto(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Register", notes = "New users can register to the app..")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest request){
        UserDto user = userService.registerUser(request);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
