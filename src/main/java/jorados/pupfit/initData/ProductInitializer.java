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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
                .puppyName("토이 푸들")
                .puppyType("반려견")
                .imgUrl("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT_6ekQ3BWIWBWoIcg7NgfLymv81hpvraSdDwfq28HWRrOt8yWE")
                .walkLow(walkLowTime) // 최소시간
                .walkHigh(walkHighTime) // 최대시간
                .personality("활기차고, 적극적이며, 호기심이 많은 성격을 가지고 있습니다. 주인에게 매우 충실하며, 높은 지능과 교육성으로 인해 훈련이 쉽습니다.")
                .build();

        Puppy puppy2 = Puppy.builder()
                .puppyName("비숑 프리제")
                .puppyType("반려견")
                .imgUrl("https://static.wixstatic.com/media/a27d24_f136357da1e54ff2a15ff3299e9de0f6~mv2.jpg/v1/fill/w_600,h_619,al_c,lg_1,q_85,enc_auto/a27d24_f136357da1e54ff2a15ff3299e9de0f6~mv2.jpg")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("활기차고, 적극적이며, 호기심이 많은 성격을 가지고 있습니다. 주인에게 매우 충실하며, 높은 지능과 교육성으로 인해 훈련이 쉽습니다.")
                .build();

        Puppy puppy3 = Puppy.builder()
                .puppyName("포메라니안")
                .puppyType("반려견")
                .imgUrl("https://i.namu.wiki/i/7IJj6iEgr4a41vfl5vOyUGtHAoC2ZQZABnuNua26JeAXjThMcrjJuul8E0CWmAfVB_m_8e535REXuU47IxU2Cg.webp")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("활기차고, 적극적이며, 호기심이 많은 성격을 가지고 있습니다. 주인에게 매우 충실하며, 높은 지능과 교육성으로 인해 훈련이 쉽습니다.")
                .build();

        Puppy puppy4 = Puppy.builder()
                .puppyName("티베탄 테리어")
                .puppyType("실용견")
                .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/Tibet_Terrier_Bennie_%28cropped%29.jpg/442px-Tibet_Terrier_Bennie_%28cropped%29.jpg")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("전설에 따르면 티베탄 테리어는 2000년 이상 순혈종으로 유지되었다. 티베탄 테리어는 행운 부적, 마스코트, 감시견, 목축개, 동료로 키워졌다. 산 측면에 떨어진 문헌을 찾는 일에 쓰이기도 한다.")
                .build();

        Puppy puppy5 = Puppy.builder()
                .puppyName("티베탄 테리어")
                .puppyType("테리어견")
                .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/01_Wire_Fox_terrier.jpg/480px-01_Wire_Fox_terrier.jpg")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("어깨높이 약 40cm의 작은 개인데 본래는 사냥개로서 특히 여우사냥에 많이 쓰였으므로 이 이름이 붙여졌다. 영국 원산의 애완견으로 예민한 감각과 민첩한 행동, 총명한 두뇌를 겸비하고 있다. 애완용이 된 것은 19세기 말 경이다. 색깔은 흰 바탕에 검은색과 황갈색의 얼룩점이 있다. 입 끝이 길게 나오고 몸통이 짧은 것이 우수하다.")
                .build();

        Puppy puppy6 = Puppy.builder()
                .puppyName("골든 리트리버")
                .puppyType("조렵견")
                .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Golden_retriever.jpg/480px-Golden_retriever.jpg")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("골든 리트리버(영어: Golden Retriever)는 개의 품종 중 하나로, 골든 리트리버의 원산지는 영국의 스코틀랜드이다. 또한 골든 리트리버는 근육질의 몸에 건강하고 산뜻한 느낌을 준다. 금색의 윤기나는 털을 가지고 있는데, 속털은 방수성이고 겉털은 탄력성이 있다. 잡은 사냥감을 찾아오도록 훈련되었다. 겉털과 속털은 추위와 물로부터 몸을 보호해 주어 물 속의 사냥감을 잘 물어 온다. 수영을 잘하고 냄새를 잘 맡으며 쉽게 훈련시킬 수 있다. 몸집이 크지만 대소변을 가리면 집안에서도 키울 수 있다. 단지 너무 살이 찌지 않도록 성장기에 체중 관리를 해 주어야 한다. 장모지만 털빠짐이 매우 심한 견종이다.")
                .build();

        Puppy puppy7 = Puppy.builder()
                .puppyName("닥스훈트")
                .puppyType("수렵견")
                .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/2/27/Short-haired-Dachshund.jpg/480px-Short-haired-Dachshund.jpg")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("닥스훈트(영어: Dachshund)는 몸통이 길고 사지가 짧은 독일 원산의 개로서, 원래는 오소리 사냥의 전문개였다. 어깨높이가 21~27cm, 몸무게가 7~15kg이다. 이집트로부터 건너온 오래된 개의 품종으로 보인다. 단모종·강모종·장모종 등이 있으며, 성질은 영리하고 끈기가 있어서 애완용 개로 기르고 있다. 이 견종들의 사지가 짧고, 허리가 긴 이유는 종 전체가 연골발육부전증(Achondrodysplasia)이라는 유전병을 가지고 있기 때문이다. 이 유전병은 팔, 다리의 정상적 성장은 방해하나, 허리의 성장에는 영향을 주지 않기 때문에 닥스훈트 특유의 외관형성의 중요 요인으로 알려져있다. 성격이 밝고 명랑하며 지칠줄 모르는 근성이 있어 쉬지 않고 놀아주길 바란다. ")
                .build();

        Puppy puppy8 = Puppy.builder()
                .puppyName("비글")
                .puppyType("사역견")
                .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Beagle_Upsy.jpg/220px-Beagle_Upsy.jpg")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("비글(영어: beagle)은 개 품종의 하나이다. 외모는 억세어 보이며, 몸에 군더더기 살이 없고, 짧은 털이 빽빽하게 나 있다. 머리는 넓적하고, 귀는 부드러우며 길쭉하다. 체중은 약 8~14kg이다 그리고 작은비글은 4~5kg정도이다. 크기에 따라 두 종류로 나뉘는데, 포켓비글 종류는 어깨높이가 33cm이고, 다른 종류는 33-40cm로 그보다 크다. 17세기 영국에서 오늘날의 품종으로 개량했다. 냄새를 잘 맡아 토끼 사냥에 쓰이기도 한다. 민첩하고 붙임성이 있으나, 쉽게 훈련시킬 수 없어 애완용으로는 그리 적합하지 않아 전통적으로 사냥용으로 쓰였다. 최근에는 후각이 예민한 특성을 이용해 마약탐지견으로 활용하고 있다.")
                .build();

        Puppy puppy9 = Puppy.builder()
                .puppyName("스태퍼드셔 불 테리어")
                .puppyType("테리어견")
                .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/Staffordshire-bull-terrier-white-2748733.jpg/220px-Staffordshire-bull-terrier-white-2748733.jpg")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("스태퍼드셔 불 테리어(Staffordshire Bull Terrier) 또는 스태피(Staffie)는 잉글랜드 스태퍼드셔와 버밍엄 북부 지역의 기원이 되는, 털이 짧고 중간 크기의 견종이다. 이 종은 불독, 블랙 앤드 탠 테리어와의 교배를 통해 탄생하였다.")
                .build();

        Puppy puppy10 = Puppy.builder()
                .puppyName("브리트니")
                .puppyType("조렵견")
                .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/American_Brittany_standing.jpg/300px-American_Brittany_standing.jpg\n")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("'브리트니'라는 이름은 원산지인 프랑스 북서부의 브르타뉴 지역에서 유래되었으며, 갈색빛과 흰색 털을 가진 브라트니와 비슷한 개들의 이미지는 17세기의 태피스트리와 그림에서 처음 발견되었다. 브리트니에 대한 검증 가능한 최초의 기록은 1850년 'Davies'라는 목사가 쓴 사냥 설명문에서 비롯되었다. 1907년에 '보이(Boy)'라는 이름을 가진 갈색빛과 흰색 털을 가진 브리트니 수컷이 프랑스에 등록되었을 때 처음으로 품종으로 인정받았다. 그 후 미국은 1931년에 브리트니의 품종을 인정했고 1934년에 American Kennel Club에서 품종임이 승인되었으며 1982년 '스패니얼'은 공식적으로 이름에서 제거되었다.")
                .build();

        Puppy puppy11 = Puppy.builder()
                .puppyName("헤리어")
                .puppyType("수렵견")
                .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/Harrier_tricolour.jpg/200px-Harrier_tricolour.jpg")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("해리어(Harrier)는 중간 크기의 하운드종 사냥개이다. 해리어는 잉글리시 폭스하운드와 비슷하게 생겼는데, 잉글리시 폭스하운드 보다는 작지만, 비글만큼 작지는 않다. 사냥감을 쫓는 지구력이 매우 강해 이 개의 이름을 따서 전투기 이름도 해리어라고 지은 바가 있다. 어깨높이 48~53cm, 몸무게 18~23kg이다. 이 품종의 기대 수명은 보통 10년~12년이다. 성질이 온순하지만 독립심이 매우 강해 바쁘고 돌볼 시간이 별로 없는 사람에게 적합한 견종이다. 마치 고양이와 같은 성격을 지녔는데 매우 기분파이며 제 기분이 나쁠 때는 주인에게도 화를 내기도 한다. 제멋대로이고 애교도 없는 개지만 집을 지키는 것과 사냥개로 쓰기는 아주 적합하다.")
                .build();

        Puppy puppy12 = Puppy.builder()
                .puppyName("사모예드견")
                .puppyType("사역견")
                .imgUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/1/18/Samojed00.jpg/200px-Samojed00.jpg")
                .walkLow(walkLowTime)
                .walkHigh(walkHighTime)
                .personality("사모예드견(Samoyed dog)은 시베리아 원산으로 썰매용·가정용·반려용으로 많이 기른다. 북부 시베리아의 유목민 사모예드족이 옛날부터 기르던 썰매용 개로서, 부족의 이름을 따서 명명되었다. 19세기에 와서 미국과 영국의 북극 탐험가들에 의하여 발전되어 썰매를 끄는 개로서 그들과 함께 활약한 후 처음 서양 각국에 소개되었다. 우아한 용모와 대담한 성격, 썰매용으로 단련된 강력한 힘을 인정받아 세계에 알려지면서 세계의 304여개의 견종 사이에서 가장 비싼 견종이라고도 한다. 또, 이와 같이 북부 시베리아 쪽에서 살다보니 더위에 취약하며 털 빠짐에 유의해야한다. 흰백색의 털과 입 끝 부분이 살짝 올라가서 생기는 미소(Samoyed smile)가 특징이다. 이중모여서 단열과 청결을 유지하기 쉽지만 씻기가 힘들다. 몸무게 15-30kg, 어깨높이 48-58cm이다. 털은 두 가지로서 속털은 조밀하고 굵으며 양털처럼 부드러우나 표면의 털은 길고 다소 뻣뻣하며 목둘레와 앞가슴의 털은 우아하다. 털빛깔은 순백색 외에 크림색 및 담갈색도 있다. 순하고 친근하고 솔직하며, 기민하고 매우 발랄하다. 사냥 본능은 거의 없다. 그래서 공격적인 면도 없는데, 사교성이 매우 뛰어나서 경비견으로 활용할 수 없다.[1]")
                .build();

        Puppy save1 = puppyRepository.save(puppy1);
        Puppy save2 = puppyRepository.save(puppy2);
        Puppy save3 = puppyRepository.save(puppy3);
        Puppy save4 = puppyRepository.save(puppy4);
        Puppy save5 = puppyRepository.save(puppy5);
        Puppy save6 = puppyRepository.save(puppy6);
        Puppy save7 = puppyRepository.save(puppy7);
        Puppy save8 = puppyRepository.save(puppy8);
        Puppy save9 = puppyRepository.save(puppy9);
        Puppy save10 = puppyRepository.save(puppy10);
        Puppy save11 = puppyRepository.save(puppy11);
        Puppy save12 = puppyRepository.save(puppy12);


        // 사용자-강아지 관계
        Puppy findPuppy1 = puppyRepository.findById(1L).orElseThrow(() -> new CustomNotFoundException("강아지")); // 푸들
        Puppy findPuppy2 = puppyRepository.findById(2L).orElseThrow(() -> new CustomNotFoundException("강아지")); // 비숑
        Puppy findPuppy3 = puppyRepository.findById(3L).orElseThrow(() -> new CustomNotFoundException("강아지")); // 비숑
        User findUser1 = userRepository.findById(1L).orElseThrow(() -> new CustomNotFoundException("사용자"));

        UserPuppy userPuppy = UserPuppy.builder()
                .puppyPersonalName("성진1")
                .user(findUser1)
                .puppy(findPuppy1)
                .build();

        UserPuppy userPuppy2 = UserPuppy.builder()
                .puppyPersonalName("성진2")
                .user(findUser1)
                .puppy(findPuppy2)
                .build();

        UserPuppy userPuppy3 = UserPuppy.builder()
                .puppyPersonalName("성진2")
                .user(findUser1)
                .puppy(findPuppy3)
                .build();

        userPuppyRepository.save(userPuppy);
        userPuppyRepository.save(userPuppy2);
        userPuppyRepository.save(userPuppy3);

        // 산책기록
        UserPuppy findUserPuppy1 = userPuppyRepository.findById(1L).orElseThrow(() -> new CustomNotFoundException("산책 정보"));
        UserPuppy findUserPuppy2 = userPuppyRepository.findById(2L).orElseThrow(() -> new CustomNotFoundException("산책 정보"));

        String walkedDate = "2023-08-01 10:30:00";
        LocalDateTime parseWalkedDate = LocalDateTime.parse(walkedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        WalkedNote walkedNote1 = WalkedNote.builder()
                .walked(true)
                .walkedContent("오늘은 푸들과 함께 산책을 했다.")
                .walkedDate(parseWalkedDate)
                .userPuppy(findUserPuppy1)
                .build();

        WalkedNote walkedNote2 = WalkedNote.builder()
                .walked(true)
                .walkedContent("내일은 푸들과 함께 산책을 했다.")
                .walkedDate(parseWalkedDate)
                .userPuppy(findUserPuppy1)
                .build();

        WalkedNote walkedNote4 = WalkedNote.builder()
                .walked(true)
                .walkedContent("오늘은 비숑과 함께 산책을 했다.")
                .walkedDate(parseWalkedDate)
                .userPuppy(findUserPuppy2)
                .build();

        WalkedNote walkedNote5 = WalkedNote.builder()
                .walked(true)
                .walkedContent("내일은 비숑과 함께 산책을 했다.")
                .walkedDate(parseWalkedDate)
                .userPuppy(findUserPuppy2)
                .build();

        walkedNoteRepository.save(walkedNote1);
        walkedNoteRepository.save(walkedNote2);
        walkedNoteRepository.save(walkedNote4);
        walkedNoteRepository.save(walkedNote5);
    }
}
