package com.queueup.qup.service.impl;

import com.queueup.qup.dto.KeyDto;
import com.queueup.qup.entity.Key;
import com.queueup.qup.repository.KeyRepo;
import com.queueup.qup.service.KeyService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyServiceImpl implements KeyService{
    private final KeyRepo keyRepo;

    LocalDate localDate = LocalDate.now();

    public KeyServiceImpl(KeyRepo keyRepo) {

        this.keyRepo = keyRepo;
    }

    @Override

    public KeyDto save(KeyDto keyDto){
        Key entity = Key.builder()
                .key_id(keyDto.getKey_id())
                .name(keyDto.getName())
                .key(keyDto.getKey())
                .build();
        entity.setDate(localDate);
        if(keyRepo.findAll().size()==0){
            entity = keyRepo.save(entity);
        }else {
            throw new RuntimeException("cannot generate more than one key");
        }
            return keyDto.builder()
                    .key_id(entity.getKey_id())
                    .name(keyDto.getName())
                    .key(keyDto.getKey())
                    .build();
    }

    @Override
    public List<KeyDto> findAll() {
        List<Key> keyList = keyRepo.findAll();
        return keyList.stream().map(
                key -> KeyDto.builder()
                        .key_id(key.getKey_id())
                        .name(key.getName())
                        .key(key.getKey())
                        .date(key.getDate())
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public KeyDto findById(Integer integer) {
        return null;
    }

    @Override
    public void deleteById(Integer key_id) {
        keyRepo.deleteById(key_id);
    }
}
