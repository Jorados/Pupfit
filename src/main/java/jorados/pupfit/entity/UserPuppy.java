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

    private String puppy_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puppy_id", nullable = false)
    private Puppy puppy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "user_puppy", cascade = CascadeType.ALL)
    private List<WalkedNote> walked_note = new ArrayList<>();

    @CreatedDate
    @Column(name="created_at" , updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

}
