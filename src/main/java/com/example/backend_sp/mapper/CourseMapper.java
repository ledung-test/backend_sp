package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.CategoryResponse;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.utils.SlugUtil;


public class CourseMapper {
    public static void mapCourseRequestToCourse(CourseRequest request, Course course){
        course.setName(request.getName());
        course.setSlug(SlugUtil.createSlug(request.getName()));
        course.setLesson_content(request.getLesson_content());
        course.setRequirements(request.getRequirements());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setUrl_img(request.getUrl_img());
        course.setActivated(request.isActivated());
    };
    public static void mapCourseToCourseResponse(Course course, CourseResponse response) {
        response.setId(course.getId());
        response.setName(course.getName());
        response.setSlug(SlugUtil.createSlug(course.getName()));
        response.setIntro(course.getIntro());
        response.setLesson_content(course.getLesson_content());
        response.setRequirements(course.getRequirements());
        response.setDescription(course.getDescription());
        response.setPrice(course.getPrice());
        response.setUrl_img(course.getUrl_img());
        response.setTotalStudents(course.getTotalStudents());
        response.setRating(course.getRating());
        response.setActivated(course.isActivated());
        CategoryResponse categoryResponse = new CategoryResponse();
        CategoryMapper.mapCategoryToCategoryResponse(course.getCategory(), categoryResponse);
        response.setCategory(categoryResponse);
    }
}
