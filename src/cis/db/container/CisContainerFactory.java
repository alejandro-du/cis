package cis.db.container;

import cis.db.dto.Category;
import cis.db.dto.File;
import cis.db.dto.Project;
import cis.db.dto.Ticket;
import cis.db.dto.User;
import enterpriseapp.hibernate.ContainerFactory;
import enterpriseapp.hibernate.DefaultHbnContainer;

public class CisContainerFactory extends ContainerFactory {
	
	@Override
	public DefaultHbnContainer<?> getContainer(Class<?> clazz) {
		
		if(clazz.equals(Category.class)) {
			return getCategoryContainer();
		} else if(clazz.equals(File.class)) {
				return getFileContainer();
		} else if(clazz.equals(Project.class)) {
			return getProjectContainer();
		} else if(clazz.equals(Ticket.class)) {
			return getTicketContainer();
		} else if(clazz.equals(User.class)) {
			return getUserContainer();
		}
		
		return getDefaultFactory().getContainer(clazz);
	}
	
	public static CategoryContainer getCategoryContainer() {
		return new CategoryContainer();
	}
	
	public static FileContainer getFileContainer() {
		return new FileContainer();
	}
	
	public static ProjectContainer getProjectContainer() {
		return new ProjectContainer();
	}
	
	public static TicketContainer getTicketContainer() {
		return new TicketContainer();
	}
	
	public static UserContainer getUserContainer() {
		return new UserContainer();
	}
	
}
