package com.example.board.dto.request.board;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetBoardListWithWordDto {
    
    @NotNull
    private String words;

}
