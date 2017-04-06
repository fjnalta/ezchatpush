package eu.ezlife.ezChatPush;

import org.glassfish.jersey.server.ResourceConfig;

import eu.ezlife.ezChatPush.provider.AuthenticationFilter;

public class CustomApplication extends ResourceConfig {

	public CustomApplication() {
		packages("eu.ezlife.ezChatPush");
		register(AuthenticationFilter.class);
	}
}