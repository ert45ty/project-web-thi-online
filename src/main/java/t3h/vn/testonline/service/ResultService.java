package t3h.vn.testonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.ResultEntity;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.repository.ResultRepo;

@Service
public class ResultService {

    @Autowired
    ResultRepo resultRepo;
    @Autowired
    ExamService examService;
    @Autowired
    UserService userService;

    public ResultEntity save(Long userId, Long examId, ResultEntity resultEntity){
        ExamEntity examEntity = examService.getExamById(examId);
        UserEntity userEntity = userService.getById(userId);
        resultEntity.setExam(examEntity);
        resultEntity.setUser(userEntity);
        return resultRepo.save(resultEntity);
    }
}
