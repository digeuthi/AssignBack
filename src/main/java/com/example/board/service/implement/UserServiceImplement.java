package com.example.board.service.implement;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.board.common.CustomResponse;
import com.example.board.domain.UserEntity;
import com.example.board.dto.request.user.SignInRequestDto;
import com.example.board.dto.request.user.SignUpRequestDto;
import com.example.board.dto.response.user.GetAuthResponseDto;
import com.example.board.repository.UserRepository;
import com.example.board.service.UserService;
import com.example.board.provider.JwtProvider;

@Service
public class UserServiceImplement implements UserService{

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private HttpSession httpSession;

    @Autowired
    public UserServiceImplement(
            UserRepository userRepository,
            JwtProvider jwtProvider, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.jwtProvider = jwtProvider;
        this.httpSession = httpSession;
    }

    @Override //회원가입
    public ResponseEntity<? super GetAuthResponseDto> signUp(SignUpRequestDto dto) {
        GetAuthResponseDto body = null;
        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();
        String userName = dto.getUserName();

        try {
            // 존재하는 유저 이메일
            boolean existedUserEmail = userRepository.existsByUserEmail(userEmail);
            if (existedUserEmail)
                return CustomResponse.existUserEmail();

            String encodedPassword = passwordEncoder.encode(userPassword); // 유저 계정 생성 및 암호화 작업
            dto.setUserPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);
            System.out.println(userEntity.toString());
            int userCode = userEntity.getUserCode();

            body = new GetAuthResponseDto(userCode);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override //로그인
    public ResponseEntity<? super GetAuthResponseDto> signIn(SignInRequestDto dto) {
        GetAuthResponseDto body = null;

        String userEmail = dto.getUserEmail();
        String userPassword = dto.getUserPassword();

        try {
            // 로그인 실패 (이메일 x)
            UserEntity userEntity = userRepository.findByUserEmail(userEmail);
            if (userEntity == null)
                return CustomResponse.signInFailed();

            // 로그인 실패 (패스워드 x)
            String encordedPassword = userEntity.getUserPassword();
            boolean equaledPassword = passwordEncoder.matches(userPassword, encordedPassword);
            
            if (!equaledPassword)
                return CustomResponse.signInFailed();

            String jwt = jwtProvider.create(userEmail);
            int userCode = userEntity.getUserCode();
            String jwtSecret = jwt.split("\\.")[2];
            String encordJwt = passwordEncoder.encode(jwtSecret);
            userEntity.setJwtoken(encordJwt);
            userRepository.save(userEntity);
            body = new GetAuthResponseDto(jwt, userCode);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override //로그아웃
    public ResponseEntity<? super GetAuthResponseDto> logout(String email, HttpSession httpSession) {
        UserEntity userEntity = userRepository.findByUserEmail(email);
        try {
            userEntity.setJwtoken(null);
           userRepository.save(userEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.logoutFailed();
        }
        return CustomResponse.successs();
    }

    @Override
    public boolean validateStoredToken(String email, String token) {
        
        if (email.isBlank()) return false;

        UserEntity userEntity = userRepository.findByUserEmail(email);
        String storedToken = userEntity.getJwtoken();
        String jwtSecret = token.split("\\.")[2];
        boolean comparedResult = passwordEncoder.matches(jwtSecret, storedToken);
        return comparedResult;
        
    }
    
}
