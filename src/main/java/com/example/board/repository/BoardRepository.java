package com.example.board.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.board.domain.BoardEntity;
import com.example.board.domain.resultSet.BoardListResultSet;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer>{

    public BoardEntity findByBoardNumber(int boardNumber);
    
    @Query(
        value = 
        "SELECT " +
        "* FROM board_view; ",
        nativeQuery = true  
    )
    public List<BoardListResultSet> getList();

    public List<BoardEntity> findByBoardWriterEmail(String email);

    @Query(
    value = "SELECT * FROM board WHERE UPPER(board_title) "
    +"LIKE UPPER(:words) OR UPPER(board_content) LIKE UPPER(:words); ",
    nativeQuery = true
    )
    public List<BoardEntity> getSearchWord(@Param("words") String words);
    
}
