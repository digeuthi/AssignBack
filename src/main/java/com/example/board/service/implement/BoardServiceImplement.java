package com.example.board.service.implement;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.board.common.CustomResponse;
import com.example.board.domain.BoardEntity;
import com.example.board.domain.UserEntity;
import com.example.board.domain.resultSet.BoardListResultSet;
import com.example.board.dto.request.board.GetBoardListWithWordDto;
import com.example.board.dto.request.board.PatchBoardRequestDto;
import com.example.board.dto.request.board.PostBoardRequestDto;
import com.example.board.dto.response.ResponseDto;
import com.example.board.dto.response.board.GetBoardListResponseDto;
import com.example.board.dto.response.board.GetBoardResponseDto;
import com.example.board.service.BoardService;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService{
    
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Override
    public ResponseEntity<ResponseDto> postBoard(String email, PostBoardRequestDto dto) {
        
        // 게시물 작성
        String boardWriterEmail = email;
        UserEntity userEntity;
       
        try {
            // 회원가입 한 회원 확인
            boolean isUser = userRepository.existsByUserEmail(boardWriterEmail);
            if(!isUser){
                return CustomResponse.noPermission();
            }
            userEntity = userRepository.findByUserEmail(boardWriterEmail);
            String userName = userEntity.getUserName();

            BoardEntity boardEntity = new BoardEntity(email, dto);
            boardEntity.setBoardWriterName(userName);
            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            // 데이터 베이스 오류반환
            exception.printStackTrace();

            return CustomResponse.databaseError();
        }
        // 성공반환
        return CustomResponse.successs();
    }
    
    //게시물 조회
    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {
        GetBoardResponseDto body = null;
        try {
            if (boardNumber == null) {
                return CustomResponse.validationFaild();
            }
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) {
                return CustomResponse.notExistBoardNumber();
            }
            
            boardRepository.save(boardEntity);
            body = new GetBoardResponseDto(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    //게시물 목록 조회
    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardList(Pageable pageable) {
        // 게시물이 차단 된 경우 게시글 목록에서 보여지지 않게 해야함.
        // TODO : 페이징 처리부분을 추가해야함.
        GetBoardListResponseDto body = null;
        try {
            
            List<BoardListResultSet> resultSet = boardRepository.getList();
            body = new GetBoardListResponseDto(resultSet);

            resultSet.removeIf(boardListResultSet -> boardListResultSet.getBoardBlocked());

        } catch (Exception exception) {
            exception.printStackTrace();

            return CustomResponse.databaseError();

        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    //특정 단어로 게시물 검색
    @Override
    public ResponseEntity<? super GetBoardListResponseDto> getBoardWithWords(GetBoardListWithWordDto dto) {
        GetBoardListResponseDto body = null;
        String words = dto.getWords();
        words = '%'+words+'%';
        System.out.println(words);
        try {

            List<BoardEntity> resultSet = boardRepository.getSearchWord(words);
            if(resultSet == null){
                return CustomResponse.hasNoBoardWithWord();
            }
            body = new GetBoardListResponseDto(resultSet);
            

        } catch (Exception exception) {
            exception.printStackTrace();

            return CustomResponse.databaseError();

        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<ResponseDto> patchBoard(String userEmail, PatchBoardRequestDto dto) {
        
        int boardNumber = dto.getBoardNumber();
        String boardTitle = dto.getBoardTitle();
        String boardContent = dto.getBoardContent();
        //String boardImageUrl = dto.getBoardImageurl();
        String boardModifyEmail = userEmail;
        UserEntity userEntity = userRepository.findByUserEmail(boardModifyEmail);
        String boardWriterName = userEntity.getUserName();
        try{
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            // 존재하지 않는 게시물 번호 반환 boardNumber가 필요함
            if(boardEntity == null){
                return CustomResponse.notExistBoardNumber();
            }
            // 회원 목록에 존재하지 않는 이메일 (존재하는 회원의 이메일 필요함)
            Boolean isExistUserEmail = userRepository.existsByUserEmail(userEmail);
            if (!isExistUserEmail){
                return CustomResponse.noPermission();
            }
            // if(boardImageUrl != null){
            //     boardEntity.setBoardImageUrl(boardImageUrl);
            // }
            boardEntity.setBoardWriterName(boardWriterName);
            boardEntity.setBoardWriterEmail(boardModifyEmail);
            boardEntity.setBoardTitle(boardTitle);
            boardEntity.setBoardContent(boardContent);
            

            boardRepository.save(boardEntity);
        } catch(Exception exception){
            exception.printStackTrace();
            return CustomResponse.databaseError();
        }
        return CustomResponse.successs();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteBoard(String email, Integer boardNumber) {
        
        // 존재하지 않는 게시물 번호 반환 
        BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
        if(boardEntity == null){
            return CustomResponse.notExistBoardNumber();
        }
        // 회원 목록에 존재하지 이메일
        Boolean isExistUserEmail = userRepository.existsByUserEmail(email);
        if (!isExistUserEmail){
            return CustomResponse.noPermission();
        }
        boardRepository.delete(boardEntity);
        return CustomResponse.successs();
    }

    //게시물 차단
    @Override
    public ResponseEntity<ResponseDto> blockBoard(Integer boardNumber) {

        //존재하지 않는 게시물 번호 반환
        BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
        if(boardEntity == null){
            return CustomResponse.notExistBoardNumber();
        }

        boardEntity = boardRepository.findByBoardNumber(boardNumber);
        boardEntity.setBoardBlocked(true);
        boardRepository.save(boardEntity);

        
        return CustomResponse.successs();
    }
}
