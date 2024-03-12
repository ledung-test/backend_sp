package com.example.backend_sp.service.impl;

import com.example.backend_sp.entity.Category;
import com.example.backend_sp.entity.Course;
import com.example.backend_sp.entity.Lecture;
import com.example.backend_sp.entity.Section;
import com.example.backend_sp.repository.CategoryRepository;
import com.example.backend_sp.repository.CourseRepository;
import com.example.backend_sp.repository.LectureRepository;
import com.example.backend_sp.repository.SectionRepository;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.CategoryResponse;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.response.LectureResponse;
import com.example.backend_sp.response.SectionResponse;
import com.example.backend_sp.service.CourseService;
import com.example.backend_sp.utils.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    private final CategoryRepository categoryRepository;

    private final SectionRepository sectionRepository;

    private final LectureRepository lectureRepository;

    @Override
    public List<Course> findAll() {
        List<Course> courses = repository.findAll();
        if (courses.isEmpty()) {
            throw new RuntimeException("Danh sách khóa học trống.");
        }
        return courses;
    }

    @Override
    public List<Course> findAllByActivatedTrue() {
        List<Course> courses = repository.findAllByActivatedTrue();
        if (courses.isEmpty()) {
            throw new RuntimeException("Danh sách khóa học trống.");
        }
        return courses;
    }

    @Override
    public Course findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
    }

    @Override
    public void mapCourseRequestToCourse(CourseRequest request, Course course) {
        course.setName(request.getName());
        course.setSlug(SlugUtil.createSlug(request.getName()));
        course.setLesson_content(request.getLesson_content());
        course.setRequirements(request.getRequirements());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setUrl_img(request.getUrl_img());
        course.setActivated(request.isActivated());
        Optional<Category> optionalCategory = categoryRepository.findById(request.getCategory_id());
        course.setCategory(optionalCategory.orElse(null));
    }

    @Override
    public Course save(CourseRequest request) {
        Course course = new Course();
        mapCourseRequestToCourse(request, course);
        return repository.save(course);
    }

    @Override
    public Course update(Integer id, CourseRequest request) {
        Course existingCourse = findById(id);
        mapCourseRequestToCourse(request, existingCourse);
        return repository.save(existingCourse);
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public CourseResponse findByIdAndSlug(Integer id, String slug) {
        Course course = repository.findByIdAndSlug(id, slug)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
        CategoryResponse categoryResponse = null;
        if (course.getCategory() != null) {
            categoryResponse = CategoryResponse.builder()
                    .id(course.getCategory().getId())
                    .name(course.getCategory().getName())
                    .activated(course.getCategory().isActivated())
                    .build();
        }
        List<SectionResponse> sectionResponseList = new ArrayList<>();
        List<Section> sections = sectionRepository.findByCourse_Id(id);
        if (sections != null && !sections.isEmpty()) {
            List<LectureResponse> lectureResponseList = new ArrayList<>();
            for (Section section : sections) {
                List<Lecture> lectures = lectureRepository.findBySectionId(section.getId());
                if (lectures != null && !lectures.isEmpty()){
                    for (Lecture lecture : lectures){
                        LectureResponse lectureResponse = LectureResponse.builder()
                                .id(lecture.getId())
                                .name(lecture.getName())
                                .video_url(lecture.getVideo_url())
                                .document_url(lecture.getDocument_url())
                                .activated(lecture.isActivated())
                                .build();
                        lectureResponseList.add(lectureResponse);
                    }
                }
                SectionResponse sectionResponse = SectionResponse.builder()
                        .id(section.getId())
                        .name(section.getName())
                        .position(section.getPosition())
                        .activated(section.isActivated())
                        .lectures(lectureResponseList)
                        .build();
                sectionResponseList.add(sectionResponse);
            }
        }
        return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .lesson_content(course.getLesson_content())
                .requirements(course.getRequirements())
                .description(course.getDescription())
                .price(course.getPrice())
                .url_img(course.getUrl_img())
                .totalStudents(course.getTotalStudents())
                .rating(course.getRating())
                .activated(course.isActivated())
                .category(categoryResponse)
                .sections(sectionResponseList)
                .build();
    }

}
