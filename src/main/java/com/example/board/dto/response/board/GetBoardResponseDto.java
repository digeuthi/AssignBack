package com.example.board.dto.response.board;

import com.example.board.domain.BoardEntity;
import com.example.board.dto.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBoardResponseDto extends ResponseDto{

    private int boardNumber;
    private String boardTitle;
    private String boardImageUrl;
    private String boardWriteDatetime;
    private String boardWriterEmail;
    private String boardWriterName;
   

    public GetBoardResponseDto(BoardEntity boardEntity){
        super("SU","Success");
        this.boardNumber = boardEntity.getBoardNumber();
        this.boardTitle = boardEntity.getBoardTitle();
        this.boardImageUrl = boardEntity.getBoardImageUrl();
        this.boardWriteDatetime = boardEntity.getBoardWriteDateTime();
        this.boardWriterEmail = boardEntity.getBoardWriterEmail();
        this.boardWriterName = boardEntity.getBoardWriterName();
        
    }
    
}
