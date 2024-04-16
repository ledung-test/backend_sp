package com.example.backend_sp.service.course;

import com.example.backend_sp.entity.*;
import com.example.backend_sp.entity.enums.DiscountType;
import com.example.backend_sp.mapper.*;
import com.example.backend_sp.repository.*;
import com.example.backend_sp.repository.specification.CourseSpecifications;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.*;
import com.example.backend_sp.service.file.FileService;
import com.example.backend_sp.utils.DiscountUtils;
import com.example.backend_sp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;

    private final CategoryRepository categoryRepository;

    private final SectionRepository sectionRepository;

    private final LectureRepository lectureRepository;

    private final DiscountRepository discountRepository;

    private final CourseDiscountRepository courseDiscountRepository;

    private final FileService fileService;

    //Lấy danh sách tất cả các khóa học
    @Override
    public List<CourseResponse> findAll() {
        List<Course> courses = repository.findAll();
        List<CourseResponse> courseResponseList = new ArrayList<>();
        if (!courses.isEmpty()) {
            mapCourseListToCourseListResponse(courses, courseResponseList);
        }
        return courseResponseList;
    }

    public void mapCourseListToCourseListResponse(List<Course> courseList, List<CourseResponse> courseResponseList) {
        for (Course course : courseList) {
            CourseResponse courseResponse = new CourseResponse();
            mapCategoryResponseToCourseResponse(course.getCategory(), courseResponse);
            mapDiscountToCourseResponse(course, courseResponse);
            CourseMapper.mapCourseToCourseResponse(course, courseResponse);
            courseResponseList.add(courseResponse);
        }
    }

    public void mapCategoryResponseToCourseResponse(Category category, CourseResponse courseResponse) {
        CategoryResponse categoryResponse = new CategoryResponse();
        CategoryMapper.mapCategoryToCategoryResponse(category, categoryResponse);
        courseResponse.setCategory(categoryResponse);
    }

    public void mapDiscountToCourseResponse(Course course, CourseResponse courseResponse) {
        DiscountUtils.setDiscountToCourseResponse(courseDiscountRepository, course, courseResponse);
    }

    //Lấy danh sách khóa học trạng thái true
    @Override
    public List<CourseResponse> findAllByActivatedTrue() {
        List<Course> courses = repository.findAllByActivatedTrue();
        List<CourseResponse> courseResponseList = new ArrayList<>();
        if (!courses.isEmpty()) {
            mapCourseListToCourseListResponse(courses, courseResponseList);
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
        Category category = categoryRepository.findById(request.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        Course course = Course.builder()
                .name(request.getName())
                .category(category)
                .price(BigDecimal.ZERO)
                .activated(false)
                .build();
        repository.save(course);
        return course;
    }


    //Cập nhật khóa học
    @Override
    public CourseResponse update(Integer id, CourseRequest request) {
        Course existingCourse = findById(id);
        CourseMapper.mapCourseRequestToCourse(request, existingCourse);
        if (request.getFile() != null) {
            if (!FileUtils.isContentTypeImg(request.getFile())){
                throw new RuntimeException("Chỉ chấp nhận file hình ảnh");
            }
            if (!FileUtils.isValidFileImgSize(request.getFile())){
                throw new RuntimeException("Kích thước file ảnh < 4MB");
            }
            if (!FileUtils.isValidFileImgExtension(request.getFile())){
                throw new RuntimeException("Chỉ chấp nhận định dạng .png hoặc .jpg");
            }
            if (existingCourse.getUrl_img() != null) {
                FileUtils.deleteFile(existingCourse.getUrl_img());
            }
            String filePath = fileService.uploadFile(request.getFile(), FileUtils.UPLOAD_IMAGE_DIR);
            existingCourse.setUrl_img(filePath);
        }
        if (request.getCategory_id() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(request.getCategory_id());
            existingCourse.setCategory(optionalCategory.orElse(null));
        }
        if (request.getDiscount_id() != null) {
            if (Objects.equals(request.getPrice(), BigDecimal.ZERO)) {
                throw new RuntimeException("Không thể dùng mã giảm giá cho khóa học miễn phí");
            }
            Discount discount = discountRepository.findById(request.getDiscount_id())
                    .orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại"));
            if (discount.getDiscountType().equals(DiscountType.FIXED)) {
                if (discount.getDiscountValue().compareTo(request.getPrice()) > 0) {
                    throw new RuntimeException("Giảm giá cố định cần nhỏ hơn giá khóa học");
                }
            }
            Optional<CourseDiscount> courseDiscounts = courseDiscountRepository.findByCourseId(
                    existingCourse.getId());
            courseDiscounts.ifPresent(courseDiscountRepository::delete);
            CourseDiscount courseDiscount = CourseDiscount.builder()
                    .course(existingCourse)
                    .discount(discount)
                    .build();
            courseDiscountRepository.save(courseDiscount);
        } else {
            Optional<CourseDiscount> courseDiscounts = courseDiscountRepository.findByCourseId(
                    existingCourse.getId());
            courseDiscounts.ifPresent(courseDiscountRepository::delete);
        }
        if (request.getTargets() != null) {
            existingCourse.setTargets(request.getTargets());
        }
        repository.save(existingCourse);
        return getCourseById(existingCourse.getId());
    }

    @Override
    public CourseResponse getCourseById(Integer id) {
        Course existingCourse = findById(id);
        CourseResponse courseResponse = new CourseResponse();
        mapCategoryResponseToCourseResponse(existingCourse.getCategory(), courseResponse);
        mapDiscountToCourseResponse(existingCourse, courseResponse);
        CourseMapper.mapCourseToCourseResponse(existingCourse, courseResponse);
        return courseResponse;
    }

    //Lấy khóa học theo Course ID và Slug
    @Override
    public CourseResponse findByIdAndSlug(Integer id, String slug) {
        Course existingCourse = repository.findByIdAndSlug(id, slug)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
        CourseResponse courseResponse = new CourseResponse();
        mapCategoryResponseToCourseResponse(existingCourse.getCategory(), courseResponse);
        mapDiscountToCourseResponse(existingCourse, courseResponse);
        CourseMapper.mapCourseToCourseResponse(existingCourse, courseResponse);
        return courseResponse;
    }

    //Lấy danh sách Sections trong khóa học
    @Override
    public List<SectionResponse> findAllSectionByCourseId(Integer id) {
        if (repository.existsCourseById(id)) {
            List<SectionResponse> sectionResponseList = new ArrayList<>();
            List<Section> sections = sectionRepository.findByCourseIdOrderByPositionAsc(id);
            if (!sections.isEmpty()) {
                for (Section section : sections) {
                    List<LectureResponse> lectureResponseList = new ArrayList<>();
                    List<Lecture> lectures = lectureRepository.findBySectionIdOrderByPositionAsc(section.getId());
                    if (!lectures.isEmpty()) {
                        LectureMapper.mapLectureListToLectureResponseList(lectures, lectureResponseList);
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
        if (priceOptions != null && !priceOptions.isEmpty()) {
            spec = spec.and(CourseSpecifications.hasPrice(priceOptions));
        }
        List<Course> courses = repository.findAll(spec);
        if (!courses.isEmpty()) {
            List<CourseResponse> courseResponseList = new ArrayList<>();
            mapCourseListToCourseListResponse(courses, courseResponseList);
            return courseResponseList;
        } else {
            throw new RuntimeException("Không tìm thấy khóa học");
        }
    }

    @Override
    public List<CourseResponse> getCoursesByPriceFree() {
        List<Course> courses = repository.findTop5ByPriceOrderByCreatedAtDesc(BigDecimal.ZERO);
        List<CourseResponse> courseResponseList = new ArrayList<>();
        if (!courses.isEmpty()) {
            mapCourseListToCourseListResponse(courses, courseResponseList);
        }
        return courseResponseList;
    }

    @Override
    public List<CourseResponse> getCoursesByRatingAndTotalStudentDesc() {
        List<Course> courses = repository.findTop5ByRatingGreaterThanEqualOrderByTotalStudentsDesc(4);
        List<CourseResponse> courseResponseList = new ArrayList<>();
        if (!courses.isEmpty()) {
            mapCourseListToCourseListResponse(courses, courseResponseList);
        }
        return courseResponseList;
    }

    @Override
    public List<CourseResponse> getCoursesByDiscountNotNull() {
        List<CourseDiscount> courseDiscounts = courseDiscountRepository.findByDiscountEndDateAfter(new Date());
        List<CourseResponse> courseResponseList = new ArrayList<>();
        if (!courseDiscounts.isEmpty()) {
            List<Course> courses = new ArrayList<>();
            for (CourseDiscount courseDiscount : courseDiscounts) {
                courses.add(courseDiscount.getCourse());
            }
            mapCourseListToCourseListResponse(courses, courseResponseList);
        }
        return courseResponseList;
    }
}

