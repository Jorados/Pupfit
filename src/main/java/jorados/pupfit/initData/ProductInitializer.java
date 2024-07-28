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
                .puppyName("푸들")
                .puppyType("푸들")
                .imgUrl("~~~~~")
                .walkLow(walkLowTime) // 최소시간
                .walkHigh(walkHighTime) // 최대시간
                .personality("소심함")
                .build();

        Puppy puppy2 = Puppy.builder()
                .puppyName("비숑")
                .puppyType("비숑")
                .imgUrl("~~~~~")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("친화력 좋음")
                .build();

        Puppy save1 = puppyRepository.save(puppy1);
        Puppy save2 = puppyRepository.save(puppy2);


        // 사용자-강아지 관계
        Puppy findPuppy1 = puppyRepository.findById(1L).orElseThrow(() -> new CustomNotFoundException("강아지")); // 푸들
        Puppy findPuppy2 = puppyRepository.findById(2L).orElseThrow(() -> new CustomNotFoundException("강아지")); // 비숑
        User findUser1 = userRepository.findById(1L).orElseThrow(() -> new CustomNotFoundException("사용자"));

        UserPuppy userPuppy = UserPuppy.builder()
                .puppyName("푸들")
                .user(findUser1)
                .puppy(findPuppy1)
                .build();

        UserPuppy userPuppy2 = UserPuppy.builder()
                .puppyName("비숑")
                .user(findUser1)
                .puppy(findPuppy2)
                .build();

        userPuppyRepository.save(userPuppy);
        userPuppyRepository.save(userPuppy2);

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

        WalkedNote walkedNote3 = WalkedNote.builder()
                .walked(true)
                .walkedContent("어제도 푸들과 함께 산책을 했다.")
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
        walkedNoteRepository.save(walkedNote3);
        walkedNoteRepository.save(walkedNote4);
        walkedNoteRepository.save(walkedNote5);
    }
}
