package t3h.vn.testonline.service;

import t3h.vn.testonline.dto.request.OptionDto;
import t3h.vn.testonline.entities.OptionEntity;

public interface OptionService {

    void save(OptionDto optionDto, Long id);

    OptionEntity getById(Long id);

}
