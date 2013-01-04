package cis.web.frontend;

import java.util.List;

import cis.db.container.CategoryContainer;
import cis.db.container.CisContainerFactory;
import cis.db.dto.Category;
import cis.db.dto.Project;
import cis.db.dto.User;
import cis.web.CisConstants;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class CustomerComponent extends CustomComponent {
	
	private static final long serialVersionUID = 1L;

	public CustomerComponent(User user) {
		CategoryContainer categoryContainer = CisContainerFactory.getCategoryContainer();
		
		TabSheet tabsheet = new TabSheet();
		tabsheet.setSizeFull();
		
		boolean showSuggestions = false;
		boolean showTickets = false;
		
		if(user.getProjects() != null && !user.getProjects().isEmpty()) {
			for(Project project : user.getProjects()) {
				
				VerticalLayout layout = new VerticalLayout();
				layout.setMargin(true);
				layout.setSpacing(true);
				
				ProjectOverviewComponent projectOverviewComponent = new ProjectOverviewComponent(user, project);
				layout.addComponent(projectOverviewComponent);
				layout.addComponent(new Label("<hr/><br/>", Label.CONTENT_XHTML));
				
				if(project.getLocked()) {
					layout.addComponent(new Label(CisConstants.uiProjectLocked, Label.CONTENT_XHTML));
					
				} else {
					List<Category> categories = categoryContainer.listParents();
					
					if(project.getCategories() != null && !project.getCategories().isEmpty()) {
						int columns = project.getColumns() == null || project.getColumns() < 1 ? 2 : project.getColumns();
						
						GridLayout gridLayout = new GridLayout(columns, project.getCategories().size() / columns + 1);
						gridLayout.setSpacing(true);
						gridLayout.setSizeFull();
						
						int col = 0;
						int row = 0;
						
						for(Category c : categories) {
							if(project.getCategories().contains(c)) {
								gridLayout.addComponent(new FileComponent(project, c), col, row);
								col++;
								if(col >= columns) {
									col = 0;
									row++;
								}
							}
						}
						
						layout.addComponent(gridLayout);
					} else {
						layout.addComponent(new Label(CisConstants.uiNoFiles, Label.CONTENT_XHTML));
					}
					
					if(project.getShowTickets()) {
						showTickets = true;
					}
					
					if(project.getShowSuggestions()) {
						showSuggestions = true;
					}
					
				}
				
				tabsheet.addTab(layout, project.getName(), null);
			}
		}
		
		if(showTickets) {
			tabsheet.addTab(new TicketComponent(user), CisConstants.uiTickets, null);
		}
		
		if(showSuggestions) {
			tabsheet.addTab(new SuggestionComponent(), CisConstants.uiSuggestionBox, null);
		}
		
		setCompositionRoot(tabsheet);
	}

}
