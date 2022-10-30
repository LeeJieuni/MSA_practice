package com.pro.baebooreung.userservice.service;

import com.pro.baebooreung.userservice.domain.UserEntity;
import com.pro.baebooreung.userservice.domain.repository.UserRepository;
import com.pro.baebooreung.userservice.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        //길게 set하지 않고 간편하게 쓰는 법
        ModelMapper mapper = new ModelMapper();
        //매칭 전략을 딱 맞아 떨어지는 것만 되게끔 강력하게 설정
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //전달받은 userDto 값을 UserEntity로 변환
        UserEntity userEntity = mapper.map(userDto,UserEntity.class);
        userEntity.setEncryptedPwd("encrypted_password"); // 아직 설정안해서 기본값으로

        userRepository.save(userEntity);
        return null;
    }
}
