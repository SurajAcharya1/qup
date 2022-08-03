package com.queueup.qup.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "tbl_login_detail")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "name")
    private String name;

    @Column(name = "token_number")
    private Integer token_number;
}
