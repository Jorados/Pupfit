package jorados.pupfit.entity;


import jakarta.persistence.*;
import jorados.pupfit.entity.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id; // DB 넘버

    //@Column(unique = true,nullable = false,length=20)
    private String username; // 시큐리티 로그인 ID

    @Column(nullable = false, length = 60)
    private String password; // 암호

    @Column(nullable = false, length = 20)
    private String nickname; // 닉네임

    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별 ("M","W")

    @CreatedDate
    @Column(name="created_at" , updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPuppy> user_puppy;
}
