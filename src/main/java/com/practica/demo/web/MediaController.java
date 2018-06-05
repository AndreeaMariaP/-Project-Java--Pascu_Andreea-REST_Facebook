package com.practica.demo.web;

import com.practica.demo.db.entity.Media;
import com.practica.demo.model.MediaModel;
import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import com.practica.demo.service.MediaService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class MediaController {

    @Autowired
    MediaService mediaService;

    @PostMapping("/my-user/images")
    public void uploadImage(@RequestParam("file") MultipartFile uploadfile) throws InvalidArgumentsException {
        mediaService.uploadImage(uploadfile);
    }

    @GetMapping("/my-user/images")
    public List<MediaModel> getUploadedImages() throws ValueNotFoundException {
        return mediaService.getUploadedImages();
    }

    @GetMapping("/my-user/images/{imageId}")
    @ResponseBody
    public ResponseEntity<Resource> getImageByImageId(@PathVariable("imageId")Integer imageId) throws AuthorizationException, ValueNotFoundException, IOException {
        return mediaService.loadImage(imageId);
    }

    @DeleteMapping("/my-user/images/{imageId}")
    public void deleteImageByImageId(@PathVariable("imageId")Integer imageId) throws AuthorizationException, ValueNotFoundException, IOException {
         mediaService.deleteImageByImageId(imageId);
    }


}
