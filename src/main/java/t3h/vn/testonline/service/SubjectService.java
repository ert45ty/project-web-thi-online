package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.SubjectDto;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.repository.SubjectRepo;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    SubjectRepo subjectRepo;

    public List<SubjectEntity> getAll(){
        return subjectRepo.findAll();
    }

    public void save(SubjectDto subjectDto){
        SubjectEntity subjectEntity = new SubjectEntity();
        BeanUtils.copyProperties(subjectDto, subjectEntity);
        subjectRepo.save(subjectEntity);
    }
}
