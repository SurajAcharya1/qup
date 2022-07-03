package com.queueup.qup.entity;

import lombok.*;
import org.hibernate.id.IntegralDataTypeHolder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_token", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_id", columnNames = "fk_user_id"),
        @UniqueConstraint(name = "unique_token_number", columnNames = "token_number"),
        @UniqueConstraint(name = "unique_username", columnNames = "username")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer token_id;

    @Column(name = "fk_user_id")
    private Integer fk_user_id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "token_key")
    private String token_key;

    @Column(name = "token_number")
    private Integer token_number;

    @Column(name="status")
    private Integer Status;

    @Column(name = "statusChangedBy")
    private String statusChangedBy;

    @Column(name = "email")
    private String email;

    @Column(name="date")
    private LocalDate date;
}
