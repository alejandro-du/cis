package cis.web.auth;

import cis.web.CisConstants;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LoginWindow extends Window implements Button.ClickListener {

	private static final long serialVersionUID = 1L;
	
	private Panel panel = new Panel();
	private Label label = new Label();
	private TextField loginTf = new TextField(CisConstants.uiUserLogin);
	private PasswordField passwordTf = new PasswordField(CisConstants.uiUserPassword);
	
	public LoginWindow() {
		setCaption(CisConstants.uiInitSession);
		setModal(true);
		setSizeUndefined();
		setResizable(false);
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeUndefined();
		
		FormLayout formLayout = new FormLayout();
		formLayout.setMargin(true);
		
		Button loginButton = new Button(CisConstants.uiInitSession);
		loginButton.addListener(this);
		
		panel.setVisible(false);
		panel.addComponent(label);
		
		formLayout.addComponent(loginTf);
		formLayout.addComponent(passwordTf);
		formLayout.addComponent(loginButton);
		
		verticalLayout.addComponent(panel);
		verticalLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		verticalLayout.addComponent(formLayout);
		verticalLayout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
		
		setContent(verticalLayout);
		loginTf.focus();
		loginButton.setClickShortcut(KeyCode.ENTER);
	}
	
	public void buttonClick(ClickEvent event) {
		loginTf.setComponentError(null);
		passwordTf.setComponentError(null);
		panel.setVisible(false);
		
		if(loginTf.getValue() == null || loginTf.getValue().toString().isEmpty()) {
			loginTf.setComponentError(new UserError(CisConstants.uiRequiredField));
			
		} else if(passwordTf.getValue() == null || passwordTf.getValue().toString().isEmpty()) {
			passwordTf.setComponentError(new UserError(CisConstants.uiRequiredField));
			
		} else {
			if(!AuthService.login(getApplication(), loginTf.getValue().toString(), passwordTf.getValue().toString())) {
				label.setCaption(CisConstants.uiBadCredentials);
				panel.setVisible(true);
				
			} else {
				this.close();
			}
		}
	}

}
