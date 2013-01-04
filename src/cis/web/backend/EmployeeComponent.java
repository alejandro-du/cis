package cis.web.backend;

import cis.db.dto.Category;
import cis.db.dto.Project;
import cis.db.dto.Ticket;
import cis.db.dto.User;
import cis.web.CisConstants;
import cis.web.CisFieldFactory;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;

import enterpriseapp.ui.crud.CrudComponent;

public class EmployeeComponent extends CustomComponent {
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	private TabSheet tabsheet;
	private CrudComponent<User> userComponent;
	private CrudComponent<Project> projectComponent;
	private CrudComponent<Ticket> ticketComponent;
	private FileCrudComponent fileComponent;
	private CrudComponent<Category> categoryComponent;

	public EmployeeComponent(User user) {
		this.user = user;
		
		tabsheet = new TabSheet();
		tabsheet.setSizeFull();
		
		setCompositionRoot(tabsheet);
	}
	
	@Override
	public void attach() {
		super.attach();
		
		if(user.getTicketsAccess()) {
			ticketComponent = new CrudComponent<Ticket>(Ticket.class, new CisFieldFactory());
			ticketComponent.setSizeFull();
			tabsheet.addTab(ticketComponent, CisConstants.uiTickets, new ThemeResource("tickets.png"));
		}
		
		if(user.getFilesAccess()) {
			fileComponent = new FileCrudComponent();
			fileComponent.setSizeFull();
			tabsheet.addTab(fileComponent, CisConstants.uiFiles, new ThemeResource("files.png"));
		}
		
		if(user.getProjectsAccess()) {
			projectComponent = new CrudComponent<Project>(Project.class, new CisFieldFactory());
			projectComponent.setSizeFull();
			tabsheet.addTab(projectComponent, CisConstants.uiProjects, new ThemeResource("projects.png"));
		}
		
		if(user.getCategoriesAccess()) {
			categoryComponent = new CrudComponent<Category>(Category.class, new CisFieldFactory());
			categoryComponent.setSizeFull();
			tabsheet.addTab(categoryComponent, CisConstants.uiCategories, new ThemeResource("categories.png"));
		}
		
		if(user.getUsersAccess()) {
			userComponent = new CrudComponent<User>(User.class, new CisFieldFactory());
			userComponent.setSizeFull();
			tabsheet.addTab(userComponent, CisConstants.uiUsers, new ThemeResource("users.png"));
		}
	}

}
