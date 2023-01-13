package com.example.adstrade.model.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UsernameAndPasswordAuthRequest {

    private String email;
    private String password;
}
