package jorados.pupfit.initData;

import jorados.pupfit.entity.Puppy;
import jorados.pupfit.entity.User;
import jorados.pupfit.entity.UserPuppy;
import jorados.pupfit.entity.WalkedNote;
import jorados.pupfit.entity.type.Gender;
import jorados.pupfit.error.CustomNotFoundException;
import jorados.pupfit.repository.PuppyRepository;
import jorados.pupfit.repository.UserPuppyRepository;
import jorados.pupfit.repository.UserRepository;
import jorados.pupfit.repository.WalkedNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class ProductInitializer {

    private final UserRepository userRepository;
    private final PuppyRepository puppyRepository;
    private final UserPuppyRepository userPuppyRepository;
    private final WalkedNoteRepository walkedNoteRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public static LocalTime createTime(int hour, int minute, int second) {
        LocalTime time = LocalTime.of(hour, minute, second); // 특정 시간
        return time; // LocalTime 객체 생성
    }


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        User user1 = User.builder()
                .username("aaa")
                .password(bCryptPasswordEncoder.encode("aaa"))
                .nickname("조성진")
                .gender(Gender.MALE)
                .build();

        User user2 = User.builder()
                .username("bbb")
                .password(bCryptPasswordEncoder.encode("bbb"))
                .nickname("테스트")
                .gender(Gender.FEMALE)
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        LocalTime walkLowTime = createTime(10, 30, 00); // 10:30 시간만 설정
        LocalTime walkHighTime = createTime(18, 30, 00); // 18:30 시간만 설정

        Puppy puppy1 = Puppy.builder()
                .puppyName("성진1")
                .puppyType("푸들")
                .imgUrl("https://i.namu.wiki/i/j2Gz_JHE5A1MlSaglc2dyOecDVT0FXdjUbdzkPEjndZ9gsERUnl0G6rDs8d0LyOf2WeS71GS1yokVhs4EEcLVw.webp")
                .walkLow(walkLowTime) // 최소시간
                .walkHigh(walkHighTime) // 최대시간
                .personality("활기차고, 적극적이며, 호기심이 많은 성격을 가지고 있습니다. 주인에게 매우 충실하며, 높은 지능과 교육성으로 인해 훈련이 쉽습니다.")
                .build();

        Puppy puppy2 = Puppy.builder()
                .puppyName("성진2")
                .puppyType("비숑")
                .imgUrl("https://m.milliong.co.kr/file_data/milliong01/2021/12/30/66de6e83edfa9d14fc9cd3376631e9a3.jpg")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("활기차고, 적극적이며, 호기심이 많은 성격을 가지고 있습니다. 주인에게 매우 충실하며, 높은 지능과 교육성으로 인해 훈련이 쉽습니다.")
                .build();

        Puppy puppy3 = Puppy.builder()
                .puppyName("성진3")
                .puppyType("포메라니안")
                .imgUrl("https://i.namu.wiki/i/7IJj6iEgr4a41vfl5vOyUGtHAoC2ZQZABnuNua26JeAXjThMcrjJuul8E0CWmAfVB_m_8e535REXuU47IxU2Cg.webp")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("활기차고, 적극적이며, 호기심이 많은 성격을 가지고 있습니다. 주인에게 매우 충실하며, 높은 지능과 교육성으로 인해 훈련이 쉽습니다.")
                .build();

        Puppy save1 = puppyRepository.save(puppy1);
        Puppy save2 = puppyRepository.save(puppy2);
        Puppy save3 = puppyRepository.save(puppy3);


        // 사용자-강아지 관계
        Puppy findPuppy1 = puppyRepository.findById(1L).orElseThrow(() -> new CustomNotFoundException("강아지")); // 푸들
        Puppy findPuppy2 = puppyRepository.findById(2L).orElseThrow(() -> new CustomNotFoundException("강아지")); // 비숑
        Puppy findPuppy3 = puppyRepository.findById(3L).orElseThrow(() -> new CustomNotFoundException("강아지")); // 비숑
        User findUser1 = userRepository.findById(1L).orElseThrow(() -> new CustomNotFoundException("사용자"));

        UserPuppy userPuppy = UserPuppy.builder()
                .puppyName("성진1")
                .user(findUser1)
                .puppy(findPuppy1)
                .build();

        UserPuppy userPuppy2 = UserPuppy.builder()
                .puppyName("성진2")
                .user(findUser1)
                .puppy(findPuppy2)
                .build();

        UserPuppy userPuppy3 = UserPuppy.builder()
                .puppyName("성진2")
                .user(findUser1)
                .puppy(findPuppy3)
                .build();

        userPuppyRepository.save(userPuppy);
        userPuppyRepository.save(userPuppy2);
        userPuppyRepository.save(userPuppy3);

        // 산책기록
        UserPuppy findUserPuppy1 = userPuppyRepository.findById(1L).orElseThrow(() -> new CustomNotFoundException("산책 정보"));
        UserPuppy findUserPuppy2 = userPuppyRepository.findById(2L).orElseThrow(() -> new CustomNotFoundException("산책 정보"));

        WalkedNote walkedNote1 = WalkedNote.builder()
                .walked(true)
                .walkedContent("오늘은 푸들과 함께 산책을 했다.")
                .walkedDate(walkLowTime)
                .userPuppy(findUserPuppy1)
                .build();

        WalkedNote walkedNote2 = WalkedNote.builder()
                .walked(true)
                .walkedContent("내일은 푸들과 함께 산책을 했다.")
                .walkedDate(walkLowTime)
                .userPuppy(findUserPuppy1)
                .build();

        WalkedNote walkedNote4 = WalkedNote.builder()
                .walked(true)
                .walkedContent("오늘은 비숑과 함께 산책을 했다.")
                .walkedDate(walkLowTime)
                .userPuppy(findUserPuppy2)
                .build();

        WalkedNote walkedNote5 = WalkedNote.builder()
                .walked(true)
                .walkedContent("내일은 비숑과 함께 산책을 했다.")
                .walkedDate(walkLowTime)
                .userPuppy(findUserPuppy2)
                .build();

        walkedNoteRepository.save(walkedNote1);
        walkedNoteRepository.save(walkedNote2);
        walkedNoteRepository.save(walkedNote4);
        walkedNoteRepository.save(walkedNote5);
    }
}
