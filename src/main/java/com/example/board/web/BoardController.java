package com.example.board.web;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.request.board.GetBoardListWithWordDto;
import com.example.board.dto.request.board.PatchBoardRequestDto;
import com.example.board.dto.request.board.PostBoardRequestDto;
import com.example.board.dto.response.ResponseDto;
import com.example.board.dto.response.board.GetBoardListResponseDto;
import com.example.board.dto.response.board.GetBoardResponseDto;
import com.example.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 1. 게시물 등록
    @PostMapping("")
    public ResponseEntity<ResponseDto> postBoard(
            @AuthenticationPrincipal String userEmail,
            @Valid @RequestBody PostBoardRequestDto requestbody) {
        ResponseEntity<ResponseDto> response = boardService.postBoard(userEmail, requestbody);
        return response;
    }

    // 2. 특정게시물 조회
    @GetMapping("/{boardNumber}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(
            @PathVariable("boardNumber") Integer boardNumber) {
        ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber);
        return response;
    }
    // 2 - 1 특정단어 포함된 게시물 리스트 조회
    @PostMapping("/search")
    public ResponseEntity<? super GetBoardListResponseDto> getBoardWithWords(
        @Valid @RequestBody GetBoardListWithWordDto requesetBody){
        ResponseEntity<? super GetBoardListResponseDto> response = boardService.getBoardWithWords(requesetBody);
        return response;
    }

    // 3. 게시물 목록 조회
    @GetMapping("/list")
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList(
        @PageableDefault(sort = {"idx"}) Pageable pageable
    ) {
        ResponseEntity<? super GetBoardListResponseDto> response = boardService.getBoardList(pageable);
        return response;
    }
    // 4. 특정 게시물 수정
    @PatchMapping("")
    public ResponseEntity<ResponseDto> patchBoard(
            @AuthenticationPrincipal String userEmail,
    @Valid @RequestBody PatchBoardRequestDto requestBody) {
        ResponseEntity<ResponseDto> response = 
        boardService.patchBoard(userEmail, requestBody);
        return response;
    }
    // 5. 특정 게시물 삭제
    @DeleteMapping("/{boardNumber}")
    public ResponseEntity<ResponseDto> deleteBoard(
        @AuthenticationPrincipal String userEmail,
        @PathVariable("boardNumber") Integer boardNumber
        ){
        ResponseEntity<ResponseDto> response = 
        boardService.deleteBoard(userEmail, boardNumber);
        return response;
    }

    // 6. 게시글 차단 기능
    @PostMapping("/block/{boardNumber}")
    public void blockBoard(@PathVariable Integer boardNumber) {
        boardService.blockBoard(boardNumber);
    }
    
}
