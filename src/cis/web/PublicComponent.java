package cis.web;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;

public class PublicComponent extends CustomComponent {
	
	private static final long serialVersionUID = 1L;
	
	private VerticalLayout welcomeLayout;

	public PublicComponent() {
		welcomeLayout = new VerticalLayout();
		welcomeLayout.setSizeFull();
		
		setCompositionRoot(welcomeLayout);
	}
	
	@Override
	public void attach() {
		super.attach();
		
		ThemeResource resource = new ThemeResource("welcome-content.html");
		
		Embedded embedded = new Embedded("", resource);
		embedded.setType(Embedded.TYPE_BROWSER);
		embedded.setSizeFull();
		
		welcomeLayout.addComponent(embedded);
	}
	
}
