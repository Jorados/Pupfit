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
import java.util.ArrayList;
import java.util.List;

// 회원과 강아지의 결합 정보를 저장하기 위한 테이블
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_puppy")
@Builder
public class UserPuppy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_puppy_id")
    private Long id; // DB 넘버

    private String puppyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puppy_id", nullable = false)
    private Puppy puppy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "userPuppy", cascade = CascadeType.ALL)
    private List<WalkedNote> walkedNote = new ArrayList<>();

    @CreatedDate
    @Column(name="created_at" , updatable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

}
