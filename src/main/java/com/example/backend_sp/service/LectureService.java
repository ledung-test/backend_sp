package com.example.backend_sp.service;

import com.example.backend_sp.entity.Lecture;
import com.example.backend_sp.request.LectureRequest;

public interface LectureService {

    Lecture findById(Integer id);

    void mapLectureRequestToLecture(LectureRequest request, Lecture lecture);

    Lecture save(LectureRequest request);

    Lecture update(Integer id, LectureRequest request);

}
