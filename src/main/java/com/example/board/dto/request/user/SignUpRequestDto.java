package com.example.board.dto.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    @Email
    private String userEmail;
    @NotBlank
    @Size(min=8)
    private String userPassword;
    @NotBlank
    @Size(min=0, max=6)
    private String userName;
    
}
