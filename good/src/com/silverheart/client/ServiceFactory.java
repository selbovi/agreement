package com.silverheart.client;

import com.google.gwt.core.client.GWT;

public class ServiceFactory {
	
	private static GreetingServiceAsync greetingService;
	
	public static GreetingServiceAsync getService(){
		if (greetingService == null)
			greetingService = GWT.create(GreetingService.class);
		
		return greetingService;
	}  

}
