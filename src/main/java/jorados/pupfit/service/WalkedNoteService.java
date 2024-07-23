package jorados.pupfit.service;

import jorados.pupfit.dto.WalkedNoteDto;
import jorados.pupfit.entity.UserPuppy;
import jorados.pupfit.entity.WalkedNote;
import jorados.pupfit.repository.UserPuppyRepository;
import jorados.pupfit.repository.WalkedNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalkedNoteService {

    private final WalkedNoteRepository walkedNoteRepository;
    private final UserPuppyRepository userPuppyRepository;

    // 생성
    @Transactional
    public void createWalkedNote(Long userPuppy, WalkedNoteDto walkedNoteDto) {
        UserPuppy findUserPuppy = userPuppyRepository.findById(userPuppy).orElseThrow(() -> new IllegalArgumentException());
        WalkedNote walkedNote = WalkedNote.builder()
                .walked(walkedNoteDto.isWalked())
                .walkedDate(walkedNoteDto.getWalkedDate())
                .walkedContent(walkedNoteDto.getWalkedContent())
                .userPuppy(findUserPuppy)
                .build();
        walkedNoteRepository.save(walkedNote);
    }

    // 산책기록 모두 모두 조회
    public List<WalkedNoteDto> realAllWalkedNote(){
        List<WalkedNote> findAllWalkedNote = walkedNoteRepository.findAll();
        return findAllWalkedNote.stream().map(walkedNote -> {
            WalkedNoteDto walkedNoteDto = WalkedNoteDto.builder()
                    .id(walkedNote.getId())
                    .walked(walkedNote.isWalked())
                    .walkedContent(walkedNote.getWalkedContent())
                    .walkedDate(walkedNote.getWalkedDate())
                    .build();
            return walkedNoteDto;
        }).collect(Collectors.toList());
    }

    // 특정 산책 기록 조회
    public WalkedNoteDto readWalkedNote(Long walkedNoteId) {
        WalkedNote findWalkedNote = walkedNoteRepository.findById(walkedNoteId).orElseThrow(() -> new IllegalArgumentException());

        WalkedNoteDto walkedNoteDto = WalkedNoteDto.builder()
                .walked(findWalkedNote.isWalked())
                .walkedContent(findWalkedNote.getWalkedContent())
                .walkedDate(findWalkedNote.getWalkedDate())
                .build();
        return walkedNoteDto;
    }

    // 수정
    @Transactional
    public void updateWalkedNote(Long walkedNoteId, WalkedNoteDto walkedNoteDto){
        WalkedNote findWalkedNote = walkedNoteRepository.findById(walkedNoteId).orElseThrow(() -> new IllegalArgumentException());
        findWalkedNote.edit(walkedNoteDto);
    }

    // 삭제
    @Transactional void deleteWalkedNote(Long walkedNoteId){
        WalkedNote findWalkedNote = walkedNoteRepository.findById(walkedNoteId).orElseThrow(() -> new IllegalArgumentException());
        walkedNoteRepository.delete(findWalkedNote);
    }
}
