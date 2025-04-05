package t3h.vn.testonline.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_answer")
public class UserAnswerEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "result_id", nullable = false)
    private ResultEntity result;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private OptionEntity option;
}
