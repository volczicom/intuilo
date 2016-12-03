package com.doctusoft.example.forum;

import lombok.Data;

/**
 * Wrapper for the message content in the cloud endpoint api.<br/>
 * We need a wrapper for the content so it is transmitted in the body of the request instead of as a query parameter.<br/>
 * The maximal size of a query parameter is much less than the limit for the body.
 * 
 * @author ttozser
 *
 */
@Data
public class MessageContent {

	private String content;
}
