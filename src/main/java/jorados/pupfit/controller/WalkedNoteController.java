package jorados.pupfit.controller;

import jorados.pupfit.config.auth.PrincipalDetails;
import jorados.pupfit.dto.UserPuppyDto;
import jorados.pupfit.dto.WalkedNoteDto;
import jorados.pupfit.service.UserPuppyService;
import jorados.pupfit.service.WalkedNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 산책정보 테이블 (게시판같은거임)
@RestController
@RequiredArgsConstructor
@RequestMapping("/walkedNote")
public class WalkedNoteController {

    private final UserPuppyService userPuppyService;
    private final WalkedNoteService walkedNoteService;

    // 내가 쓴 일기 -> 모두 조회 (userId로)
    @GetMapping("/read")
    public ResponseEntity<?> readWalkedNote(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<UserPuppyDto> findUserPuppy = userPuppyService.readAllByUserId(principalDetails.getUser().getId());
        List<WalkedNoteDto> walkedNoteDtoList = walkedNoteService.readAllWalkedNote(findUserPuppy);
        return ResponseEntity.status(HttpStatus.OK).body(walkedNoteDtoList);
    }

    // 상세 조회
    @GetMapping("/read/{walkedNoteId}")
    public ResponseEntity<?> readWalkedNoteById(@PathVariable Long walkedNoteId) {
        WalkedNoteDto findWalkedNote = walkedNoteService.readWalkedNote(walkedNoteId);
        return ResponseEntity.status(HttpStatus.OK).body(findWalkedNote);
    }

    // 해당 정보 삭제하기
    @DeleteMapping("/delete/{walkedNoteId}")
    public ResponseEntity<?> deleteWalkedNoteById(@PathVariable Long walkedNoteId) {
        walkedNoteService.deleteWalkedNote(walkedNoteId);
        return ResponseEntity.status(HttpStatus.OK).body("해당 산책 정보가 삭제 되었습니다.");
    }

    // 해당 정보 수정하기
    @PatchMapping("/delete/{walkedNoteId}")
    public ResponseEntity<?> updateWalkedNoteById(@RequestBody @Validated WalkedNoteDto walkedNoteDto) {
        walkedNoteService.updateWalkedNote(walkedNoteDto);
        return ResponseEntity.status(HttpStatus.OK).body("해당 산책 정보가 삭제 되었습니다.");
    }
}
