package jorados.pupfit.service;

import jorados.pupfit.dto.PuppyDto;
import jorados.pupfit.entity.Puppy;
import jorados.pupfit.repository.PuppyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PuppyService {
    private final PuppyRepository puppyRepository;

    // 강아지 생성
    @Transactional
    public void save(PuppyDto puppyDto){
        Puppy puppy = Puppy.builder()
            .puppyName(puppyDto.getPuppyName())
            .puppyType(puppyDto.getPuppyType())
            .imgUrl(puppyDto.getImgUrl())
            .personality(puppyDto.getPersonality())
            .walkLow(puppyDto.getWalkLow())
            .walkHigh(puppyDto.getWalkHigh())
            .build();

        puppyRepository.save(puppy);
    }

    // 모든 강아지 조회
    public List<PuppyDto> readAllPuppy(){
        List<Puppy> puppyAll = puppyRepository.findAll();

        return puppyAll.stream().map(puppy -> {
            PuppyDto puppyDto = PuppyDto.builder()
                    .id(puppy.getId())
                    .puppyName(puppy.getPuppyName())
                    .puppyType(puppy.getPuppyType())
                    .imgUrl(puppy.getImgUrl())
                    .walkLow(puppy.getWalkLow())
                    .walkHigh(puppy.getWalkHigh())
                    .personality(puppy.getPersonality())
                    .build();
            return puppyDto;
        }).collect(Collectors.toList());
    }

    // 특정 강아지 조회
    public PuppyDto readPuppyById(Long puppyId){
        Puppy puppy = puppyRepository.findById(puppyId).orElseThrow(() -> new IllegalArgumentException("없는 값"));

        PuppyDto puppyDto =PuppyDto.builder()
                .id(puppy.getId())
                .puppyName(puppy.getPuppyName())
                .puppyType(puppy.getPuppyType())
                .imgUrl(puppy.getImgUrl())
                .walkLow(puppy.getWalkLow())
                .walkHigh(puppy.getWalkHigh())
                .personality(puppy.getPersonality())
                .build();
        return puppyDto;
    }

    // 강아지 정보 수정
    @Transactional
    public void updatePuppyById(Long puppyId, PuppyDto puppyDto){
        Puppy findPuppy = puppyRepository.findById(puppyId).orElseThrow(() -> new IllegalArgumentException("없는 값."));
        findPuppy.edit(puppyDto);
    }

    // delete
    @Transactional
    public void deletePuppyById(Long puppyId){
        Puppy findPuppy = puppyRepository.findById(puppyId).orElseThrow(() -> new IllegalArgumentException("없는 값."));
        puppyRepository.delete(findPuppy);
    }

}
