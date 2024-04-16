package com.example.backend_sp.service.section;

import com.example.backend_sp.entity.Section;
import com.example.backend_sp.request.SectionRequest;
import com.example.backend_sp.request.UpdatePositionRequest;


import java.util.List;

public interface SectionService {

    Section findById(Integer id);

    void create(SectionRequest request);

    void update(Integer id, SectionRequest request);

    void updatePosition(List<UpdatePositionRequest> request);

    void delete(Integer id);

}
