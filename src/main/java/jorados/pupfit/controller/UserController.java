package jorados.pupfit.controller;

import jakarta.validation.Valid;
import jorados.pupfit.config.auth.PrincipalDetails;
import jorados.pupfit.dto.UserDto;
import jorados.pupfit.dto.request.UserLoginDto;
import jorados.pupfit.dto.request.UserPasswordRequest;
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

    // 회원가입
    // 로그인 -> 시큐리티 기능 사용
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Validated UserLoginDto loginUser){
        userService.createUser(loginUser);
        return ResponseEntity.status(HttpStatus.OK).body("회원 생성 완료");
    }

    // 현재 사용자 정보 조회
    @GetMapping("/read")
    public ResponseEntity<?> readUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        UserResponse userResponse = userService.readUser(principalDetails.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    // 모두조회
    @GetMapping("/readAll")
    public ResponseEntity<?> readAllUser(){
        List<UserDto> findUsers = userService.readAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(findUsers);
    }

    // 패스워드 일치 불일치 확인
    @PostMapping("/password")
    public ResponseEntity<?> findPassword(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                          @RequestBody UserPasswordRequest userPasswordRequest){
        User findUser = principalDetails.getUser();

        boolean isPasswordValid = userService.checkPassword(findUser, userPasswordRequest.getPassword());
        if (isPasswordValid) return ResponseEntity.status(HttpStatus.OK).body("비밀번호 일치");
        else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호 불일치");
    }

    // 수정
    @PatchMapping("/update")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Validated UserRequest userRequest){
        User findUser = principalDetails.getUser();
        userService.updateUser(findUser.getId(), userRequest);
        return ResponseEntity.status(HttpStatus.OK).body("회원 정보 수정이 완료 되었습니다");
    }

    // 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
        User findUser = principalDetails.getUser();
        userService.deleteUser(findUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body("회원 정보 삭제가 완료 되었습니다");
    }
}
