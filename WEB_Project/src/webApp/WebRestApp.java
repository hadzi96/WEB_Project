package webApp;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import webApp.controllers.ControllerKorpa;
import webApp.controllers.ControllerPhoto;
import webApp.controllers.ControllerTest;
import webApp.controllers.ControllerUser;

@ApplicationPath("/server")
public class WebRestApp extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(ControllerUser.class);
		classes.add(ControllerPhoto.class);
		classes.add(ControllerKorpa.class);
		classes.add(ControllerTest.class);
		return classes;
	}
}
