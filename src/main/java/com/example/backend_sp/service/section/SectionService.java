package com.example.backend_sp.service.section;

import com.example.backend_sp.entity.Section;
import com.example.backend_sp.request.SectionRequest;


import java.util.List;

public interface SectionService {

    List<Section> findAll();

    List<Section> findAllByActivatedTrue();

    Section findById(Integer id);

    void mapSectionRequestToSection(SectionRequest request, Section section);

    Section save(SectionRequest request);

    Section update(Integer id, SectionRequest request);

    void deleteById(Integer id);


}
