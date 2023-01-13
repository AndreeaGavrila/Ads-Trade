package com.example.adstrade.controller;

import com.example.adstrade.model.User;
import com.example.adstrade.model.dto.SaleTopicDto;
import com.example.adstrade.model.dto.UserDto;
import com.example.adstrade.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.example.adstrade.model.dto.UserDto.convertEntityToDto;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Find a user by ID", notes = "The user must exist.")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id){
        UserDto userDto = userService.findUserByIdDto(id);
        if(userDto != null){
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Find all users", notes = "Display all existing users.")
    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.findAllUsersDto();
        if(users != null){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
