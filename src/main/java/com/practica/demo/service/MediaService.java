package com.practica.demo.service;

import com.practica.demo.db.dao.MediaRepository;
import com.practica.demo.db.dao.UserRepository;
import com.practica.demo.db.entity.Media;
import com.practica.demo.model.MediaModel;
import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class MediaService {

    @Autowired
    SessionService sessionService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MediaRepository mediaRepository;

    private static String UPLOADED_FOLDER = "C://Users//andre//Desktop//Practica//demo//src";

    public void uploadImage(MultipartFile uploadfile) throws InvalidArgumentsException {
        if (uploadfile.isEmpty()) {
           throw new InvalidArgumentsException("No file inserted");
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfile));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Media media = new Media(userRepository.findUserById(sessionService.getSessionModel().getUserId()),Paths.get(UPLOADED_FOLDER + uploadfile.getOriginalFilename() ).toString());
        mediaRepository.save(media);
    }

    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue;
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }

    public List<MediaModel> getUploadedImages() throws ValueNotFoundException {
        if(sessionService.getSessionModel().getUserId()==null)
        {
            throw new ValueNotFoundException("User not found");
        }
        List<MediaModel> imagesList=mediaRepository.getImagesListByUserId(sessionService.getSessionModel().getUserId());

        return imagesList;
    }

    public ResponseEntity<Resource> loadImage(Integer imageId) throws AuthorizationException, IOException, ValueNotFoundException {
        //verificare: logged user is the one that uploaded the file
        Media media = mediaRepository.getImageByImageId(imageId);
        if(media.getUser().getId()!=sessionService.getSessionModel().getUserId()){
            throw new AuthorizationException("Not your photo");
        }
            Path file = Paths.get(media.getPicturePath());
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists() || !resource.isReadable()) {
               throw new ValueNotFoundException("Invalid resource");
            }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public void deleteImageByImageId(Integer imageId) throws AuthorizationException, IOException, ValueNotFoundException {
        Media media = mediaRepository.getImageByImageId(imageId);
        if(media.getUser().getId()!=sessionService.getSessionModel().getUserId()){
            throw new AuthorizationException("Not your photo");
        }
        Path file = Paths.get(media.getPicturePath());
        Resource resource = new UrlResource(file.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            throw new ValueNotFoundException("Media not found");
        }

        //delete at the picture path and in database
        if(file==null)
        {
            throw new ValueNotFoundException("Invalid filepath");
        }

        Files.delete(file);
        mediaRepository.delete(media);
    }
}

