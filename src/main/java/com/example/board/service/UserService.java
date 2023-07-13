package com.example.board.service;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;

import com.example.board.dto.request.user.SignInRequestDto;
import com.example.board.dto.request.user.SignUpRequestDto;
import com.example.board.dto.response.user.GetAuthResponseDto;

public interface UserService {

    public ResponseEntity<? super GetAuthResponseDto> signIn(SignInRequestDto dto);
    public ResponseEntity<? super GetAuthResponseDto> signUp(SignUpRequestDto dto);
    public ResponseEntity<? super GetAuthResponseDto> logout(String email, HttpSession httpSession);

    public boolean validateStoredToken(String email, String token);
    
}
