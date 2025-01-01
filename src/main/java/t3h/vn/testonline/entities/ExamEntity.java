package t3h.vn.testonline.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
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
}
