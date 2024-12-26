package t3h.vn.testonline.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "topic")
public class TopicEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private SubjectEntity subject;

    @OneToMany(mappedBy = "topic")
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "topic")
    private List<ExamEntity> exams = new ArrayList<>();

    @Column
    private String name;
}
