package com.example.board.domain.resultSet;

public interface BoardListResultSet {
    
    public int getBoardNumber();
    public String getBoardTitle();
    public String getBoardContent();
    public String getBoardimageUrl();
    public String getBoardWriteDatetime();
    public String getBoardWriterEmail();
    public String getBoardWriterName();
    public Boolean getBoardBlocked();

}
