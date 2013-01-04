package cis.web.frontend;

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

class UpdateFieldWindow extends Window {

	private static final long serialVersionUID = 1L;
	
	private Panel panel = new Panel();
	private Label label = new Label();
	private TextField textField;
	
	public UpdateFieldWindow(String caption, String fieldCaption, final Button.ClickListener clickListener) {
		super(caption);
		setSizeUndefined();
		setResizable(false);
		setModal(true);
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeUndefined();
		
		panel.setVisible(false);
		panel.addComponent(label);
		
		FormLayout formLayout = new FormLayout();
		formLayout.setMargin(true);
		
		textField = new TextField(fieldCaption);
		
		final Button updateButton = new Button(CisConstants.uiModify) {
			private static final long serialVersionUID = 1L;
			@Override
			public Object getData() {
				return textField.getValue().toString();
			}
		};
		
		updateButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				textField.setComponentError(null);
				panel.setVisible(false);
				
				if(textField.getValue() == null || textField.getValue().toString().isEmpty()) {
					textField.setComponentError(new UserError(CisConstants.uiRequiredField));
				} else {
					clickListener.buttonClick(event);
					setMessage(CisConstants.uiSaved);
				}
			}
		});
		
		formLayout.addComponent(textField);
		formLayout.addComponent(updateButton);
		
		verticalLayout.addComponent(panel);
		verticalLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		verticalLayout.addComponent(formLayout);
		verticalLayout.setComponentAlignment(formLayout, Alignment.MIDDLE_CENTER);
		
		setContent(verticalLayout);
		textField.focus();
		updateButton.setClickShortcut(KeyCode.ENTER);
	}
	
	public void setMessage(String message) {
		label.setCaption(message);
		panel.setVisible(true);
	}
	
}