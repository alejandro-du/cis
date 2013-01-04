package cis.db;

import cis.db.container.CisContainerFactory;
import cis.db.dto.User;
import cis.web.CisConstants;

public class Bootstrap {
	
	public static void execute() {
		if(CisContainerFactory.getUserContainer().count() == 0) {
			createUsers();
		}
	}

	public static void createUsers() {
		User user = new User();
		user.setLogin("admin");
		user.setPassword("admin");
		user.setName("admin");
		user.setRole(CisConstants.roleEmployee);
		user.setEmail("customer.interaction.system@gmail.com");
		user.setUsersAccess(true);
		
		CisContainerFactory.getUserContainer().saveEntity(user);
	}

}
