package jorados.pupfit.entity;

import jakarta.persistence.*;
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

    private String puppy_name; // 이름
    private String puppy_type; // 종
    private String img_url; // 이미지
    private Date walk_low; // 산책 시작 시간
    private Date walk_high; // 산책 마감 시간
    private String personality; // 성격

    @CreatedDate
    @Column(name="created_at" , updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "puppy",cascade = CascadeType.ALL)
    private List<UserPuppy> user_puppy;

}
