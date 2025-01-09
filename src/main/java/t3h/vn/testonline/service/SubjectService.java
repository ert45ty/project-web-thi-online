package t3h.vn.testonline.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<SubjectEntity> search(String query, Integer page, Integer perpage){
        Pageable pageable = PageRequest.of(page - 1, perpage);
        Page<SubjectEntity> pageResult;
        if (query != null && !query.isEmpty()){
            pageResult = subjectRepo.findAllByNameContaining(query, pageable);
            return pageResult;
        }
        pageResult = subjectRepo.findAll(pageable);
        return pageResult;
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
