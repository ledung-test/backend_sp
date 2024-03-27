package com.example.backend_sp.mapper;

import com.example.backend_sp.entity.Target;
import com.example.backend_sp.response.TargetResponse;

public class TargetMapper {
    public static void mapTargetToTargetResponse(Target target, TargetResponse response){
        response.setId(target.getId());
        response.setTitle(target.getTitle());
    }
}
