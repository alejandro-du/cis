package cis.web.frontend;

import javax.mail.MessagingException;

import org.slf4j.LoggerFactory;

import cis.web.CisConstants;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.Runo;

import enterpriseapp.mail.MailSender;

public class SuggestionComponent extends CustomComponent implements Button.ClickListener {

	private static final long serialVersionUID = 1L;
	
	private RichTextArea textArea = new RichTextArea();
	private Button sendButton = new Button(CisConstants.uiSend);
	private Button clearButton = new Button(CisConstants.uiClear);
	private VerticalLayout iconLayout = new VerticalLayout();
	
	public SuggestionComponent() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.setMargin(true);
		horizontalLayout.addComponent(iconLayout);
		
		iconLayout.setWidth("64px");
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSpacing(true);
		horizontalLayout.addComponent(verticalLayout);
		
		Label titleLabel = new Label("<h1>" + CisConstants.uiSuggestionBox + "</h1>", Label.CONTENT_XML);
		Label descriptionLabel = new Label(CisConstants.uiSuggestionBoxDescription, Label.CONTENT_XML);
		Panel descriptionPanel = new Panel();
		descriptionPanel.setWidth("500px");
		descriptionPanel.addComponent(descriptionLabel);
		descriptionPanel.setStyleName(Runo.PANEL_LIGHT);
		
		verticalLayout.addComponent(titleLabel);
		verticalLayout.addComponent(descriptionPanel);
		verticalLayout.addComponent(textArea);
		
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);
		buttonsLayout.addComponent(clearButton);
		buttonsLayout.addComponent(sendButton);
		
		verticalLayout.addComponent(buttonsLayout);
		
		sendButton.addListener(this);
		clearButton.addListener(this);
		
		setCompositionRoot(horizontalLayout);
	}

	public void buttonClick(ClickEvent event) {
		textArea.setComponentError(null);
		
		if(event.getButton().equals(sendButton)) {
			if(textArea.getValue() == null || textArea.getValue().toString().isEmpty()) {
				textArea.setComponentError(new UserError(CisConstants.uiRequiredField));
			} else {
				try {
					MailSender.send(CisConstants.emailSuggestionBoxEmail, CisConstants.uiSuggestionBox, textArea.getValue().toString());
					LoggerFactory.getLogger(SuggestionComponent.class).info(CisConstants.uiSuggestionBox + ": " + textArea.getValue().toString());
					getApplication().getMainWindow().showNotification(CisConstants.uiSuggestionSent);
					
				} catch (MessagingException e) {
					LoggerFactory.getLogger(SuggestionComponent.class).error("Error sending email", e);
					getApplication().getMainWindow().showNotification(CisConstants.uiSuggestionNotSent, Notification.TYPE_ERROR_MESSAGE);
				}
			}
		}
		
		textArea.setValue("");
	}
	
	@Override
	public void attach() {
		super.attach();
		iconLayout.addComponent(new Embedded("", new ThemeResource("suggestions.png")));
	}
	
}
