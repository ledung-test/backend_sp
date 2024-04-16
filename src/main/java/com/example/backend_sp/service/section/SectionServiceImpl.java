package com.example.backend_sp.service.section;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.entity.Lecture;
import com.example.backend_sp.entity.Section;
import com.example.backend_sp.repository.CourseRepository;
import com.example.backend_sp.repository.LectureRepository;
import com.example.backend_sp.repository.SectionRepository;
import com.example.backend_sp.request.SectionRequest;
import com.example.backend_sp.request.UpdatePositionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class SectionServiceImpl implements SectionService {

    private final SectionRepository repository;

    private final CourseRepository courseRepository;

    private final LectureRepository lectureRepository;

    @Override
    public Section findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chương không tồn tại"));
    }

    @Override
    public void create(SectionRequest request) {
        Course course = courseRepository.findById(request.getCourse_id())
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));
        int position = repository.countSectionByCourseId(course.getId());
        Section section = Section.builder()
                .name(request.getName())
                .course(course)
                .activated(request.isActivated())
                .position(position + 1)
                .build();
        repository.save(section);
    }

    @Override
    public void update(Integer id, SectionRequest request) {
        Section existingSection = findById(id);
        existingSection.setName(request.getName());
        existingSection.setActivated(request.isActivated());
        List<Lecture> lectures = lectureRepository.findBySectionId(existingSection.getId());
        if (!lectures.isEmpty()){
            for (Lecture lecture: lectures) {
                lecture.setActivated(request.isActivated());
                lectureRepository.save(lecture);
            }
        }
        repository.save(existingSection);
    }

    @Override
    public void updatePosition(List<UpdatePositionRequest> request) {
        if (!request.isEmpty()) {
            for (UpdatePositionRequest newPosition : request) {
                Section existingSection = findById(newPosition.getId());
                existingSection.setPosition(newPosition.getNewPosition());
                repository.save(existingSection);
            }
        }

    }

    @Override
    public void delete(Integer id) {
        Section existingSection = findById(id);
        Integer position = existingSection.getPosition();
        Course course = existingSection.getCourse();
        List<Lecture> lectures = lectureRepository.findBySectionId(existingSection.getId());
        if (!lectures.isEmpty()) {
            lectureRepository.deleteAll(lectures);
        }
        repository.delete(existingSection);
        List<Section> sections = repository.findByCourseIdAndPositionGreaterThan(course.getId(), position);
        if (!sections.isEmpty()) {
            for (Section section : sections) {
                section.setPosition(section.getPosition() - 1);
                repository.save(section);
            }
        }


    }

}
