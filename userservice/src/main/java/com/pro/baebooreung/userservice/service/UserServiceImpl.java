package com.pro.baebooreung.userservice.service;

import com.pro.baebooreung.userservice.domain.UserEntity;
import com.pro.baebooreung.userservice.domain.repository.UserRepository;
import com.pro.baebooreung.userservice.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository; //필드단위에서 @Autowired사용할 수 있지만 생성자 통해서 주입하는 것이 더 좋음
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        //길게 set하지 않고 간편하게 쓰는 법
        ModelMapper mapper = new ModelMapper();
        //매칭 전략을 딱 맞아 떨어지는 것만 되게끔 강력하게 설정
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        //전달받은 userDto 값을 UserEntity로 변환
        UserEntity userEntity = mapper.map(userDto,UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPassword())); // 비밀번호 암호화

        userRepository.save(userEntity);

        //반환해서 확인하기 위함
        UserDto returnUserDto = mapper.map(userEntity,UserDto.class);
        return returnUserDto;
    }
}
