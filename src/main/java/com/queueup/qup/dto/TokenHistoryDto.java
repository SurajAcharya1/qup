package com.queueup.qup.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenHistoryDto {

    private Integer token_history_id;
    private String token_key;
    private Integer fk_user_id;
    private String name;
    private String username;
    private Integer token_number;
    private LocalDate date;
}
