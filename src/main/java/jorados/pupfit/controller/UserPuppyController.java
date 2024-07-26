package jorados.pupfit.controller;


import jorados.pupfit.config.auth.PrincipalDetails;
import jorados.pupfit.dto.PuppyDto;
import jorados.pupfit.dto.UserPuppyDto;
import jorados.pupfit.entity.User;
import jorados.pupfit.service.PuppyService;
import jorados.pupfit.service.UserPuppyService;
import jorados.pupfit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userPuppy")
public class UserPuppyController {

    private final UserPuppyService userPuppyService;
    private final UserService userService;
    private final PuppyService puppyService;

    // 생성
    @PostMapping("/create")
    public ResponseEntity<?> createUserPuppy(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody UserPuppyDto userPuppyDto) {
        userPuppyService.createUserPuppy(principalDetails.getUser().getId(), userPuppyDto);
        return ResponseEntity.status(HttpStatus.OK).body("유저강아지 공통정보가 성공적으로 생성되었습니다");
    }

    // userId로 데이터 찾기
    @GetMapping("/read")
    public ResponseEntity<?> readUserPuppy(@AuthenticationPrincipal PrincipalDetails principalDetails){
        List<UserPuppyDto> findUserPuppy = userPuppyService.readAllByUserId(principalDetails.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body(findUserPuppy);
    }

    // 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserPuppy(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        userPuppyService.deleteUserPuppy(principalDetails.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body("유저강아지 공통정보가 성공적으로 삭제되었습니다");
    }
}
