package com.doctusoft.example.forum;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;
import java.util.List;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.config.DefaultValue;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import lombok.extern.slf4j.Slf4j;

/**
 * Cloud endpoint api to handle forum messages.
 * 
 * @author ttozser
 *
 */
@Slf4j
@Api(
		name = "message", 
		resource = "message", 
		version = "v1", 
		scopes = { Constants.EMAIL_SCOPE }, 
		clientIds = {
			// the client id of the angular ui
			Constants.WEB_CLIENT_ID,
			// the client id of the api explorer
			com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID
		}, 
		description = "Api to create or read messages in a topic.",
		authLevel = AuthLevel.REQUIRED)
public class Messages {

	/**
	 * You need to register the entity classes before you use them.
	 */
	static {
		ObjectifyService.register(Message.class);
		ObjectifyService.register(Topic.class);
	}

	/**
	 * Gets the messages in a topic.
	 * 
	 * @param topic
	 *            the id of the topic
	 * @param limit
	 *            the number of messages to get
	 * @param minDate
	 *            the returned messages should be after this date
	 * @param maxDate
	 *            the returned messages should be before this date
	 * @return a list of messages in the topic
	 * @throws ServiceException
	 *             if the topic does not exists
	 */
	@ApiMethod(path="topic/{topic}")
	public List<Message> getMessages(
			@Named("topic") long topic,
			@Nullable @Named("limit") @DefaultValue("10") int limit,
			@Nullable @Named("minDate") Date minDate,
			@Nullable @Named("maxDate") Date maxDate)
					throws ServiceException {

		verifyTopicExistence(topic);

		Query<Message> query = ofy().load().type(Message.class)
				.ancestor(Key.create(Topic.class, topic))
				.order("-" + Message.CREATED_AT)
				.limit(limit);

		if (minDate != null) {
			// the query is immutable the assignment is important
			query = query.filter(Message.CREATED_AT + " >", minDate);
		}
		if (maxDate != null) {
			query = query.filter(Message.CREATED_AT + " <", maxDate);
		}

		List<Message> messages = query.list();
		return messages;
	}

	/**
	 * Creates a new message in the topic.
	 * 
	 * @param topic
	 *            the id of the topic
	 * @param content
	 *            the content of the message
	 * @param user
	 *            cloud endpoint populated field with the current user
	 *            information
	 * @throws ServiceException
	 *             if the topic does not exists
	 */
	@ApiMethod(path="topic/{topic}")
	public void createMessage(
			@Named("topic") long topic,
			MessageContent content,
			User user) throws ServiceException {

		log.info("name: " + content + " topic: " + topic + " user: " + user);

		verifyTopicExistence(topic);

		Message message = new Message();
		message.setTopic(Key.create(Topic.class, topic));
		message.setContent(content.getContent());
		message.setCreatedAt(new Date());
		message.setAuthor(user.getEmail());

		ofy().save().entity(message).now();
	}

	/**
	 * Throws an exception that will be translated into a 404 error if the topic does not exists.
	 * 
	 * @param topic the id of the topic
	 * @throws NotFoundException if the topic does not exists
	 */
	private void verifyTopicExistence(long topic) throws NotFoundException {
		if (ofy().load().type(Topic.class).id(topic).now() == null) {
			throw new NotFoundException("There is no topic with id: " + topic);
		}
	}

}
