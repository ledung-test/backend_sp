package com.example.backend_sp.service.lecture;

import com.example.backend_sp.entity.Lecture;
import com.example.backend_sp.entity.Section;
import com.example.backend_sp.repository.LectureRepository;
import com.example.backend_sp.repository.SectionRepository;
import com.example.backend_sp.request.LectureRequest;
import com.example.backend_sp.request.UpdatePositionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LectureServiceImpl implements LectureService {

    private final LectureRepository repository;

    private final SectionRepository sectionRepository;
    @Override
    public Lecture findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài giảng"));
    }

    @Override
    public Lecture save(LectureRequest request) {
        Section section = sectionRepository.findById(request.getSection_id())
                .orElseThrow(() -> new RuntimeException("Chương không tồn tại"));
        Integer position = repository.countLectureBySectionId(section.getId());
        Lecture lecture = Lecture.builder()
                .name(request.getName())
                .position(position + 1)
                .section(section)
                .activated(request.isActivated())
                .isPreview(request.isPreview())
                .build();
        return repository.save(lecture);
    }

    @Override
    public Lecture update(Integer id, LectureRequest request) {
        Lecture existingLecture = repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Bài giảng không tồn tại"));
        existingLecture.setName(request.getName());
        existingLecture.setActivated(request.isActivated());
        existingLecture.setPreview(request.isPreview());
        return repository.save(existingLecture);
    }

    @Override
    public void updatePosition(List<UpdatePositionRequest> request) {
        log.info("vị trí mới" + request);
        if (!request.isEmpty()){
            for (UpdatePositionRequest newPosition: request){
                Lecture existingLecture = findById(newPosition.getId());
                existingLecture.setPosition(newPosition.getNewPosition());
                repository.save(existingLecture);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        Lecture existingLecture = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bài giảng không tồn tại"));
        //ToDo
        //Kiểm tra xem có dữ liệu về video không, nếu có thì xóa
        Integer position = existingLecture.getPosition();
        Section section = existingLecture.getSection();
        repository.delete(existingLecture);
        List<Lecture> lectures = repository.findBySectionIdAndPositionGreaterThan(section.getId(), position);
        if (!lectures.isEmpty()){
            for (Lecture lecture: lectures) {
                lecture.setPosition(lecture.getPosition() - 1);
                repository.save(lecture);
            }
        }
    }
}
