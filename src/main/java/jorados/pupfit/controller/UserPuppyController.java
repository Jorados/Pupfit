package jorados.pupfit.controller;


import jorados.pupfit.service.UserPuppyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userPuppy")
public class UserPuppyController {

    private final UserPuppyService userPuppyService;


}
