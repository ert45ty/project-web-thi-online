package t3h.vn.testonline.service;

import org.springframework.data.domain.Page;
import t3h.vn.testonline.dto.request.CommentDto;
import t3h.vn.testonline.entities.CommentEntity;

import java.time.LocalDateTime;

public interface CommentService {

    Page<CommentEntity> getAllComments(int page, int perpage);

    CommentEntity createComment(CommentDto commentDto, Long topicId, String username);

    void delete(Long id);
}
