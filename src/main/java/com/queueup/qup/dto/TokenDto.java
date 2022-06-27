package com.queueup.qup.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenDto {

    private Integer token_id;
    private String token_key;
    private Integer fk_user_id;
    private String name;
    private String username;
    private Integer token_number;
    private Integer status;
    private String statusChangedBy;
}
