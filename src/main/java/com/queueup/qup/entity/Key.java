package com.queueup.qup.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_key", uniqueConstraints = {
        @UniqueConstraint(name = "unique_key_name", columnNames = "key_name"),
        @UniqueConstraint(name = "unique_key", columnNames = "key")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Key implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer key_id;

    @Column(name = "key_name", length = 25)
    private String name;

    @Column(name = "key", length = 25)
    private String key;

    @Column(name = "date")
    private LocalDate date;
}
