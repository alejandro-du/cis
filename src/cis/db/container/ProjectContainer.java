package cis.db.container;

import cis.db.dto.Project;
import enterpriseapp.hibernate.DefaultHbnContainer;


@SuppressWarnings("unchecked")
public class ProjectContainer extends DefaultHbnContainer<Project> {

	private static final long serialVersionUID = 1L;

	public ProjectContainer() {
		super(Project.class);
	}

}
