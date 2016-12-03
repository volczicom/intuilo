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
import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import lombok.extern.slf4j.Slf4j;

	/**
	 * Cloud endpoint api to handle forum messages.
	 * 
	 * @author volczi
	 *
	 */
	@Slf4j
	@Api(
			name = "playerManagerCE", 
			resource = "playerManagerCE", 
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
	public class PlayerManagerCE {

		/**
		 * You need to register the entity classes before you use them.
		 */
		static {
			ObjectifyService.register(Message.class);
			ObjectifyService.register(Topic.class);
		}

		@ApiMethod(path="topic")
		public ApiBoolean getMessages(
				)
						throws ServiceException {

			return new ApiBoolean(true);
		}

	}
