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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userPuppy")
public class UserPuppyController {

    private final UserPuppyService userPuppyService;
    private final UserService userService;
    private final PuppyService puppyService;

    // 생성 --> 강아지 케어 시작하는 경우
    @PostMapping("/create")
    public ResponseEntity<?> createUserPuppy(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody @Validated UserPuppyDto userPuppyDto) {
        userPuppyService.createUserPuppy(principalDetails.getUser().getId(), userPuppyDto);
        return ResponseEntity.status(HttpStatus.OK).body("유저강아지 공통정보가 성공적으로 생성되었습니다");
    }

    // userId로 데이터 찾기
    @GetMapping("/read")
    public ResponseEntity<?> readUserPuppy(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                           @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                           @RequestParam(name = "size", required = false, defaultValue = "20") Integer size){

        Pageable pageable = PageRequest.of(page, size);
        List<UserPuppyDto> findUserPuppy = userPuppyService.readAllByUserId(principalDetails.getUser().getId(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(findUserPuppy);
    }

    // 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserPuppy(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        userPuppyService.deleteUserPuppy(principalDetails.getUser().getId());
        return ResponseEntity.status(HttpStatus.OK).body("유저강아지 공통정보가 성공적으로 삭제되었습니다");
    }
}
