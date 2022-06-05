package com.queueup.qup.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyDto {
    private Integer key_id;
    private String name;
    private String key;
}
