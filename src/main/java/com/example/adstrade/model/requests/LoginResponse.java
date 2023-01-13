package com.example.adstrade.model.requests;

import com.example.adstrade.model.User;
import com.example.adstrade.model.dto.UserDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginResponse {

    private UserDto userDto;
}