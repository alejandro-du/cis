package cis.web.frontend;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import cis.db.container.CisContainerFactory;
import cis.db.container.UserContainer;
import cis.db.dto.Project;
import cis.db.dto.User;
import cis.web.CisConstants;

import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class ProjectOverviewComponent extends CustomComponent implements Button.ClickListener {

	private static final long serialVersionUID = 1L;
	
	private Project project;
	private VerticalLayout logo = new VerticalLayout();
	private Label phoneLabel;
	private Label cellPhoneLabel;
	private Button updatePhoneButton = new Button(CisConstants.uiModify);
	private Button updateCellPhoneButton = new Button(CisConstants.uiModify);
	
	public ProjectOverviewComponent(User user, Project project) {
		
		this.project = project;
		
		updatePhoneButton.addListener(this);
		updateCellPhoneButton.addListener(this);
		updatePhoneButton.setStyleName(BaseTheme.BUTTON_LINK);
		updateCellPhoneButton.setStyleName(BaseTheme.BUTTON_LINK);
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSpacing(true);
		
		verticalLayout.addComponent(getComponent(CisConstants.uiProject, project.getName()));
		verticalLayout.addComponent(getComponent(CisConstants.uiCustomer, user.getName()));
		verticalLayout.addComponent(getComponent(CisConstants.uiEmail, user.getEmail()));
		verticalLayout.addComponent(getComponent(CisConstants.uiPhoneNumber, user.getPhoneNumber(), updatePhoneButton));
		verticalLayout.addComponent(getComponent(CisConstants.uiCellPhoneNumber, user.getPhoneNumber(), updateCellPhoneButton));
		
		if(project.getComments() != null && !project.getComments().isEmpty()) {
			verticalLayout.addComponent(getComponent(CisConstants.uiComments, project.getComments()));
		}
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSizeFull();
		horizontalLayout.setSpacing(true);
		horizontalLayout.addComponent(logo);
		horizontalLayout.addComponent(verticalLayout);
		horizontalLayout.setExpandRatio(verticalLayout, 1);
		
		setCompositionRoot(horizontalLayout);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void attach() {
		super.attach();
		Embedded embedded;
		
		if(project.getIcon() != null) {
			StreamResource streamResource = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				public InputStream getStream() {
					return new ByteArrayInputStream(project.getIcon());
				}
			}, project.getName() + ".png", getApplication());
			
			embedded = new Embedded(null, streamResource);
			
		} else {
			embedded = new Embedded("", new ThemeResource("project.png"));
		}
		
		logo.addComponent(embedded);
		logo.setWidth(embedded.getWidth());
	}
	
	private Component getComponent(String caption, String content) {
		return getComponent(caption, content, null);
	}
	
	private Component getComponent(String caption, String content, Component additionalComponent) {
		if(content == null) {
			content = "";
		}
		
		Label label = new Label("<i>" + caption + "</i>: <b>" + content + "</b>", Label.CONTENT_XHTML);
		label.setSizeFull();
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.addComponent(label);
		
		if(additionalComponent != null) {
			horizontalLayout.addComponent(additionalComponent);
			horizontalLayout.setSizeUndefined();
		} else {
			horizontalLayout.setSizeFull();
		}
		
		return horizontalLayout;
	}
	
	public void updatePhoneLabels() {
		User user = (User) getApplication().getUser();
		phoneLabel.setValue(user.getPhoneNumber());
		cellPhoneLabel.setValue(user.getCellPhoneNumber());
	}
	
	public void buttonClick(ClickEvent event) {
		if(event.getButton().equals(updatePhoneButton)) {
			getApplication().getMainWindow().addWindow(
				new UpdateFieldWindow(CisConstants.uiUpdatePhone, CisConstants.uiPhoneNumber, new Button.ClickListener() {
					private static final long serialVersionUID = 1L;
					public void buttonClick(ClickEvent event) {
						User user = (User) getApplication().getUser();
						user.setPhoneNumber(event.getButton().getData().toString());
						UserContainer userContainer = CisContainerFactory.getUserContainer();
						userContainer.saveOrUpdateEntity(user);
						updatePhoneLabels();
					}
				})
			);
			
		} else if(event.getButton().equals(updateCellPhoneButton)) {
			getApplication().getMainWindow().addWindow(
				new UpdateFieldWindow(CisConstants.uiUpdateCellPhone, CisConstants.uiCellPhoneNumber, new Button.ClickListener() {
					private static final long serialVersionUID = 1L;
					public void buttonClick(ClickEvent event) {
						User user = (User) getApplication().getUser();
						user.setCellPhoneNumber(event.getButton().getData().toString());
						UserContainer userContainer = CisContainerFactory.getUserContainer();
						userContainer.saveOrUpdateEntity(user);
						updatePhoneLabels();
					}
				})
			);
		}
	}

}

