package cis.web.auth;

import javax.mail.MessagingException;

import org.slf4j.LoggerFactory;

import cis.web.CisConstants;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class RestorePasswordWindow extends Window implements Button.ClickListener {

	private static final long serialVersionUID = 1L;
	
	private Panel panel = new Panel();
	private Label label = new Label();
	private TextField loginTf = new TextField(CisConstants.uiUserLogin);
	
	public RestorePasswordWindow() {
		super(CisConstants.uiRestorePassword);
		setModal(true);
		setSizeUndefined();
		setResizable(false);
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeUndefined();
		
		FormLayout formLayout = new FormLayout();
		formLayout.setMargin(true);
		
		Button restoreButton = new Button(CisConstants.uiRestore);
		restoreButton.addListener(this);
		
		panel.setVisible(false);
		panel.addComponent(label);
		
		formLayout.addComponent(loginTf);
		formLayout.addComponent(restoreButton);
		
		verticalLayout.addComponent(panel);
		verticalLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		verticalLayout.addComponent(formLayout);
		verticalLayout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
		
		setContent(verticalLayout);
		loginTf.focus();
		restoreButton.setClickShortcut(KeyCode.ENTER);
	}

	public void buttonClick(ClickEvent event) {
		loginTf.setComponentError(null);
		panel.setVisible(false);
		
		if (loginTf.getValue() == null || loginTf.getValue().toString().isEmpty()) {
			loginTf.setComponentError(new UserError(CisConstants.uiRequiredField));

		} else {
			try {
				if (!AuthService.restorePassword(loginTf.getValue().toString())) {
					label.setCaption(CisConstants.uiBadCredentials);
					panel.setVisible(true);
					
				} else {
					label.setCaption(CisConstants.uiPasswordSent);
					panel.setVisible(true);
				}
				
			} catch (MessagingException e) {
				LoggerFactory.getLogger(RestorePasswordWindow.class).error("Error sending email", e);
				getApplication().getMainWindow().showNotification(CisConstants.uiPasswordNotSent, Notification.TYPE_ERROR_MESSAGE);
			}
		}
	}
}
