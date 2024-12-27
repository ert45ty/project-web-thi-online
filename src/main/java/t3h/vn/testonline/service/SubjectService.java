package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.dto.SubjectDto;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.repository.SubjectRepo;
import t3h.vn.testonline.utils.FileUtils;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    SubjectRepo subjectRepo;
    @Autowired
    FileUtils fileUtils;

    public List<SubjectEntity> getAll(){
        return subjectRepo.findAll();
    }

    public void save(SubjectDto subjectDto){
        SubjectEntity subjectEntity = new SubjectEntity();
        BeanUtils.copyProperties(subjectDto, subjectEntity);
        if(subjectDto.getFileImage() != null && !subjectDto.getFileImage().isEmpty()){
            try{
                String image_name = fileUtils.saveFile(subjectDto.getFileImage());
                subjectEntity.setImage_name(image_name);
            }catch (Exception e){
            }
        }
        subjectRepo.save(subjectEntity);
    }

    public SubjectEntity getById(Long id){
        return subjectRepo.getById(id);
    }

    public void delete(Long id){
        subjectRepo.deleteById(id);
    }
}
