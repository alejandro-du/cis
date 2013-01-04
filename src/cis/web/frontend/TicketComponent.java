package cis.web.frontend;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.LoggerFactory;

import cis.db.container.CisContainerFactory;
import cis.db.container.TicketContainer;
import cis.db.dto.Project;
import cis.db.dto.Ticket;
import cis.db.dto.User;
import cis.web.CisConstants;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import enterpriseapp.mail.MailSender;

public class TicketComponent extends CustomComponent implements Button.ClickListener {

	private static final long serialVersionUID = 1L;
	
	private Select projectSelect = new Select(CisConstants.uiProject);
	private TextField titleTf = new TextField(CisConstants.uiTicketTitle);
	private Select prioritySelect = new Select(CisConstants.uiPriority);
	private RichTextArea textArea = new RichTextArea();
	private Button sendButton = new Button(CisConstants.uiSend);
	private Button clearButton = new Button(CisConstants.uiClear);
	private VerticalLayout iconLayout1 = new VerticalLayout();
	private VerticalLayout iconLayout2 = new VerticalLayout();

	public TicketComponent(final User user) {
		
		iconLayout1.setWidth("64px");
		iconLayout2.setWidth("64px");
		
		titleTf.setWidth("313px");
		
		projectSelect.setWidth("100%");
		
		for(Project project : user.getProjects()) {
			if(project.getShowTickets()) {
				projectSelect.addItem(project);
			}
		}
		
		Label titleLabel = new Label("<h1>" + CisConstants.uiTickets + "</h1>");
		titleLabel.setContentMode(Label.CONTENT_XML);
		Label descriptionLabel = new Label(CisConstants.uiTicketsDescription, Label.CONTENT_XML);
		
		prioritySelect.setWidth("100%");
		prioritySelect.addItem(CisConstants.uiNormalPriority);
		prioritySelect.addItem(CisConstants.uiHighPriority);
		
		HorizontalLayout fieldsLayout = new HorizontalLayout();
		fieldsLayout.setWidth("100%");
		fieldsLayout.setSpacing(true);
		fieldsLayout.addComponent(projectSelect);
		fieldsLayout.addComponent(prioritySelect);
		
		titleTf.setWidth("100%");
		
		textArea.setWidth("100%");
		
		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);
		buttonsLayout.addComponent(clearButton);
		buttonsLayout.addComponent(sendButton);
		
		sendButton.addListener(this);
		clearButton.addListener(this);
		
		VerticalLayout newTicketLayout = new VerticalLayout();
		newTicketLayout.setSizeFull();
		newTicketLayout.setSpacing(true);
		newTicketLayout.addComponent(titleLabel);
		newTicketLayout.addComponent(descriptionLabel);
		newTicketLayout.addComponent(fieldsLayout);
		newTicketLayout.addComponent(titleTf);
		newTicketLayout.addComponent(textArea);
		newTicketLayout.addComponent(buttonsLayout);
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();
		layout.addComponent(iconLayout1);
		layout.addComponent(newTicketLayout);
		layout.setExpandRatio(newTicketLayout, 1);
		
		Label closedTicketsLabel = new Label("<h1>" + CisConstants.uiClosedTickets + "<h1/>", Label.CONTENT_XHTML);
		
		VerticalLayout closedTicketsLayout = new VerticalLayout();
		closedTicketsLayout.setSizeFull();
		closedTicketsLayout.addComponent(closedTicketsLabel);
		
		layout.addComponent(iconLayout2);
		layout.addComponent(closedTicketsLayout);
		layout.setExpandRatio(closedTicketsLayout, 1);
		
		List<Ticket> closedTickets = CisContainerFactory.getTicketContainer().listClosedByUser(user);
		
		if(closedTickets != null && !closedTickets.isEmpty()) {
			
			Table table = new Table();
			table.setSizeFull();
			table.setHeight("335px");
			table.addContainerProperty("project", String.class, "", CisConstants.uiProject, null, null);
			table.addContainerProperty("title", String.class, "", CisConstants.uiTicketTitle, null, null);
			
			for(Ticket ticket : closedTickets) {
				table.addItem(new String[] {ticket.getProject().getName(), ticket.getTitle()}, ticket.getId());
			}
			
			closedTicketsLayout.addComponent(table);
			closedTicketsLayout.setExpandRatio(table, 1);
			
		} else {
			closedTicketsLayout.addComponent(new Label(CisConstants.uiNoClosedTickets, Label.CONTENT_XHTML));
		}
		
		setCompositionRoot(layout);
	}

	public void buttonClick(ClickEvent event) {
		titleTf.setComponentError(null);
		prioritySelect.setComponentError(null);
		textArea.setComponentError(null);
		
		if(event.getButton().equals(sendButton)) {
			if(projectSelect.getValue() == null) {
				projectSelect.setComponentError(new UserError(CisConstants.uiRequiredField));
			} else if(titleTf.getValue() == null || titleTf.getValue().toString().isEmpty()) {
				titleTf.setComponentError(new UserError(CisConstants.uiRequiredField));
			} else if(prioritySelect.getValue() == null || prioritySelect.getValue().toString().isEmpty()) {
				prioritySelect.setComponentError(new UserError(CisConstants.uiRequiredField));
			} else if(textArea.getValue() == null || textArea.getValue().toString().isEmpty()) {
				textArea.setComponentError(new UserError(CisConstants.uiRequiredField));
			} else {
				Ticket ticket = new Ticket();
				ticket.setTitle(titleTf.getValue().toString());
				ticket.setPriority(prioritySelect.getValue().toString());
				ticket.setDescription(textArea.getValue().toString());
				ticket.setProject((Project) projectSelect.getValue());
				ticket.setDate(new Date());
				
				TicketContainer ticketContainer = CisContainerFactory.getTicketContainer();
				ticketContainer.saveOrUpdateEntity(ticket);
				
				String title = CisConstants.uiTicketSent + " " + CisConstants.uiPriority + " " + prioritySelect.getValue() + ". " + titleTf.getValue();
				String description = textArea.getValue().toString();
				
				LoggerFactory.getLogger(TicketComponent.class).info(title + "\n" + "Description: " + description);
				
				try {
					MailSender.send(((User) getApplication().getUser()).getEmail(), title, description);
					
					if(ticket.getProject().getSendNewTicketNotificationsToCis()) {
						MailSender.send(CisConstants.mailSmtpAddress, title, description);
					}
					
					getApplication().getMainWindow().showNotification(CisConstants.uiTicketSent);
				} catch (MessagingException e) {
					LoggerFactory.getLogger(TicketComponent.class).error("Error sending email", e);
					getApplication().getMainWindow().showNotification(CisConstants.uiTicketNotSent, Notification.TYPE_ERROR_MESSAGE);
				}
				
				textArea.setValue("");
				titleTf.setValue("");
				prioritySelect.setValue("");
			}
		} else {
			textArea.setValue("");
		}
		
	}
	
	@Override
	public void attach() {
		super.attach();
		iconLayout1.addComponent(new Embedded("", new ThemeResource("tickets2.png")));
		iconLayout2.addComponent(new Embedded("", new ThemeResource("tickets3.png")));
	}
	
}
