package jorados.pupfit.service;


import jorados.pupfit.dto.UserDto;
import jorados.pupfit.dto.request.UserRequest;
import jorados.pupfit.dto.response.UserResponse;
import jorados.pupfit.entity.User;
import jorados.pupfit.error.DuplicateException;
import jorados.pupfit.error.UserNotFoundException;
import jorados.pupfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원 생성 (가입)
    @Transactional
    public void createUser(User requestUser){
        validateDuplicateMember(requestUser);

        String encPassword = bCryptPasswordEncoder.encode(requestUser.getPassword());

        User user = User.builder()
                .username(requestUser.getUsername())
                .password(encPassword)
                .nickname(requestUser.getNickname())
                .gender(requestUser.getGender())
                .build();
        userRepository.save(user);
        log.info("회원 가입 성공");
    }

    // 회원 모두 조회
    public List<UserDto> readAllUser(){
        List<User> findAllUser = userRepository.findAll();
        return findAllUser.stream().map(user->{
            UserDto userDto = UserDto.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .nickname(user.getNickname())
                    .gender(user.getGender())
                    .build();
            return userDto;
        }).collect(Collectors.toList());
    }

    // 특정 회원 조회
    public UserResponse readUser(Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        return UserResponse.builder()
                .gender(findUser.getGender())
                .nickname(findUser.getNickname())
                .build();
    }

    // 회원 정보 수정
    @Transactional
    public void updateUser(Long userId, UserRequest userRequest){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        findUser.edit(userRequest);
    }

    // 회원 삭제
    @Transactional
    public void deleteUser(Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        userRepository.delete(findUser);
    }

    // 회원 중복체크 메서드
    public void validateDuplicateMember(User user){
        List<User> findUserName = userRepository.findAllByUsername(user.getUsername());
        User findNickName = userRepository.findByNickName(user.getNickname());

        // username (로그인 id) 겹치는 경우
        if(!findUserName.isEmpty()){
            log.info("회원 아이디 겹침");
            throw  new DuplicateException();
        }

        // nickname 겹치는 경우
        if(findNickName != null){
            log.info("회원 닉네임 겹침");
            throw  new DuplicateException();
        }
    }

}
