package com.erlangga.finalproject.authservice.service;

import com.erlangga.finalproject.authservice.model.User;
import com.erlangga.finalproject.authservice.payload.TokenResponse;
import com.erlangga.finalproject.authservice.payload.UserRegister;
import com.erlangga.finalproject.authservice.payload.UsernamePassword;

public interface AuthService {
    User register (UserRegister req);
    TokenResponse generateToken(UsernamePassword req);
}
