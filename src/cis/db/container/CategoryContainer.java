package cis.db.container;


import java.util.List;

import cis.db.dto.Category;
import enterpriseapp.hibernate.DefaultHbnContainer;


@SuppressWarnings("unchecked")
public class CategoryContainer extends DefaultHbnContainer<Category> {

	private static final long serialVersionUID = 1L;

	public CategoryContainer() {
		super(Category.class);
	}

	@Override
	public List<Category> listAll() {
		return query("from Category order by ordering");
	}

	public List<Category> listParents() {
		return query("from Category where category is null order by ordering");
	}

}
