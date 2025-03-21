package t3h.vn.testonline.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "result")
public class ResultEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private ExamEntity exam;

    @OneToMany(mappedBy = "result")
    private List<UserAnswerEntity> userAnswers = new ArrayList<>();

    @Column
    private Integer exam_duration;

    @Column
    private Float score;

    @Column
    private LocalDateTime created_at;

}
