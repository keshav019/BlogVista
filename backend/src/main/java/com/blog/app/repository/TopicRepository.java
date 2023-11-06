package com.blog.app.repository;

import com.blog.app.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicRepository extends MongoRepository<Topic, String> {
    Topic findByTopicName(String topicName);
}
