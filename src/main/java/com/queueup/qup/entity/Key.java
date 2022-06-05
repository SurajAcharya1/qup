package com.queueup.qup.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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
    @SequenceGenerator(name = "key_id_sequence", sequenceName = "key_id_sequence")
    @GeneratedValue(generator = "key_id_sequence", strategy = GenerationType.SEQUENCE)
    private Integer key_id;

    @Column(name = "key_name", length = 25)
    private String name;

    @Column(name = "key", length = 25)
    private String key;
}
