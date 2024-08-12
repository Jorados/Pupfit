package jorados.pupfit.service;

import jorados.pupfit.dto.UserPuppyDto;
import jorados.pupfit.dto.WalkedNoteDto;
import jorados.pupfit.entity.UserPuppy;
import jorados.pupfit.entity.WalkedNote;
import jorados.pupfit.error.CustomNotFoundException;
import jorados.pupfit.repository.UserPuppyRepository;
import jorados.pupfit.repository.WalkedNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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

    // 산책 필요 상태를 알기위한 userPuppyId로 walked가 true이면서 walkedNote가 최신인것 1개 조회
    // null 인 경우 처리도 해줘야한다.
    public WalkedNoteDto readWalkedNoteByUserPuppyId(Long userPuppyId){

        Optional<List<WalkedNote>> findWalkedNote = walkedNoteRepository.findWalkedNoteByUserPuppyId(userPuppyId);

        if (findWalkedNote.isEmpty() || findWalkedNote.get().isEmpty()) {
            throw new CustomNotFoundException("산책 필요상태를 조회하기 위한 산책 정보");
        }

        WalkedNoteDto walkedNoteDto = WalkedNoteDto.builder()
                .walkedDate(findWalkedNote.get().get(0).getWalkedDate())
                .build();
        return walkedNoteDto;
    }

    public int CountReadWalkedNoteByUserPuppyId(Long userPuppyId){
        int count = walkedNoteRepository.CountReadWalkedNoteByUserPuppyId(userPuppyId);
        return count;
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
