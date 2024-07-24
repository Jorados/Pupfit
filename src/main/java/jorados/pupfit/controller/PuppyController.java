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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/puppy")
public class PuppyController {

    private final PuppyRepository puppyRepository;
    private final PuppyService puppyService;

    // 강아지 생성
    @PostMapping("/create")
    public ResponseEntity<?> createPuppy(@RequestBody PuppyDto puppyDto){
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
    @GetMapping("/{puppy_id}")
    public ResponseEntity<?> readPuppy(@PathVariable Long puppy_id){
        PuppyDto puppyDto = puppyService.readPuppyById(puppy_id);
        return ResponseEntity.status(HttpStatus.OK).body(puppyDto);
    }

    // 강아지 정보 수정
    @PatchMapping("/{puppy_id}")
    public ResponseEntity<?> updatePuppy(@PathVariable Long puppy_id, @RequestBody PuppyDto puppyDto){
        puppyService.updatePuppyById(puppy_id,puppyDto);
        return ResponseEntity.status(HttpStatus.OK).body(puppyDto);
    }

    // 강아지 정보 삭제
    @DeleteMapping("/{puppy_id}")
    public ResponseEntity<?> deletePuppy(@PathVariable Long puppy_id){
        puppyService.deletePuppyById(puppy_id);
        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제 되었습니다.");
    }

}
