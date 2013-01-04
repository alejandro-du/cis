package cis.web.auth;

import javax.mail.MessagingException;

import cis.db.container.CisContainerFactory;
import cis.db.container.UserContainer;
import cis.db.dto.User;
import cis.web.CisConstants;

import com.vaadin.Application;

import enterpriseapp.Utils;
import enterpriseapp.mail.MailSender;

public class AuthService {
	
	private AuthService() {}
	
	public static boolean login(Application application, String login, String password) {
		UserContainer userContainer = CisContainerFactory.getUserContainer();
		User user = userContainer.getByLoginAndPassword(login, password);
		
		if(user != null && user.getId() != null) {
			initApp(application, user);
			return true;
		}
		
		return false;
	}
	
	public static void logout(Application application) {
		application.close();
	}
	
	private static void initApp(Application application, User user) {
		application.setUser(user);
		application.removeWindow(application.getMainWindow());
		application.init();
	}

	public static boolean updatePassword(User user, String currentPassword, String newPassword) {
		UserContainer userContainer = CisContainerFactory.getUserContainer();
		user = userContainer.getByLoginAndPassword(user.getLogin(), currentPassword);
		
		if(user != null && user.getId() != null) {
			user.setPassword(Utils.getMD5Hash(newPassword));
			userContainer.saveOrUpdateEntity(user);
			return true;
		}
		
		return false;
	}

	public static boolean restorePassword(String login) throws MessagingException {
		UserContainer userContainer = CisContainerFactory.getUserContainer();
		User user = userContainer.getByLogin(login);
		
		if(user == null || user.getId() == null) {
			return false;
		}
		
		String newPassword = Utils.generateRandomString(10);
		user.setPassword(Utils.getMD5Hash(newPassword));
		
		String message = CisConstants.uiTheNewGeneratedPasswordIs + ": " + newPassword;
		
		MailSender.send(user.getEmail(), CisConstants.uiNewPassword, message);
		
		userContainer.saveOrUpdateEntity(user);
		
		return true;
	}
	
}
