package com.blog.app.controller;

import com.blog.app.model.Topic;
import com.blog.app.service.TopicService;
import com.blog.app.utils.IdGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topic")
@CrossOrigin()
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<Topic> addTopic(@RequestBody String topicName) {
    	System.out.println(topicName);
    	String id= IdGenerator.GetUniqueId();
    	Topic topic=new Topic(id,topicName);
    	System.out.println(topic.toString());
        topic= topicService.addTopic(topic);
        return ResponseEntity.ok(topic);
    }

    @PutMapping
    public ResponseEntity<Topic> updateTopic(@RequestBody Topic topic) {
        topic= topicService.updateTopic(topic);
        return ResponseEntity.ok(topic);
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics= topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }
    
    
    
    @GetMapping("/suggestions")
    public ResponseEntity<List<Topic>> getTopicSuggestions(@RequestParam String userInput) {
    	List<Topic> topics= topicService.getSuggestions(userInput);
    	return ResponseEntity.ok(topics);
    }
    
    
    @GetMapping("/{topicId}")
    public ResponseEntity<Topic> getTopicById(@PathVariable String topicId) {
    	Topic topic = topicService.getTopicById(topicId);
        if (topic != null) {
            return ResponseEntity.ok(topic);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

