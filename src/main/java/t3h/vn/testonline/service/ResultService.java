package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.ResultDto;
import t3h.vn.testonline.entities.ExamEntity;
import t3h.vn.testonline.entities.ResultEntity;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.repository.ResultRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    ResultRepo resultRepo;
    @Autowired
    ExamService examService;
    @Autowired
    UserService userService;

    public ResultEntity save(ResultDto resultDto, Long examId, Long userId){
        ResultEntity resultEntity = new ResultEntity();
        BeanUtils.copyProperties(resultDto, resultEntity);
        ExamEntity examEntity = examService.getExamByIdAndStatusIsLike(examId);
        UserEntity userEntity = userService.getById(userId);
        resultEntity.setExam(examEntity);
        resultEntity.setUser(userEntity);
        return resultRepo.save(resultEntity);
    }

    public ResultEntity getById(Long id){
        return resultRepo.getById(id);
    }

    public Page<ResultEntity> findResultByUserId(Long id, int page, int perpage){
        Pageable pageable = PageRequest.of(page - 1, perpage);
        return resultRepo.findResultEntitiesByUser_IdOrderByIdDesc(id, pageable);
    }

    public List<ResultEntity> getTop10ExamsHighestScore(Long id, int page, int perpage){
        Pageable pageable = PageRequest.of(page -1, perpage);
        return resultRepo.findResultEntitiesByExamIdOrderByScoreDesc(id, pageable);
    }

    public Float findHighestScoreByExamId(Long id){
        Optional<ResultEntity> result = resultRepo.findFirstByExamIdOrderByScoreDesc(id);
        if (result.isPresent()){
            return result.get().getScore();
        }else return null;
    }
}
