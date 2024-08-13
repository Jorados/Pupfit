package jorados.pupfit.controller;

import jorados.pupfit.config.auth.PrincipalDetails;
import jorados.pupfit.dto.UserPuppyDto;
import jorados.pupfit.dto.WalkedNoteDto;
import jorados.pupfit.error.CustomNotFoundException;
import jorados.pupfit.service.UserPuppyService;
import jorados.pupfit.service.WalkedNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// 산책정보 테이블 (게시판같은거임)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/walkedNote")
public class WalkedNoteController {

    private final UserPuppyService userPuppyService;
    private final WalkedNoteService walkedNoteService;

    // 산책 정보 생성
    @PostMapping("/create")
    public ResponseEntity<?> createWalkedNote(@RequestBody @Validated WalkedNoteDto walkedNoteDto){
        System.out.println(walkedNoteDto);
        walkedNoteService.createWalkedNote(walkedNoteDto);
        return ResponseEntity.status(HttpStatus.OK).body("산책 정보가 생성되었습니다");
    }

    // 내가 쓴 일기 -> 모두 조회 (userId로)
    @GetMapping("/read")
    public ResponseEntity<?> readWalkedNote(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                            @RequestParam(name = "limit", required = false) Integer limit) {
        List<UserPuppyDto> findUserPuppy = userPuppyService.readAllByUserId(principalDetails.getUser().getId());
        Map<Long,List<WalkedNoteDto>> walkedNoteDtoList = walkedNoteService.readAllWalkedNote(findUserPuppy, limit);

        return ResponseEntity.status(HttpStatus.OK).body(walkedNoteDtoList);
    }

    // 특정 키우는 강아지에 대한 산책 최신 정보 -> 산책 필요 상태를 알기 위함.
    @GetMapping("/read/userPuppy/{userPuppyId}")
    public ResponseEntity<?> readWalkedNoteByUserPuppyId(@PathVariable("userPuppyId") Long userPuppyId) {
        int count = walkedNoteService.CountReadWalkedNoteByUserPuppyId(userPuppyId);
        if(count <= 0){
            return ResponseEntity.status(HttpStatus.OK).body("userPuppy " + userPuppyId + "에 대한 산책정보 없음");
        }

        WalkedNoteDto walkedNoteDto = walkedNoteService.readWalkedNoteByUserPuppyId(userPuppyId);
        return ResponseEntity.status(HttpStatus.OK).body(walkedNoteDto);
    }

    // 상세 조회
    @GetMapping("/read/{walkedNoteId}")
    public ResponseEntity<?> readWalkedNoteById(@PathVariable("walkedNoteId") Long walkedNoteId) {
        WalkedNoteDto findWalkedNote = walkedNoteService.readWalkedNote(walkedNoteId);
        return ResponseEntity.status(HttpStatus.OK).body(findWalkedNote);
    }

    // 해당 정보 삭제하기
    @DeleteMapping("/delete/{walkedNoteId}")
    public ResponseEntity<?> deleteWalkedNoteById(@PathVariable("walkedNoteId") Long walkedNoteId) {
        walkedNoteService.deleteWalkedNote(walkedNoteId);
        return ResponseEntity.status(HttpStatus.OK).body("해당 산책 정보가 삭제 되었습니다.");
    }

    // 해당 정보 수정하기
    @PatchMapping("/update/{walkedNoteId}")
    public ResponseEntity<?> updateWalkedNoteById(@RequestBody @Validated WalkedNoteDto walkedNoteDto) {
        walkedNoteService.updateWalkedNote(walkedNoteDto);
        return ResponseEntity.status(HttpStatus.OK).body("해당 산책 정보가 삭제 되었습니다.");
    }



//    // 해당 walkedNoteId를 이용해서 puppy의 정보를 불러오는 api
//    @GetMapping("/read/puppy/{walkedNoteId}")
//    public ResponseEntity<?> readPuppyByWalkedNoteId(@PathVariable("walkedNoteId") Long walkedNoteId){
//        walkedNoteService.readPuppyByWalkedNoteId(walkedNoteId);
//    }
}
