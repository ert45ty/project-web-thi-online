package t3h.vn.testonline.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.request.CommentDto;
import t3h.vn.testonline.entities.CommentEntity;
import t3h.vn.testonline.repository.CommentRepo;
import t3h.vn.testonline.service.CommentService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final TopicServiceImpl topicService;
    private final UserServiceImpl userService;

    public Page<CommentEntity> getAllComments(int page, int perpage){
        Pageable pageable = PageRequest.of(page - 1, perpage);
        return commentRepo.findAllByContentContainingOrderByIdDesc("" ,pageable);
    }

    public CommentEntity createComment(CommentDto commentDto, Long topicId, String username){
        CommentEntity commentEntity = new CommentEntity();

        BeanUtils.copyProperties(commentDto, commentEntity);
        commentEntity.setTopic(topicService.getById(topicId));
        commentEntity.setUser(userService.findByUsername(username));
        commentEntity.setCreated_at(LocalDateTime.now());
        return commentRepo.save(commentEntity);

    }

    public void delete(Long id){
        commentRepo.deleteById(id);
    }
}
