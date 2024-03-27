package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.Section;
import com.example.backend_sp.request.SectionRequest;
import com.example.backend_sp.response.SectionResponse;

public class SectionMapper {
    public static void mapSectionRequestToSection(SectionRequest request, Section section){

    }

    public static void mapSectionToSectionResponse(Section section, SectionResponse response){
        response.setId(section.getId());
        response.setName(section.getName());
        response.setPosition(section.getPosition());
        response.setActivated(section.isActivated());
    }

}
