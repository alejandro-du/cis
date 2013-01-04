package cis.web.auth;

import cis.db.dto.User;
import cis.web.CisConstants;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.BaseTheme;

public class SessionControlComponent extends CustomComponent implements Button.ClickListener {

	private static final long serialVersionUID = 1L;
	private Button loginButton = new Button(CisConstants.uiInitSession);
	private Button updateButton = new Button(CisConstants.uiUpdatePassword);
	private Button restoreButton = new Button(CisConstants.uiRestorePassword);
	private Button logoutButton = new Button(CisConstants.uiLogout);
	
	public SessionControlComponent(User user) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		layout.setMargin(false, false, false, true);
		
		if(user == null || user.getId() == null) {
			layout.addComponent(restoreButton);
			layout.addComponent(loginButton);
			restoreButton.addListener(this);
			loginButton.addListener(this);
			restoreButton.setStyleName(BaseTheme.BUTTON_LINK);
			loginButton.setStyleName(BaseTheme.BUTTON_LINK);
		} else {
			layout.addComponent(new Label(user.getLogin() + " - "));
			layout.addComponent(updateButton);
			layout.addComponent(logoutButton);
			updateButton.addListener(this);
			logoutButton.addListener(this);
			updateButton.setStyleName(BaseTheme.BUTTON_LINK);
			logoutButton.setStyleName(BaseTheme.BUTTON_LINK);
		}
		
		setCompositionRoot(layout);
	}
	
	public void buttonClick(ClickEvent event) {
		if(event.getButton().equals(loginButton)) {
			getApplication().getMainWindow().addWindow(new LoginWindow());
		} else if(event.getButton().equals(updateButton)) {
			getApplication().getMainWindow().addWindow(new UpdatePasswordWindow());
			
		} else if(event.getButton().equals(restoreButton)) {
			getApplication().getMainWindow().addWindow(new RestorePasswordWindow());
			
		} else if(event.getButton().equals(logoutButton)) {
			AuthService.logout(getApplication());
		}
	}

}
