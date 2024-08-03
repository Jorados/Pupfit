package jorados.pupfit.repository;

import jorados.pupfit.entity.WalkedNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalkedNoteRepository extends JpaRepository<WalkedNote, Long> {

    List<WalkedNote> findByUserPuppyIdOrderByIdAsc(Long userPuppyId);

}
