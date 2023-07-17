package com.example.board.domain;

import java.text.SimpleDateFormat;

import javax.persistence.Column;
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
    @Column(name = "board_number")
    private Integer boardNumber;
    @Column(name = "board_writer_email")
    private String boardWriterEmail;
    @Column(name = "board_writer_name")
    private String boardWriterName;
    @Column(name = "board_title")
    private String boardTitle;
    @Column(name = "board_content")
    private String boardContent;
    @Column(name = "board_image_url")
    private String boardImageUrl;
    @Column(name = "board_write_date_time")
    private String boardWriteDateTime;
    @Column(name = "board_blocked")
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
