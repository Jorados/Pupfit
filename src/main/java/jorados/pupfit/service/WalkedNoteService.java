package jorados.pupfit.service;

import jorados.pupfit.dto.UserPuppyDto;
import jorados.pupfit.dto.WalkedNoteDto;
import jorados.pupfit.entity.UserPuppy;
import jorados.pupfit.entity.WalkedNote;
import jorados.pupfit.error.CustomNotFoundException;
import jorados.pupfit.repository.UserPuppyRepository;
import jorados.pupfit.repository.WalkedNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalkedNoteService {

    private final WalkedNoteRepository walkedNoteRepository;
    private final UserPuppyRepository userPuppyRepository;

    // 생성
    @Transactional
    public void createWalkedNote(WalkedNoteDto walkedNoteDto) {
        UserPuppy findUserPuppy = userPuppyRepository.findById(walkedNoteDto.getUserPuppyId()).orElseThrow(() -> new CustomNotFoundException("유저-강아지 정보"));
        WalkedNote walkedNote = WalkedNote.builder()
                .walked(walkedNoteDto.isWalked())
                .walkedDate(walkedNoteDto.getWalkedDate())
                .walkedContent(walkedNoteDto.getWalkedContent())
                .userPuppy(findUserPuppy)
                .build();
        walkedNoteRepository.save(walkedNote);
    }

    // 특정 사용자의 산책 기록 모두 조회
    public Map<Long,List<WalkedNoteDto>> readAllWalkedNote(List<UserPuppyDto> userPuppyDtoList){
        // 각 유저-강아지 마다 여러개의 산책정보를 가질 수 있다.
        Map<Long,List<WalkedNoteDto>> walkedList = new HashMap<>();

        for(UserPuppyDto userPuppyDto : userPuppyDtoList){
            List<WalkedNote> findWalkedNoteList = walkedNoteRepository.findByUserPuppyIdOrderByIdAsc(userPuppyDto.getId());

            if(findWalkedNoteList.size() > 0){
                List<WalkedNoteDto> findWalkedNoteDtoList = findWalkedNoteList.stream().map(findWalkedNote -> {
                    WalkedNoteDto walkedNoteDto = WalkedNoteDto.builder()
                            .id(findWalkedNote.getId())
                            .walked(findWalkedNote.isWalked())
                            .walkedContent(findWalkedNote.getWalkedContent())
                            .walkedDate(findWalkedNote.getWalkedDate())
                            .userPuppyId(findWalkedNote.getUserPuppy().getId())
                            .puppyName(userPuppyDto.getPuppyName() )
                            .puppyPersonalName(userPuppyDto.getPuppyPersonalName())
                            .build();
                    return walkedNoteDto;
                }).collect(Collectors.toList());

                walkedList.put(userPuppyDto.getId(),findWalkedNoteDtoList);
            }
        }

        if(walkedList.isEmpty()){
            throw new CustomNotFoundException("산책 정보");
        }

        return walkedList;
    }

    // 특정 산책 기록 조회
    public WalkedNoteDto readWalkedNote(Long walkedNoteId) {
        WalkedNote findWalkedNote = walkedNoteRepository.findById(walkedNoteId).orElseThrow(() -> new CustomNotFoundException("산책 정보를"));

        WalkedNoteDto walkedNoteDto = WalkedNoteDto.builder()
                .walked(findWalkedNote.isWalked())
                .walkedContent(findWalkedNote.getWalkedContent())
                .walkedDate(findWalkedNote.getWalkedDate())
                .build();
        return walkedNoteDto;
    }

    // 수정
    @Transactional
    public void updateWalkedNote(WalkedNoteDto walkedNoteDto) {
        WalkedNote findWalkedNote = walkedNoteRepository.findById(walkedNoteDto.getId()).orElseThrow(() -> new CustomNotFoundException("산책 정보를"));
        findWalkedNote.edit(walkedNoteDto);
    }

    // 삭제
    @Transactional
    public void deleteWalkedNote(Long walkedNoteId){
        WalkedNote findWalkedNote = walkedNoteRepository.findById(walkedNoteId).orElseThrow(() -> new CustomNotFoundException("산책 정보를"));
        walkedNoteRepository.delete(findWalkedNote);
    }
}
