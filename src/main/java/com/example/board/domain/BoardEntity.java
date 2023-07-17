package com.example.board.domain;

import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.board.dto.request.board.PostBoardRequestDto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Board")
@Table(name = "Board")
public class BoardEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardNumber;
    private String boardWriterEmail;
    private String boardWriterName;
    private String boardTitle;
    private String boardContent;
    private String boardImageUrl;
    private String boardWriteDateTime;
    private boolean boardBlocked;

    public BoardEntity(String email, PostBoardRequestDto dto){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat("yyyy-MM-dd");
        String writeDateTime = simpleDateFormat.format(now);
        this.boardWriterEmail = email;
        this.boardTitle = dto.getBoardTitle();
        this.boardContent = dto.getBoardContent();
        this.boardImageUrl = dto.getBoardImageUrl();
        this.boardWriteDateTime = writeDateTime;
    
    }

}
