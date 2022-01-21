package com.erlangga.finalproject.authservice.controller;

import com.erlangga.finalproject.authservice.payload.BaseResponse;
import com.erlangga.finalproject.authservice.payload.TokenResponse;
import com.erlangga.finalproject.authservice.payload.UserRegister;
import com.erlangga.finalproject.authservice.payload.UsernamePassword;
import com.erlangga.finalproject.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserRegister>> register(@RequestBody UserRegister userRegister){
        if (userRegister.getUsername() == null){
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Bad Request"), HttpStatus.BAD_REQUEST);
        }
        try {
            authService.register(userRegister);
            return ResponseEntity.ok(new BaseResponse<>(userRegister));
        }catch (Exception e){
            if(e.getMessage().equalsIgnoreCase("Duplicated")){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "Duplicated"), HttpStatus.CONFLICT);
            }
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/token")
    public ResponseEntity<BaseResponse<?>> getToken(@RequestBody UsernamePassword usernamePassword){
        if (usernamePassword.getUsername() == null){
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Bad Request"), HttpStatus.BAD_REQUEST);
        }
        try {
            TokenResponse token = authService.generateToken(usernamePassword);
            return ResponseEntity.ok(new BaseResponse<>(token));
        }catch (Exception e){
            if(e.getMessage().equalsIgnoreCase("Bad Credential")){
                return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                        "Bad Credential"), HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity(new BaseResponse(Boolean.FALSE,
                    "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
