package com.doctusoft.example.forum;

import java.util.Date;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import lombok.Data;

/**
 * The goal of a topic is to organize the messages of a forum.
 * 
 * @author ttozser
 *
 */
@Data
@Cache
@Entity
public class Topic {

	/**
	 * The name of the indexed field {@link #name}.<br/>
	 * Fields like this are useful when you construct queries.
	 */
	public static final String NAME = "name";

	/**
	 * Datastore generated id.
	 */
	@Id
	private Long id;

	/**
	 * The name of the topic.
	 */
	@Index
	private String name;
	/**
	 * The creation date of the topic.
	 */
	private Date createdAt;
	/**
	 * The email of the user who created the topic.
	 */
	private String author;

}
