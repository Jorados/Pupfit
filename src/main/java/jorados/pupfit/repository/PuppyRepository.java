package jorados.pupfit.repository;

import jorados.pupfit.entity.Puppy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PuppyRepository extends JpaRepository<Puppy,Long> {

    List<Puppy> findByPuppyName(String puppyName);
}
