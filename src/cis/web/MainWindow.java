package cis.web;

import cis.db.dto.User;
import cis.web.auth.SessionControlComponent;
import cis.web.backend.EmployeeComponent;
import cis.web.frontend.CustomerComponent;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Runo;

public class MainWindow extends Window {
	
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout mainLayout = new VerticalLayout();
	private User user;

	public MainWindow(User user) {
		super(CisConstants.uiMainWindowCaption);
		this.user = user;
		
		SessionControlComponent header = new SessionControlComponent(user);
		header.setSizeUndefined();
		
		mainLayout.setMargin(true);
		mainLayout.addComponent(header);
		mainLayout.setComponentAlignment(header, Alignment.TOP_RIGHT);
		
		setContent(mainLayout);
	}
	
	@Override
	public void attach() {
		super.attach();
		
		Component component;
		
		if(user != null) {
			
			if(user.getRole().equalsIgnoreCase(CisConstants.roleCustomer)) {
				component = new CustomerComponent(user);
				
			} else {
				component = new EmployeeComponent(user);
			}
			
		} else {
			component = new PublicComponent();
		}
		
		mainLayout.setSizeFull();
		
		component.setSizeFull();
		mainLayout.addComponent(component);
		mainLayout.setExpandRatio(component, 1);
		
		Label footerLabel = new Label("&nbsp;&nbsp;<b>Alejandro Duarte (alejandro.d.a@gmail.com)</b>. All rights reserved.", Label.CONTENT_XHTML);
		footerLabel.setStyleName(Runo.LABEL_SMALL);
		
		HorizontalLayout footerLayout = new HorizontalLayout();
		footerLayout.setWidth("100%");
		footerLayout.addComponent(footerLabel);
		
		mainLayout.addComponent(footerLayout);
	}
	
}
