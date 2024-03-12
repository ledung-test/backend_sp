package com.example.backend_sp.service.impl;

import com.example.backend_sp.entity.Lecture;
import com.example.backend_sp.entity.Section;
import com.example.backend_sp.repository.LectureRepository;
import com.example.backend_sp.repository.SectionRepository;
import com.example.backend_sp.request.LectureRequest;
import com.example.backend_sp.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private final LectureRepository repository;

    private final SectionRepository sectionRepository;
    @Override
    public Lecture findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài giảng"));
    }

    @Override
    public void mapLectureRequestToLecture(LectureRequest request, Lecture lecture) {
        Optional<Section> optionalSection = sectionRepository.findById(request.getSection_id());
        if (optionalSection.isPresent()){
            lecture.setName(request.getName());
            lecture.setVideo_url(request.getVideo_url());
            lecture.setDocument_url(request.getDocument_url());
            lecture.setActivated(request.isActivated());
            lecture.setSection(optionalSection.get());
        }else {
            throw new RuntimeException("Không tìm thấy chương");
        }

    }

    @Override
    public Lecture save(LectureRequest request) {
        Lecture lecture = new Lecture();
        mapLectureRequestToLecture(request, lecture);
        return repository.save(lecture);
    }

    @Override
    public Lecture update(Integer id, LectureRequest request) {
        Lecture existingLecture = findById(id);
        mapLectureRequestToLecture(request, existingLecture);
        return repository.save(existingLecture);
    }
}
