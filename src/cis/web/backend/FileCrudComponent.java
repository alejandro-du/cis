package cis.web.backend;

import javax.mail.MessagingException;

import cis.db.container.CisContainerFactory;
import cis.db.dto.File;
import cis.db.dto.Project;
import cis.db.dto.User;
import cis.web.CisConstants;
import cis.web.CisFieldFactory;

import com.vaadin.ui.Window.Notification;

import enterpriseapp.mail.MailSender;
import enterpriseapp.ui.crud.CrudComponent;

public class FileCrudComponent extends CrudComponent<File> {
	
	private static final long serialVersionUID = 1L;

	public FileCrudComponent() {
		super(File.class, new CisFieldFactory());
	}
	
	@Override
	public void saveOrUpdate(File file) {
		super.saveOrUpdate(file);
		
		if(file.getId() == null) {
			sendNotifications(file.getProject());
		}
	}
	
	private void sendNotifications(Project project) {
		project = CisContainerFactory.getProjectContainer().getEntity(project.getId());
		
		for(User user : project.getUsers()) {
			if(project.getSendNewFileNotificationsToCustomers()) {
				try {
					MailSender.send(user.getEmail(), CisConstants.uiNewFileNotificationSubject, CisConstants.uiNewFileNotificationMessage);
				} catch (MessagingException e) {
					getApplication().getMainWindow().showNotification(CisConstants.uiNewFileNotificationNotSent(user.getEmail()), Notification.TYPE_ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
		}
	}

}
