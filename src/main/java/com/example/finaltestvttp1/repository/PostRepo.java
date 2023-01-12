package com.example.finaltestvttp1.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.time.Duration;

import java.util.logging.Logger;
import java.util.logging.Level;

import com.example.finaltestvttp1.model.Post;

@Repository
public class PostRepo {
    // for caching in redis
    Logger logger = Logger.getLogger(PostRepo.class.getName());

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void storePostInCachee(Post post) {
        logger.log(Level.INFO, "Storing post " + post.getPostingId() + " in cache");
        redisTemplate.opsForHash()
                .put(post.getPostingId(), "entry", post.toJsonObj());

        Duration timeout = Duration.parse("PT5M");
        redisTemplate.expire(post.getPostingId(), timeout);
        System.out.println(timeout.toMinutesPart());
        logger.log(Level.INFO, "Successfully cached post: " + post.getTitle() + " at key: " + post.getPostingId() + " for "
                + timeout.toMinutesPart() + " minutes");
    }

    public void storePostInCache(String postId, String post) {

        redisTemplate.opsForValue().set(postId, post);
        
        Duration timeout = Duration.parse("PT10M");
        redisTemplate.expire(postId, timeout);

    }
}
