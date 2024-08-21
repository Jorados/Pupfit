package jorados.pupfit.repository;

import java.util.*;
import jorados.pupfit.entity.Puppy;
import jorados.pupfit.entity.User;
import jorados.pupfit.entity.UserPuppy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPuppyRepository extends JpaRepository<UserPuppy, Long> {

    UserPuppy findByUserAndPuppy(User user, Puppy puppy);

    List<UserPuppy> findByUser(User user);

    @Query("select up" +
            " from UserPuppy up" +
            " join fetch up.puppy p" +
            " where up.user = :user")
    Page<UserPuppy> findUserPuppyWithPuppyByUser(@Param("user") User user, Pageable pageable);


    @Query("select up" +
            " from UserPuppy up" +
            " join fetch up.user u" +
            " join fetch up.puppy p" +
            " where up.id = :userPuppyId")
    Optional<UserPuppy> findUserPuppyWithUserAndPuppyByUserPuppyId(@Param("userPuppyId") Long userPuppyId);

}
