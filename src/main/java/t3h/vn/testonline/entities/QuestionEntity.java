package t3h.vn.testonline.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "question")
public class QuestionEntity extends BaseEntity{

    @OneToMany(mappedBy = "question")
    private List<OptionEntity> options = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<UserAnswerEntity> userAnswers = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "exam_id", nullable = false)
    private ExamEntity exam;

    @Column
    private String question_text;

    @Column
    private String image_name;
}
