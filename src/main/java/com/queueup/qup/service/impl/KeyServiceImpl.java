package com.queueup.qup.service.impl;

import com.queueup.qup.dto.KeyDto;
import com.queueup.qup.entity.Key;
import com.queueup.qup.repository.KeyRepo;
import com.queueup.qup.service.KeyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeyServiceImpl implements KeyService{
    private final KeyRepo keyRepo;

    public KeyServiceImpl(KeyRepo keyRepo) {

        this.keyRepo = keyRepo;
    }

    @Override
    public KeyDto save(KeyDto keyDto) {
        Key entity = Key.builder()
                .key_id(keyDto.getKey_id())
                .name(keyDto.getName())
                .key(keyDto.getKey())
                .build();
        entity = keyRepo.save(entity);
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
                        .build()
        ).collect(Collectors.toList());
    }

    @Override
    public KeyDto findById(Integer integer) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
