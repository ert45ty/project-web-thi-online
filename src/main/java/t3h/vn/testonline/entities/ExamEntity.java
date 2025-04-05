package t3h.vn.testonline.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exam")
public class ExamEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;

    @Column
    private String title;

    @Column
    private Integer duration;

    @OneToMany(mappedBy = "exam")
    private List<QuestionEntity> questions = new ArrayList<>();

    @OneToMany(mappedBy = "exam")
    private List<ResultEntity> results = new ArrayList<>();

    @Column
    private Integer total_question;

    @Column
    private int status;
}
