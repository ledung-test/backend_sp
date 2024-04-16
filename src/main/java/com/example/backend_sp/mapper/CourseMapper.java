package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.Course;
import com.example.backend_sp.request.CourseRequest;
import com.example.backend_sp.response.CourseResponse;
import com.example.backend_sp.utils.SlugUtils;

public class CourseMapper {
    public static void mapCourseRequestToCourse(CourseRequest request, Course course){
        course.setName(request.getName());
        course.setSlug(SlugUtils.createSlug(request.getName()));
        course.setIntro(request.getIntro());
        course.setRequirements(request.getRequirements());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setActivated(request.isActivated());
    }
    public static void mapCourseToCourseResponse(Course course, CourseResponse response) {
        response.setId(course.getId());
        response.setName(course.getName());
        response.setSlug(SlugUtils.createSlug(course.getName()));
        response.setIntro(course.getIntro());
        response.setRequirements(course.getRequirements());
        response.setDescription(course.getDescription());
        response.setPrice(course.getPrice());
        response.setUrl_img(course.getUrl_img());
        response.setTotalStudents(course.getTotalStudents());
        response.setTargets(course.getTargets());
        response.setRating(course.getRating());
        response.setActivated(course.isActivated());
    }
}
