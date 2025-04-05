package t3h.vn.testonline.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity extends BaseEntity{

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String fullname;

    @Column
    private String image_name;

    @Column
    private String role;

    @Column
    private Integer status;

    @Column
    private String code;

    @Column
    @CreatedDate
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "user")
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ResultEntity> results = new ArrayList<>();
}
