package com.example.board.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.domain.BoardEntity;
import com.example.board.domain.UserEntity;
import com.example.board.dto.request.user.SignInRequestDto;
import com.example.board.dto.request.user.SignUpRequestDto;
import com.example.board.dto.response.user.GetAuthResponseDto;
import com.example.board.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public void signUp(
        @RequestBody UserEntity dto){
        userService.signUp(dto);
    }

    @PostMapping("/sign-in")
    public void signIn(
        @RequestBody UserEntity dto
    ){
        // ResponseEntity<? super GetAuthResponseDto> response = userServ1ice.signIn(requsetBody);
        userService.signIn(dto);
        System.out.println(dto.toString());
    }

    @GetMapping("/logout")
    public ResponseEntity<? super GetAuthResponseDto> logout(
        @AuthenticationPrincipal String email, @Valid HttpSession httpSession) {
        ResponseEntity<? super GetAuthResponseDto> response = userService.logout(email, httpSession);
        return response;
    }

    @GetMapping("/test")
    public void test() {
        userService.test();
    }
    
}
