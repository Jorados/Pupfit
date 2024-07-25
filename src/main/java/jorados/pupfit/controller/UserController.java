package jorados.pupfit.controller;

import jakarta.validation.Valid;
import jorados.pupfit.dto.UserDto;
import jorados.pupfit.dto.response.UserResponse;
import jorados.pupfit.entity.User;
import jorados.pupfit.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 회원가입 -> 구현해야함 (시큐리티 적용 이후에.)
    // 로그인, 로그아웃 -> 시큐리티 기능 사용
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("회원 생성 완료");
    }



    // 조회
    @GetMapping("/read/{userId}")
    public ResponseEntity<?> readUser(@PathVariable Long userId) {
        UserResponse userResponse = userService.readUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    // 모두조회
    //@GetMapping("/read/{")

    // 수정

    // 삭제
}
