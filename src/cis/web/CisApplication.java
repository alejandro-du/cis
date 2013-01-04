package cis.web;

import cis.db.Bootstrap;
import cis.db.container.CisContainerFactory;
import cis.db.dto.User;
import enterpriseapp.EnterpriseApplication;
import enterpriseapp.hibernate.ContainerFactory;

public class CisApplication extends EnterpriseApplication {
	
	private static final long serialVersionUID = 1L;
	
	private static boolean firstInit = true;
	
	@Override
	public void init() {
		super.init();
		ContainerFactory.init(new CisContainerFactory());
		MainWindow mainWindow;
		mainWindow = new MainWindow((User) getUser());
		setMainWindow(mainWindow);
		setTheme("cis");
		
		if(firstInit) {
			Bootstrap.execute();
			firstInit = false;
		}
	}
	
}
