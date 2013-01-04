package cis.web.auth;

import cis.db.dto.User;
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
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class UpdatePasswordWindow extends Window implements Button.ClickListener  {

	private static final long serialVersionUID = 1L;

	private Panel panel = new Panel();
	private Label label = new Label();
	private PasswordField currentPasswordTf = new PasswordField(CisConstants.uiCurrentPassword);
	private PasswordField newPasswordTf = new PasswordField(CisConstants.uiNewPassword);
	private PasswordField newPasswordConfirmationTf = new PasswordField(CisConstants.uiNewPasswordConfirmation);

	public UpdatePasswordWindow() {
		super(CisConstants.uiUpdatePassword);
		setModal(true);
		setSizeUndefined();
		setResizable(false);
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeUndefined();
		
		FormLayout formLayout = new FormLayout();
		formLayout.setMargin(true);
		
		Button updateButton = new Button(CisConstants.uiModify);
		updateButton.addListener(this);
		
		panel.setVisible(false);
		panel.addComponent(label);
		
		formLayout.addComponent(currentPasswordTf);
		formLayout.addComponent(newPasswordTf);
		formLayout.addComponent(newPasswordConfirmationTf);
		formLayout.addComponent(updateButton);
		
		verticalLayout.addComponent(panel);
		verticalLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		verticalLayout.addComponent(formLayout);
		verticalLayout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
		
		setContent(verticalLayout);
		currentPasswordTf.focus();
		updateButton.setClickShortcut(KeyCode.ENTER);
	}

	public void buttonClick(ClickEvent event) {
		panel.setVisible(false);
		currentPasswordTf.setComponentError(null);
		newPasswordTf.setComponentError(null);
		newPasswordConfirmationTf.setComponentError(null);
		
		if(currentPasswordTf.getValue() == null || currentPasswordTf.getValue().toString().isEmpty()) {
			currentPasswordTf.setComponentError(new UserError(CisConstants.uiRequiredField));
			
		} else if(newPasswordTf.getValue() == null || newPasswordTf.getValue().toString().isEmpty()) {
			newPasswordTf.setComponentError(new UserError(CisConstants.uiRequiredField));
			
		} else if(!newPasswordTf.getValue().equals(newPasswordConfirmationTf.getValue())) {
			newPasswordConfirmationTf.setComponentError(new UserError(CisConstants.uiPasswordDoesNotMatch));
			
		} else if(!AuthService.updatePassword((User) getApplication().getUser(), currentPasswordTf.getValue().toString(), newPasswordTf.getValue().toString())) {
			label.setCaption(CisConstants.uiBadCredentials);
			panel.setVisible(true);
			
		} else {
			label.setCaption(CisConstants.uiSaved);
			panel.setVisible(true);
		}
	}

}
