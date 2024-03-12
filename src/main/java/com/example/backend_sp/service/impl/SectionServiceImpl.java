package com.example.backend_sp.service.impl;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.entity.Section;
import com.example.backend_sp.repository.CourseRepository;
import com.example.backend_sp.repository.SectionRepository;
import com.example.backend_sp.request.SectionRequest;
import com.example.backend_sp.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository repository;

    private final CourseRepository courseRepository;

    @Override
    public List<Section> findAll() {
        List<Section> sections = repository.findAll();
        if (sections.isEmpty()){
            throw new RuntimeException("Danh sách chương trống.");
        }
        return sections;
    }

    @Override
    public List<Section> findAllByActivatedTrue() {
        List<Section> sections = repository.findAllByActivatedTrue();
        if (sections.isEmpty()){
            throw new RuntimeException("Danh sách chương trống.");
        }
        return sections;
    }

    @Override
    public Section findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm chương của khóa học"));
    }

    @Override
    public void mapSectionRequestToSection(SectionRequest request, Section section) {
        Optional<Course> optionalCourse = courseRepository.findById(request.getCourse_id());
        if (optionalCourse.isPresent()){
            section.setName(request.getName());
            section.setPosition(request.getPosition());
            section.setActivated(request.isActivated());
            section.setCourse(optionalCourse.get());
        }else {
            throw new RuntimeException("Không tìm thấy khóa học");
        }
    }

    @Override
    public Section save(SectionRequest request) {
        Section section = new Section();
        mapSectionRequestToSection(request, section);
        return repository.save(section);
    }

    @Override
    public Section update(Integer id, SectionRequest request) {
        Section existingSection = findById(id);
        mapSectionRequestToSection(request, existingSection);
        return repository.save(existingSection);
    }

    @Override
    public void deleteById(Integer id) {

    }
}
