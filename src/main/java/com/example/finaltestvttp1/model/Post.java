package com.example.finaltestvttp1.model;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Post {
    private String postingId;
    private String postingDate;
    private String name;
    private String email;
    private String phone;
    private String title;
    private String description;
    private String image;
    // private byte[] content;
    
    public String getPostingId() {
        return postingId;
    }
    public void setPostingId(String postingId) {
        this.postingId = postingId;
    }
    public String getPostingDate() {
        return postingDate;
    }
    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public JsonObject toJsonObj() {
        return Json.createObjectBuilder()
            .add("postingId", postingId)
            .add("postingDate", postingDate)
            .add("name", name)
            .add("email", email)
            .add("phone", phone)
            .add("title", title)
            .add("description", description)
            .add("image", image)
            .build();
    }

    public static Post createModel(String postingId, String postingDate, 
        String name, String email, String phone, String title, String description, String image) {
            Post p = new Post();
            p.setPostingId(postingId);
            p.setPostingDate(postingDate);
            p.setName(name);
            p.setEmail(email);
            p.setPhone(phone);
            p.setTitle(title);
            p.setDescription(description);
            p.setImage(image);
            return p;
        }
}
