package com.pro.baebooreung.userservice.controller;

import com.pro.baebooreung.userservice.dto.UserDto;
import com.pro.baebooreung.userservice.service.UserService;
import com.pro.baebooreung.userservice.vo.Greeting;
import com.pro.baebooreung.userservice.vo.RequestUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private Greeting greeting; //인스턴스로 생성
    private Environment env;
    private UserService userService;

    @Autowired //이건 생성자로 따로 빼서 하는게 좋음. 바로 @Autowired쓰기보다는.
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @GetMapping("/health_check")
    public String status(){
        return "It's Working in User Service";
    }

    @PostMapping("/users")
    public ResponseEntity CreateUser(@RequestBody RequestUser user){
        //userService로 넘겨주기 위해서는 Requestuser를 dto로 바꿔야함
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        return new ResponseEntity(HttpStatus.CREATED);
        //201 성공코드 반환환    }
}