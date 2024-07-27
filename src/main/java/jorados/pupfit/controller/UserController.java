package jorados.pupfit.controller;

import jakarta.validation.Valid;
import jorados.pupfit.config.auth.PrincipalDetails;
import jorados.pupfit.dto.UserDto;
import jorados.pupfit.dto.request.UserLoginDto;
import jorados.pupfit.dto.request.UserRequest;
import jorados.pupfit.dto.response.UserResponse;
import jorados.pupfit.entity.User;
import jorados.pupfit.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 회원가입 -> 구현해야함 (시큐리티 적용 이후에.)
    // 로그인, 로그아웃 -> 시큐리티 기능 사용
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Validated UserLoginDto loginUser){
        userService.createUser(loginUser);
        return ResponseEntity.status(HttpStatus.OK).body("회원 생성 완료");
    }

    // 조회
    @GetMapping("/read/{userId}")
    public ResponseEntity<?> readUser(@PathVariable("userId") Long userId) {
        UserResponse userResponse = userService.readUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    // 모두조회
    @GetMapping("/readAll")
    public ResponseEntity<?> readAllUser(){
        List<UserDto> findUsers = userService.readAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(findUsers);
    }

    // 수정
    @PatchMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Validated UserRequest userRequest){
        User findUser = principalDetails.getUser();
        userService.updateUser(findUser.getId(), userRequest);
        return ResponseEntity.status(HttpStatus.OK).body("회원 정보 수정이 완료 되었습니다");
    }

    // 삭제
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        User findUser = principalDetails.getUser();
        userService.deleteUser(findUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body("회원 정보 수정이 완료 되었습니다");
    }
}
