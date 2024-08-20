package jorados.pupfit.repository;

import jorados.pupfit.entity.WalkedNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WalkedNoteRepository extends JpaRepository<WalkedNote, Long> {

    List<WalkedNote> findByUserPuppyIdOrderByWalkedDateDesc(Long userPuppyId);

    @Query("select w" +
            " from WalkedNote w" +
            " where w.userPuppy.id = :userPuppyId" +
            " order by w.walkedDate desc")
    Page<WalkedNote> findPagedByUserPuppyIdOrderByWalkedDateDesc(@Param("userPuppyId") Long userPuppyId, Pageable pageable);

    // userPuppyId를 이용해서 최신 산책 시간 확인
    @Query("select w" +
            " from WalkedNote w" +
            " where w.walked = true and w.userPuppy.id =:userPuppyId" +
            " order by w.walkedDate desc")
    Optional<List<WalkedNote>> findWalkedNoteByUserPuppyId(@Param("userPuppyId") Long userPuppyId);


    @Query("select count(w)" +
            " from WalkedNote w" +
            " where w.userPuppy.id =:userPuppyId")
    int CountReadWalkedNoteByUserPuppyId(@Param("userPuppyId") Long userPuppyId);


    @Query("SELECT wn" +
            " FROM WalkedNote wn" +
            " JOIN FETCH wn.userPuppy up" +
            " JOIN FETCH up.puppy p" +
            " JOIN FETCH up.user u" +
            " WHERE u.id = :userId" +
            " ORDER BY wn.walkedDate DESC")
    Page<WalkedNote> findWalkedNoteWithUserPuppyAndPuppyAndUserByUserId(@Param("userId") Long userId, Pageable pageable);
}
