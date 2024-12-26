package t3h.vn.testonline.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name = "user")
public class UserEntity extends BaseEntity{

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String fullname;

    @Column
    private String image_name;

    @Column
    private String role;

    @Column
    @CreatedDate
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ResultEntity> results = new ArrayList<>();
}
