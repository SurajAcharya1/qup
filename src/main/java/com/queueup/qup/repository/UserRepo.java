package com.queueup.qup.repository;

import com.queueup.qup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,Integer> {
    @Query(value = "select  count(id) from tbl_user where user_name = ?1",nativeQuery = true)
    Integer getUserByUsername(String username);
}
