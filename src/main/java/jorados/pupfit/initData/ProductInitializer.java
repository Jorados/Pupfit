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
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductInitializer {

    private final UserRepository userRepository;
    private final PuppyRepository puppyRepository;
    private final UserPuppyRepository userPuppyRepository;
    private final WalkedNoteRepository walkedNoteRepository;

    private static Date createTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0); // 밀리초를 0으로 설정

        return calendar.getTime();
    }


    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        User user1 = User.builder()
                .username("aaa")
                .password("aaa")
                .nickname("조성진")
                .gender(Gender.MALE)
                .build();

        User user2 = User.builder()
                .username("bbb")
                .password("bbb")
                .nickname("테스트")
                .gender(Gender.FEMALE)
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        Date walkLowTime = createTime(10, 30); // 10:30 시간만 설정
        Date walkHighTime = createTime(18, 30); // 18:30 시간만 설정

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
