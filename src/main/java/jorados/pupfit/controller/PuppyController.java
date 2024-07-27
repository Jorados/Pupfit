package jorados.pupfit.controller;

import java.util.*;

import jorados.pupfit.dto.PuppyDto;
import jorados.pupfit.entity.Puppy;
import jorados.pupfit.repository.PuppyRepository;
import jorados.pupfit.service.PuppyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/puppy")
public class PuppyController {

    private final PuppyRepository puppyRepository;
    private final PuppyService puppyService;

    // 강아지 생성
    @PostMapping("/create")
    public ResponseEntity<?> createPuppy(@RequestBody @Validated PuppyDto puppyDto){
        puppyService.save(puppyDto);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 강아지가 생성 되었습니다.");
    }

    // 모든 강아지 조회
    @GetMapping("/all")
    public ResponseEntity<?> readAllPuppy(){
        List<PuppyDto> puppyDtos = puppyService.readAllPuppy();
        return ResponseEntity.status(HttpStatus.OK).body(puppyDtos);
    }

    // 특정 강아지 조회
    @GetMapping("/{puppyId}")
    public ResponseEntity<?> readPuppy(@PathVariable("puppyId") Long puppyId){
        PuppyDto puppyDto = puppyService.readPuppyById(puppyId);
        return ResponseEntity.status(HttpStatus.OK).body(puppyDto);
    }

    // 강아지 정보 수정
    @PatchMapping("/{puppyId}")
    public ResponseEntity<?> updatePuppy(@PathVariable("puppyId") Long puppyId, @RequestBody @Validated PuppyDto puppyDto){
        puppyService.updatePuppyById(puppyId,puppyDto);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 강아지 정보가 수정되었습니다.");
    }

    // 강아지 정보 삭제
    @DeleteMapping("/{puppyId}")
    public ResponseEntity<?> deletePuppy(@PathVariable("puppyId") Long puppyId){
        puppyService.deletePuppyById(puppyId);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제 되었습니다.");
    }

}
