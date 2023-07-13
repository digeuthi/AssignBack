package com.example.board.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.board.dto.request.user.SignUpRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
@Table(name = "User")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userCode;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String jwtoken;

    public UserEntity(SignUpRequestDto dto) {
        
        this.userEmail = dto.getUserEmail();
        this.userPassword = dto.getUserPassword();
        this.userName = dto.getUserName();
        
    }

}
