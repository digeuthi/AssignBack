package com.example.board.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.example.board.dto.request.board.GetBoardListWithWordDto;
import com.example.board.dto.request.board.PatchBoardRequestDto;
import com.example.board.dto.request.board.PostBoardRequestDto;
import com.example.board.dto.response.ResponseDto;
import com.example.board.dto.response.board.GetBoardResponseDto;
import com.example.board.dto.response.board.GetBoardListResponseDto;

public interface BoardService {
    
    public ResponseEntity<ResponseDto> postBoard(String email, PostBoardRequestDto dto);
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList(Pageable pageable);
    public ResponseEntity<? super GetBoardListResponseDto> getBoardWithWords(GetBoardListWithWordDto words);
    
    public ResponseEntity<ResponseDto> patchBoard(String userEmail, PatchBoardRequestDto dto);
    public ResponseEntity<ResponseDto> deleteBoard(String email, Integer boardNumber);

    public ResponseEntity<ResponseDto> blockBoard(Integer boardNumber);
    public void test();
    
}
