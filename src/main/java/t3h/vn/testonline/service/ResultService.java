package t3h.vn.testonline.service;

import org.springframework.data.domain.Page;
import t3h.vn.testonline.dto.request.ResultDto;
import t3h.vn.testonline.entities.ResultEntity;

import java.util.List;

public interface ResultService {

     ResultEntity save(ResultDto resultDto, Long examId, Long userId);

     ResultEntity getById(Long id);

     Page<ResultEntity> findResultByUserId(Long id, int page, int perpage);

     List<ResultEntity> getTop10ExamsHighestScore(Long id, int page, int perpage);

     Float findHighestScoreByExamId(Long id);
}
