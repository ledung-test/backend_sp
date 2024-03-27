package com.example.backend_sp.service.favorite;

import com.example.backend_sp.entity.Favorite;
import com.example.backend_sp.request.FavoriteRequest;
import com.example.backend_sp.response.FavoriteResponse;

import java.util.List;

public interface FavoriteService {

    List<FavoriteResponse> getFavoriteByUserId(Integer id);
    Favorite save(FavoriteRequest request);

    void deleteById(Integer id);
}
