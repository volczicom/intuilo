package com.doctusoft.example.forum;

import java.util.Date;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import lombok.Data;

/**
 * A message in a forum {@link Topic}.
 * 
 * @author ttozser
 *
 */
@Data
@Cache
@Entity
public class Message {

	/**
	 * The name of the indexed field {@link #createdAt}.<br/>
	 * Fields like this are useful when you construct queries.
	 */
	public static final String CREATED_AT = "createdAt";

	/**
	 * The parent topic id.<br/>
	 * Messages under the same topic gets into a single entity group.<br/>
	 * This field is not sent over the cloud endpoint api.
	 */
	@ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	@Parent
	private Key<Topic> topic;
	
	/**
	 * Datastore generated identifier.
	 */
	@Id
	private Long id;

	@Index
	private Date createdAt;
	private String author;
	private String content;

}
