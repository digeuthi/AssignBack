package com.example.board.dto.response.user;

import com.example.board.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAuthResponseDto extends ResponseDto {

    private Integer userCode;
    private String token;
    private int expirationDate;  

    public GetAuthResponseDto(Integer userCode){ //회원가입
        super("SU", "Sucess");
        this.userCode = userCode;
    }
    public GetAuthResponseDto(String token, Integer userCode){ //로그인
        super("SU", "Sucess");
        this.userCode = userCode;
        this.token = token;
        this.expirationDate = 3600;
    }
    
}
