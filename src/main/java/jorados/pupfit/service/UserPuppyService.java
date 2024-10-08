package jorados.pupfit.service;

import jorados.pupfit.dto.UserPuppyDto;
import jorados.pupfit.entity.Puppy;
import jorados.pupfit.entity.User;
import jorados.pupfit.entity.UserPuppy;
import jorados.pupfit.error.CustomNotFoundException;
import jorados.pupfit.repository.PuppyRepository;
import jorados.pupfit.repository.UserPuppyRepository;
import jorados.pupfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPuppyService {
    private final UserPuppyRepository userPuppyRepository;
    private final UserRepository userRepository;
    private final PuppyRepository puppyRepository;

    // 생성 ( 회원이 강아지를 기르게 되는 경우 )
    @Transactional
    public void createUserPuppy(Long userId, UserPuppyDto userPuppyDto) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new CustomNotFoundException("유저 정보"));
        Puppy findPuppy = puppyRepository.findById(userPuppyDto.getPuppyId()).orElseThrow(() -> new CustomNotFoundException("강아지 정보"));

        UserPuppy userPuppy = UserPuppy.builder()
                .user(findUser)
                .puppy(findPuppy)
                .puppyPersonalName(userPuppyDto.getPuppyPersonalName())
                .build();

        userPuppyRepository.save(userPuppy);
    }

    // 모두 조회 ( 회원 정보로만 정회 )
    public List<UserPuppyDto> readAllByUserId(Long userId, Pageable pageable) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new CustomNotFoundException("유저 정보"));

        // fetch join으로 sql 최적화 N번->1번
        Page<UserPuppy> findUserPuppy = userPuppyRepository.findUserPuppyWithPuppyByUser(findUser,pageable);

        if(findUserPuppy.isEmpty()){
            throw new CustomNotFoundException("유저-강아지 정보");
        }

        return findUserPuppy.stream().map(userPuppy->{
            UserPuppyDto userPuppyDto = UserPuppyDto.builder()
                    .id(userPuppy.getId())
                    .userId(userPuppy.getUser().getId())
                    .puppyId(userPuppy.getPuppy().getId())
                    .puppyName(userPuppy.getPuppy().getPuppyName())
                    .puppyPersonalName(userPuppy.getPuppyPersonalName())
                    .build();
            return userPuppyDto;
        }).collect(Collectors.toList());
    }

    // 특정 데이터 조회 ( 회원아이디랑 강아지아이디랑 다 필요 )
    public UserPuppyDto readById(Long userPuppyId) {

        // fetch join으로 sql 최적화 N번->1번
        UserPuppy findUserPuppy = userPuppyRepository.findUserPuppyWithUserAndPuppyByUserPuppyId(userPuppyId).orElseThrow(() -> new CustomNotFoundException("유저-강아지 정보"));

        UserPuppyDto userPuppyDto = UserPuppyDto.builder()
                .puppyId(findUserPuppy.getPuppy().getId())
                .userId(findUserPuppy.getUser().getId())
                .puppyPersonalName(findUserPuppy.getPuppyPersonalName())
                .build();
        return userPuppyDto;
    }


    // 데이터 수정 ( 강아지 이름이 수정 될 경우 ) 필요한가?
    // @Transactional
    // public void updateUserPuppy() {}


    // 데이터 삭제 ( 해당 user 혹은 puppy 가 삭제될 경우 )
    @Transactional
    public void deleteUserPuppy(Long userPuppyId) {
        UserPuppy findUserPuppy = userPuppyRepository.findById(userPuppyId).orElseThrow(() -> new CustomNotFoundException("유저-강아지 정보"));
        userPuppyRepository.delete(findUserPuppy);
    }
}
