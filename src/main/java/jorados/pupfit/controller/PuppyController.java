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
public class PuppyController {

    private final PuppyRepository puppyRepository;
    private final PuppyService puppyService;

    @PostMapping("/puppy/create")
    public ResponseEntity<?> create(@RequestBody PuppyDto puppyDto){
        puppyService.save(puppyDto);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 데이터가 생성되었습니다.");
    }

    // 특정 강아지 조회
    @GetMapping("/puppy/{puppy_id}")
    public ResponseEntity<?> readPuppy(@PathVariable Long puppy_id){
        PuppyDto puppyDto = puppyService.readPuppyById(puppy_id);
        return ResponseEntity.status(HttpStatus.OK).body(puppyDto);
    }
}
