package com.tzplatform.dao.mobileplatform;

import com.tzplatform.entity.mobileplatform.Poster;

import java.util.List;

public interface PlatformPosterDao {

     int addPoster(Poster poster);
    List<Poster> listPoster(Poster poster);
    int listPosterCount(Poster poster);
    Poster poster(Poster poster);
    int updatePoster(Poster poster);
    int deletePoster(Poster poster);
    int updateStatus(Poster poster);

}
