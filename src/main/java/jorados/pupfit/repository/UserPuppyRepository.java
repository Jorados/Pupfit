package jorados.pupfit.repository;

import java.util.*;
import jorados.pupfit.entity.Puppy;
import jorados.pupfit.entity.User;
import jorados.pupfit.entity.UserPuppy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPuppyRepository extends JpaRepository<UserPuppy, Long> {

    UserPuppy findByUserAndPuppy(User user, Puppy puppy);

    List<UserPuppy> findByUser(User user);
}
