package com.queueup.qup.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tbl_user",uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_userName", columnNames = "userName"),
        @UniqueConstraint(name = "unique_user_phoneNumber", columnNames = "phoneNumber"),
        @UniqueConstraint(name = "unique_user_email", columnNames = "email")
})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "name", length = 80)
    private String name;

    @Column(name = "userName", length = 80)
    private String userName;

    @Column(name = "email", length = 80)
    private String email;

    @Column(name = "phoneNumber", length = 10)
    private String phoneNumber;

    @Column(name = "password", length = 64)
    private String password;

    @Column(name = "gender", length = 6)
    private String gender;

    @Column(name = "role", length = 5)
    private String role;


}
