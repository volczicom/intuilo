package com.doctusoft.example.forum;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;
import java.util.List;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyService;

import lombok.extern.slf4j.Slf4j;

/**
 * Cloud endpoint api to handle forum topics.
 * 
 * @author ttozser
 *
 */
@Slf4j
@Api(
		name = "topic", 
		resource = "topic", 
		version = "v1", 
		scopes = { Constants.EMAIL_SCOPE }, 
		clientIds = {
				// the client id of the angular ui
				Constants.WEB_CLIENT_ID,
				// the client id of the api explorer
				com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID 
		}, 
		description = "Api to create or read topics.", 
		authLevel = AuthLevel.REQUIRED)
public class Topics {

	/**
	 * You need to register the entity classes before you use them.
	 */
	static {
		ObjectifyService.register(Topic.class);
	}

	/**
	 * Gets every topic in alphabetical order of their name.
	 * 
	 * @return the list of every topic
	 */
	@ApiMethod(path = "topic")
	public List<Topic> getTopics() {
		return ofy().load().type(Topic.class)
				.order(Topic.NAME)
				.list();
	}

	/**
	 * Gets a single topic by its id.
	 * 
	 * @param topicId
	 *            the id of the topic
	 * 
	 * @return the requested topic
	 * @throws ServiceException
	 *             if the topic does not exists
	 */
	@ApiMethod(path = "topic/{topicId}")
	public Topic getTopic(@Named("topicId") long topicId) throws ServiceException {
		Topic topic = ofy().load().type(Topic.class).id(topicId).now();
		if (topic == null) {
			throw new NotFoundException("There is no topic with id: " + topic);
		}
		return topic;
	}

	/**
	 * Creates a new topic with the given name
	 * 
	 * @param name
	 *            the name for the new topic
	 * @param user
	 *            cloud endpoint populated field with the user data
	 * @return the newly created topic
	 */
	@ApiMethod(path = "topic")
	public Topic createTopic(
			@Named("name") final String name,
			final User user) {

		log.info("name: " + name + " user: " + user);

		Topic topic = new Topic();
		topic.setAuthor(user.getEmail());
		topic.setCreatedAt(new Date());
		topic.setName(name);
		
		ofy().save().entity(topic).now();
		return topic;
	}

}
