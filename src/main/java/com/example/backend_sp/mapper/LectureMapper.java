package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.Lecture;
import com.example.backend_sp.request.LectureRequest;
import com.example.backend_sp.response.LectureResponse;

import java.util.List;


public class LectureMapper {
    public static void mapLectureRequestToLecture(LectureRequest request, Lecture lecture){

    }

    public static void mapLectureToLectureResponse(Lecture lecture, LectureResponse response){
        response.setId(lecture.getId());
        response.setName(lecture.getName());
        response.setVideo_url(lecture.getVideo_url());
        response.setDocument_url(lecture.getDocument_url());
        response.setPosition(lecture.getPosition());
        response.setActivated(lecture.isActivated());
        response.setPreview(lecture.isPreview());
    }

    public static void mapLectureListToLectureResponseList(List<Lecture> lectureList, List<LectureResponse> lectureResponseList){
        for (Lecture lecture : lectureList) {
            LectureResponse lectureResponse = new LectureResponse();
            LectureMapper.mapLectureToLectureResponse(lecture, lectureResponse);
            lectureResponseList.add(lectureResponse);
        }
    }
}
