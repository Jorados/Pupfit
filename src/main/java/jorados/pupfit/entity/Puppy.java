package jorados.pupfit.entity;

import jakarta.persistence.*;
import jorados.pupfit.dto.PuppyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "puppy")
@Builder
public class Puppy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "puppy_id", nullable = false)
    private Long id;

    @Column(name = "puppy_name")
    private String puppyName; // 이름

    @Column(name = "puppy_type")
    private String puppyType; // 종

    @Column(name = "img_url")
    private String imgUrl; // 이미지

    @Column(name = "walk_low")
    private Date walkLow; // 산책 시작 시간

    @Column(name = "walk_high")
    private Date walkHigh; // 산책 마감 시간

    @Column(name = "personality")
    private String personality; // 성격

    @CreatedDate
    @Column(name="created_at" , updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "puppy",cascade = CascadeType.ALL)
    private List<UserPuppy> user_puppy;

    public void edit(PuppyDto puppyDto){
        this.puppyName = puppyDto.getPuppyName();
        this.puppyType = puppyDto.getPuppyType();
        this.imgUrl = puppyDto.getImgUrl();
        this.personality = puppyDto.getPersonality();
        this.walkLow = puppyDto.getWalkLow();
        this.walkHigh = puppyDto.getWalkHigh();
    }

}
