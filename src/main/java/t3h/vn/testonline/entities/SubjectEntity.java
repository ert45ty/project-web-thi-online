package t3h.vn.testonline.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "subject")
public class SubjectEntity extends BaseEntity{
    @Column
    private String name;

    @OneToMany(mappedBy = "subject")
    private List<TopicEntity> topics = new ArrayList<>();
}
