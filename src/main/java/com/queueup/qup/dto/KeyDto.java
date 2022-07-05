package com.queueup.qup.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyDto {
    private Integer key_id;
    private String name;
    private String key;
    private LocalDate date;
}
