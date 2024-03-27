package com.example.backend_sp.service.course;

import com.example.backend_sp.entity.*;
import com.example.backend_sp.mapper.*;
import com.example.backend_sp.repository.*;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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

    private final TargetRepository targetRepository;

    //Lấy danh sách tất cả các khóa học
    @Override
    public List<Course> findAll() {
        return repository.findAll();
    }

    //Lấy danh sách khóa học trạng thái true
    @Override
    public List<CourseResponse> findAllByActivatedTrue() {
        List<Course> courses = repository.findAllByActivatedTrue();
        List<CourseResponse> courseResponseList = new ArrayList<>();
        if (!courses.isEmpty()) {
            for (Course course : courses) {
                CourseResponse courseResponse = new CourseResponse();
                CourseMapper.mapCourseToCourseResponse(course, courseResponse);
                courseResponseList.add(courseResponse);
            }
        }
        return courseResponseList;
    }

    //Tìm kiếm khóa học theo Course ID
    @Override
    public Course findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
    }

    //Lưu khóa học
    @Override
    public Course save(CourseRequest request) {
        Course course = new Course();
        CourseMapper.mapCourseRequestToCourse(request, course);
        Optional<Category> optionalCategory = categoryRepository.findById(request.getCategory_id());
        course.setCategory(optionalCategory.orElse(null));
        return repository.save(course);
    }

    //Cập nhật khóa học
    @Override
    public Course update(Integer id, CourseRequest request) {
        Course existingCourse = findById(id);
        CourseMapper.mapCourseRequestToCourse(request, existingCourse);
        Optional<Category> optionalCategory = categoryRepository.findById(request.getCategory_id());
        existingCourse.setCategory(optionalCategory.orElse(null));
        return repository.save(existingCourse);
    }

    //Lấy khóa học theo Course ID và Slug
    @Override
    public CourseResponse findByIdAndSlug(Integer id, String slug) {
        Course course = repository.findByIdAndSlug(id, slug)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
        CategoryResponse categoryResponse = new CategoryResponse();
        if (course.getCategory() != null) {
            CategoryMapper.mapCategoryToCategoryResponse(course.getCategory(), categoryResponse);
        }
        CourseResponse courseResponse = new CourseResponse();
        CourseMapper.mapCourseToCourseResponse(course, courseResponse);
        return courseResponse;
    }

    //Lấy danh sách Sections trong khóa học
    @Override
    public List<SectionResponse> findAllSectionByCourseIdAndSlug(Integer id, String slug) {
        if (repository.existsCourseByIdAndSlug(id, slug)) {
            List<SectionResponse> sectionResponseList = new ArrayList<>();
            List<Section> sections = sectionRepository.findByCourse_Id(id);
            if (!sections.isEmpty()) {
                for (Section section : sections) {
                    List<LectureResponse> lectureResponseList = new ArrayList<>();
                    List<Lecture> lectures = lectureRepository.findBySectionId(section.getId());
                    if (!lectures.isEmpty()) {
                        for (Lecture lecture : lectures) {
                            LectureResponse lectureResponse = new LectureResponse();
                            LectureMapper.mapLectureToLectureResponse(lecture, lectureResponse);
                            lectureResponseList.add(lectureResponse);
                        }
                    }
                    SectionResponse sectionResponse = new SectionResponse();
                    SectionMapper.mapSectionToSectionResponse(section, sectionResponse);
                    sectionResponse.setLectures(lectureResponseList);
                    sectionResponseList.add(sectionResponse);
                }
            }
            return sectionResponseList;
        } else {
            throw new RuntimeException("Không tìm thấy khóa học");
        }

    }

    //Tìm kiếm khóa học theo nhiều điều kiện
    @Override
    public List<CourseResponse> getCourseByFilter(Double minimumRating, String sortBy, List<Integer> categoryIds, List<String> priceOptions) {
        Specification<Course> spec = Specification.where(null);
        if (minimumRating != null) {
            spec = spec.and(CourseSpecifications.withMinimumRating(minimumRating));
        }
        if ("newest".equals(sortBy)) {
            spec = spec.and(CourseSpecifications.sortByCreationDate());
        } else if ("popular".equals(sortBy)) {
            spec = spec.and(CourseSpecifications.sortByNumberOfStudents());
        } else if ("az".equals(sortBy)) {
            spec = spec.and(CourseSpecifications.sortAlphabetically());
        }
        if (categoryIds != null && !categoryIds.isEmpty()) {
            spec = spec.and(CourseSpecifications.hasCategoryIds(categoryIds));
        }
        if (priceOptions != null && !priceOptions.isEmpty()){
            spec = spec.and(CourseSpecifications.hasPrice(priceOptions));
        }
        List<Course> courses = repository.findAll(spec);
        if (!courses.isEmpty()) {
            List<CourseResponse> courseResponseList = new ArrayList<>();
            for (Course course : courses) {
                CourseResponse courseResponse = new CourseResponse();
                CourseMapper.mapCourseToCourseResponse(course, courseResponse);
                courseResponseList.add(courseResponse);
            }
            return courseResponseList;
        } else {
            throw new RuntimeException("Không tìm thấy khóa học");
        }
    }

    //Lấy danh sách mục tiêu khóa học theo Course ID
    @Override
    public List<TargetResponse> getTargetsByCourseId(Integer courseId) {
        List<Target> targets = targetRepository.findByCourse_Id(courseId);
        if (targets.isEmpty()) {
            throw new RuntimeException("Danh sách mục tiêu trống");
        }
        List<TargetResponse> targetResponseList = new ArrayList<>();
        for (Target target : targets) {
            TargetResponse targetResponse = new TargetResponse();
            TargetMapper.mapTargetToTargetResponse(target, targetResponse);
            targetResponseList.add(targetResponse);
        }
        return targetResponseList;
    }


}
