package jorados.pupfit.service;


import jorados.pupfit.dto.UserDto;
import jorados.pupfit.dto.request.UserRequest;
import jorados.pupfit.dto.response.UserResponse;
import jorados.pupfit.entity.User;
import jorados.pupfit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원 생성 (가입)
    @Transactional
    public void createUser(User userDto){
        User user = User.builder()
                .username(userDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
//                .nickname(userDto.getNickname())
//                .gender(userDto.getGender())
                .build();
        userRepository.save(user);
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
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("값없음"));

        return UserResponse.builder()
                .gender(findUser.getGender())
                .nickname(findUser.getNickname())
                .build();
    }

    // 회원 정보 수정
    @Transactional
    public void updateUser(Long userId, UserRequest userRequest){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("값 없음"));
        findUser.edit(userRequest);
    }

    // 회원 삭제
    @Transactional
    public void deleteUser(Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("에러에러"));
        userRepository.delete(findUser);
    }

}
