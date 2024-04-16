package com.example.backend_sp.service.lecture;

import com.example.backend_sp.entity.Lecture;
import com.example.backend_sp.request.LectureRequest;
import com.example.backend_sp.request.UpdatePositionRequest;

import java.util.List;

public interface LectureService {

    Lecture findById(Integer id);

    Lecture save(LectureRequest request);

    Lecture update(Integer id, LectureRequest request);
    void updatePosition(List<UpdatePositionRequest> request);

    void delete(Integer id);



}
