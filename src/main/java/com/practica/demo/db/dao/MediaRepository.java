package com.practica.demo.db.dao;

import com.practica.demo.db.entity.Media;
import com.practica.demo.model.MediaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media,Integer> {


    @Query("SELECT new com.practica.demo.model.MediaModel(m.id,m.user.id,m.picturePath) FROM Media m WHERE m.user.id=:userId")
    List<MediaModel> getImagesListByUserId(@Param("userId") Integer userId);

    @Query("SELECT m from Media m where m.id=:imageId")
    Media getImageByImageId(@Param("imageId")Integer imageId);
}
