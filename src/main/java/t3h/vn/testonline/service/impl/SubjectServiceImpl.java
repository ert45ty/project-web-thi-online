package t3h.vn.testonline.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import t3h.vn.testonline.dto.request.SubjectDto;
import t3h.vn.testonline.entities.SubjectEntity;
import t3h.vn.testonline.repository.SubjectRepo;
import t3h.vn.testonline.service.SubjectService;
import t3h.vn.testonline.utils.FileUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepo subjectRepo;
    private final FileUtils fileUtils;

    public List<SubjectEntity> getAllAndStatusIsLike(){
        return subjectRepo.findAllByStatus(1);
    }

    public List<SubjectEntity> getAll(){
        return subjectRepo.findAll();
    }
    public Page<SubjectEntity> getPageAll(int page, int perpage){
        Pageable pageable = PageRequest.of(page -1, perpage);
        return subjectRepo.findAll(pageable);
    }

    public Page<SubjectEntity> search(String query, Integer page, Integer perpage){
        Pageable pageable = PageRequest.of(page - 1, perpage);
        return subjectRepo.findAllByNameContaining(query, pageable);
    }

    public void save(SubjectDto subjectDto){
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectDto.setStatus(1);
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

    public void update(SubjectEntity subjectEntity, MultipartFile multipartFile){
        if (multipartFile != null && !multipartFile.isEmpty()){
            try {
                String image_name = fileUtils.saveFile(multipartFile);
                subjectEntity.setImage_name(image_name);
            }catch (Exception e){
            }

        }subjectRepo.save(subjectEntity);
    }

    public SubjectEntity getById(Long id){
        return subjectRepo.getById(id);
    }

    public void delete(Long id){
        subjectRepo.deleteById(id);
    }
}
