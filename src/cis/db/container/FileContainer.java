package cis.db.container;


import java.util.Collection;

import cis.db.dto.File;
import enterpriseapp.hibernate.DefaultHbnContainer;


@SuppressWarnings("unchecked")
public class FileContainer extends DefaultHbnContainer<File> {

	private static final long serialVersionUID = 1L;
	
	public FileContainer() {
		super(File.class);
	}

	public Collection<File> listByProjectIdAndCategoryId(Long projectId, Long categoryId) {
		return query("from File where project.id = ? and category.id = ? order by date", new Object[] {projectId, categoryId});
	}

}
