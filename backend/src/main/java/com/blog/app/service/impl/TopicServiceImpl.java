package com.blog.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.model.Topic;
import com.blog.app.repository.TopicRepository;
import com.blog.app.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {
	
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public Topic updateTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

	@Override
	public List<Topic> getSuggestions(String userInput) {
		List<Topic> allTopics = topicRepository.findAll();
		return allTopics.stream()
		        .filter(topic -> topic.getTopicName().toLowerCase().startsWith(userInput.toLowerCase()))
		        .collect(Collectors.toList());

	}

	@Override
	public Topic getTopicById(String topicId) {
		Topic topic=topicRepository.findById(topicId).orElse(null);
		return topic;
	}
}
