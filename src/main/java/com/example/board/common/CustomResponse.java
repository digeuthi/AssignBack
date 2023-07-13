package com.example.board.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.board.dto.response.ResponseDto;

public class CustomResponse {

    public static ResponseEntity<ResponseDto> successs() {
        ResponseDto errorBody = new ResponseDto("SU", "Sucess");
        return ResponseEntity.status(HttpStatus.OK).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto errorBody = new ResponseDto("DE", "Database Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> noPermission() {

        ResponseDto errorBody = new ResponseDto("NP", "No-permission");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> validationFaild() {
        ResponseDto errorBody = new ResponseDto("VF", "Request Parameter Validation Failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> notExistBoardNumber() {
        ResponseDto errorBody = new ResponseDto("NB", "None-Existent Board Number");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> hasNoBoardWithWord(){
        ResponseDto errorBody = new ResponseDto("NW","Has No BoardList With the Word");
        return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> existUserEmail() {

        ResponseDto errorBody = new ResponseDto("EU", "Existent User Email");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> signInFailed() {

        ResponseDto errorBody = new ResponseDto("SF", "Sign In Failed");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorBody);
    }

    public static ResponseEntity<ResponseDto> logoutFailed() {
        ResponseDto errorBody = new ResponseDto("LF", "Logout Faild");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
    }
    
}
