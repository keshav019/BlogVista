package com.blog.app.service;

import java.util.List;

import com.blog.app.model.Topic;

public interface TopicService {
	public Topic addTopic(Topic topic);

	public Topic updateTopic(Topic topic);

	public List<Topic> getAllTopics();

	public List<Topic> getSuggestions(String userInput);
	
	public Topic getTopicById(String topicId);
}
