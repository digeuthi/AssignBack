package com.example.board.dto.response.board;

import com.example.board.domain.BoardEntity;
import com.example.board.domain.resultSet.BoardListResultSet;
import com.example.board.dto.response.ResponseDto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetBoardListResponseDto extends ResponseDto{
    
    private List<BoardSummary> boardList;
    // 게시물 목록 조회
        public GetBoardListResponseDto(List<? extends Object> resultSet){
            super("SU","Success");
    
            List<BoardSummary> boardList = new ArrayList<>();
            
            for(Object result : resultSet) {
                BoardSummary boardSummary = new BoardSummary();
                if (result instanceof BoardEntity)
                    boardSummary = new BoardSummary((BoardEntity) result);
                if (result instanceof BoardListResultSet)
                    boardSummary = new BoardSummary((BoardListResultSet) result);
                boardList.add(boardSummary);
            }
            this.boardList = boardList;
        }
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class BoardSummary {
       
        private Integer boardNumber;
        private String boardTitle;
        private String boardContent;
        private String boardImageUrl;
        private String boardWriteDatetime;
        private int viewCount;
        private String boardWriterEmail;
      
        public BoardSummary(BoardListResultSet resultSet){
            this.boardNumber = resultSet.getBoardNumber();
            this.boardTitle =resultSet.getBoardTitle();
            this.boardContent=resultSet.getBoardContent();
            //this.boardImageUrl=resultSet.getBoardimageUrl();
            this.boardWriteDatetime = resultSet.getBoardWriteDatetime();
            this.boardWriterEmail = resultSet.getBoardWriterEmail();
        }
    
        public BoardSummary(BoardEntity boardEntity){
            this.boardNumber = boardEntity.getBoardNumber();
            this.boardTitle =boardEntity.getBoardTitle();
            this.boardContent=boardEntity.getBoardContent();
            //this.boardImageUrl=boardEntity.getBoardImageUrl();
            //this.boardWriteDatetime = boardEntity.getBoardWriteDateTime();
            this.boardWriterEmail = boardEntity.getBoardWriterEmail();
        }


}
