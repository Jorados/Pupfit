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
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "walked_note")
public class WalkedNote {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "walked_note_id")
    private Long id;

    private Date walked_date; // 산책 날짜

    private String walked_content; // 산책 내용

    private boolean walked; // 걷기 유무

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_puppy_id")
    private UserPuppy user_puppy;

    @CreatedDate
    @Column(name="created_at" , updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

}
