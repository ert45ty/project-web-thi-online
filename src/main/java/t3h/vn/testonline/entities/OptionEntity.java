package t3h.vn.testonline.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "`option`")
public class OptionEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @OneToMany(mappedBy = "option")
    private List<UserAnswerEntity> userAnswers = new ArrayList<>();

    @Column
    private String option_text;

    @Column
    private Boolean is_correct;
}
