package com.queueup.qup.repository;

import com.queueup.qup.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeyRepo extends JpaRepository<Key,Integer> {

    @Query(value = "select key from tbl_key where key = ?1", nativeQuery = true)
    public String getKeyFromLogin(String key);

    @Query(value = "select key from tbl_key", nativeQuery = true)
    public String getKey();

}
