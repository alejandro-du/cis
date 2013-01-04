package cis.web.frontend;

import java.util.Date;

import org.vaadin.easyuploads.UploadField;

import cis.db.container.CisContainerFactory;
import cis.db.container.FileContainer;
import cis.db.dto.Category;
import cis.db.dto.File;
import cis.db.dto.Project;
import cis.web.CisConstants;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.BaseTheme;

public class UploadComponent extends CustomComponent implements Button.ClickListener {
	
	private static final long serialVersionUID = 1L;
	
	private Project project;
	private Category category;
	private VerticalLayout filesVerticalLayout = new VerticalLayout();
	
	public UploadComponent(Project project, Category category) {
		this.project = project;
		this.category = category;
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);
		setCompositionRoot(verticalLayout);
		
		verticalLayout.addComponent(filesVerticalLayout);
		
		Button button = new Button(CisConstants.uiAddAnother);
		button.setStyleName(BaseTheme.BUTTON_LINK);
		button.addListener(this);
		verticalLayout.addComponent(button);
		
		add();
	}
	
	private void add() {
		
		final TextField textField = new TextField();
		textField.setWidth("200px");
		textField.setRequired(true);
		textField.setImmediate(true);
		
		final UploadField uploadField = new UploadField() {
			
			private static final long serialVersionUID = 1L;
			@Override
			protected String getDisplayDetails() {
				return getLastFileName();
			}
			
			@Override
			public void uploadStarted(StartedEvent event) {
				if(textField.getValue() == null || textField.getValue().toString().isEmpty()) {
					textField.setComponentError(new UserError(CisConstants.uiRequiredField));
				}
				super.uploadStarted(event);
			}
			
			@Override
			public void uploadFinished(FinishedEvent event) {
				if(textField.isValid()) {
					super.uploadFinished(event);
					textField.setEnabled(false);
					
					File file = new File();
					file.setProject(project);
					file.setCategory(category);
					file.setDate(new Date());
					file.setDescription(textField.getValue().toString());
					file.setName(getLastFileName());
					file.setData((byte[]) getValue());
					
					FileContainer fileContainer = CisContainerFactory.getFileContainer();
					fileContainer.saveOrUpdateEntity(file);
					textField.setComponentError(null);
				}
			}
		};
		
		uploadField.setFieldType(UploadField.FieldType.BYTE_ARRAY);
		uploadField.setButtonCaption(CisConstants.uiUploadFile);
		uploadField.setFileDeletesAllowed(false);
		uploadField.setEnabled(false);
		
		textField.addListener(new FieldEvents.TextChangeListener() {
			private static final long serialVersionUID = 1L;
			public void textChange(TextChangeEvent event) {
				uploadField.setEnabled(event.getText() != null && !event.getText().isEmpty());
			}
		});
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.addComponent(new Label(CisConstants.uiFileDescription));
		horizontalLayout.addComponent(textField);
		horizontalLayout.addComponent(uploadField);
		
		filesVerticalLayout.addComponent(horizontalLayout);
		filesVerticalLayout.setSpacing(true);
	}

	public void buttonClick(ClickEvent event) {
		add();
	}
}