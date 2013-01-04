package cis.web;

import cis.db.dto.User;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextArea;

import enterpriseapp.ui.crud.DefaultCrudFieldFactory;
import enterpriseapp.ui.crud.FieldContainer;

public class CisFieldFactory extends DefaultCrudFieldFactory {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Field createCustomField(Object bean, final Item item, String pid, Component uiContext, Class<?> propertyType) {
		Field field = null;
		final FieldContainer fieldContainer = (FieldContainer) uiContext;
		
		if(User.class.equals(bean.getClass())) {
			final User user = (User) bean;
			
			if(pid.equals("role")) {
				Select select = new Select();
				select.addItem(CisConstants.roleCustomer);
				select.addItem(CisConstants.roleEmployee);
				select.setNullSelectionAllowed(false);
				
				select.addListener(new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						boolean projectsEnabled = user != null && CisConstants.roleCustomer.equalsIgnoreCase("" + event.getProperty().getValue());
						boolean accessEnabled = !projectsEnabled;
						
						Field projectsField = fieldContainer.getField("projects", item);
						Field usersAccessField = fieldContainer.getField("usersAccess", item);
						Field projectsAccessField = fieldContainer.getField("projectsAccess", item);
						Field ticketsAccessField = fieldContainer.getField("ticketsAccess", item);
						Field filesAccessField = fieldContainer.getField("filesAccess", item);
						Field categoriesAccessField = fieldContainer.getField("categoriesAccess", item);
						
						if(projectsField != null) {
							projectsField.setEnabled(projectsEnabled);
							
							if(!projectsEnabled) {
								projectsField.setValue(null);
							}
						}
						
						if(usersAccessField != null && projectsAccessField != null && ticketsAccessField != null & filesAccessField != null && categoriesAccessField != null) {
							usersAccessField.setEnabled(accessEnabled);
							projectsAccessField.setEnabled(accessEnabled);
							ticketsAccessField.setEnabled(accessEnabled);
							filesAccessField.setEnabled(accessEnabled);
							categoriesAccessField.setEnabled(accessEnabled);
							
							if(!accessEnabled) {
								usersAccessField.setValue(false);
								projectsAccessField.setValue(false);
								ticketsAccessField.setValue(false);
								filesAccessField.setValue(false);
								categoriesAccessField.setValue(false);
							}
						}
					}
				});
				
				field = select;
				
			} else if(pid.equals("projects")) {
				Field projectsField = createEntityField(pid, bean, propertyType);
				
				if(projectsField != null) {
					projectsField.setEnabled(user != null && CisConstants.roleCustomer.equalsIgnoreCase(user.getRole()));
				}
				
				field = projectsField;
					
			} else if(pid.equals("usersAccess") || pid.equals("projectsAccess") || pid.equals("ticketsAccess") || pid.equals("filesAccess") || pid.equals("categoriesAccess")) {
				if(user != null && CisConstants.roleCustomer.equalsIgnoreCase(user.getRole())) {
					field = createFieldByPropertyType(Boolean.class);
					field.setEnabled(false);
				}
			}
		}
		
		if(pid.equals("priority")) {
			Select select = new Select();
			select.addItem(CisConstants.uiNormalPriority);
			select.addItem(CisConstants.uiHighPriority);
			select.setNullSelectionAllowed(false);
			field = select;
		}
		
		if(pid.equals("comments")) {
			TextArea textArea = new TextArea();
			field = textArea;
		}
		
		return field;
	}
	
}
