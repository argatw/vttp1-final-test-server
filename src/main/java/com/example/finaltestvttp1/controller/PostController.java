package com.example.finaltestvttp1.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.finaltestvttp1.model.Post;
import com.example.finaltestvttp1.repository.PostRepo;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class PostController {

    @Autowired
    private AmazonS3 s3;

    @Autowired
    private PostRepo pRepo;

    private String IMAGE_URL = "https://arga.sgp1.digitaloceanspaces.com/Arga/images";


    // upload to s3 spaces method and redis method
    @CrossOrigin
    @PostMapping(path="/posting", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postUpload(@RequestPart MultipartFile file, @RequestPart String name, @RequestPart String email,
    @RequestPart String phone, @RequestPart String title, @RequestPart String description) {
        
        // My private metadata
        Map<String, String> myData = new HashMap<>();
        myData.put("title", title);
        myData.put("createdOn", (new Date()).toString());

        // Metadata for the object
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(myData);

        String postId = UUID.randomUUID().toString().substring(0, 8);
        String imageURL = IMAGE_URL + postId;
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        Post post = Post.createModel(postId, date, name, email, phone, title, description, imageURL); 
        pRepo.storePostInCache(postId, post.toJsonObj().toString());

        try {
            PutObjectRequest putReq = new PutObjectRequest("arga", "Arga/images/%s".formatted(postId), 
                file.getInputStream(), metadata);
            putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult result = s3.putObject(putReq);
            // JsonObject data = Json.createObjectBuilder()
            //     // .add("content-type", myfile.getContentType())
            //     .add("postingId", postId)
            //     .add("postingDate", date)
            //     .add("name", name)
            //     .add("email", email)
            //     .add("phone", phone)
            //     .add("title", title)
            //     .add("description", description)
            //     .add("image", imageURL)
            //     // .add("original_name", myfile.getOriginalFilename())
            //     // .add("size", myfile.getSize())
            //     .build();
            // return ResponseEntity.ok(data.toString());
            return ResponseEntity.ok(post.toJsonObj().toString());
        } catch (Exception ex) {
            // ex.printStackTrace();
            JsonObject data1 = Json.createObjectBuilder()
                .add("content-type", file.getContentType())
                .add("original_name", file.getOriginalFilename())
                .build();
            return ResponseEntity.ok(data1.toString());

        }

        
    }
}
