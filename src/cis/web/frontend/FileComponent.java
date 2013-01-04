package cis.web.frontend;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

import cis.db.container.CisContainerFactory;
import cis.db.container.FileContainer;
import cis.db.dto.Category;
import cis.db.dto.File;
import cis.db.dto.Project;
import cis.web.CisConstants;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class FileComponent extends CustomComponent {

	private static final long serialVersionUID = 1L;
	private Project project;
	private VerticalLayout iconLayout = new VerticalLayout();
	private Category rootCategory;
	
	public FileComponent(final Project project, final Category rootCategory) {
		
		this.project = project;
		this.rootCategory = rootCategory;
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(true);
		horizontalLayout.setSizeFull();
		horizontalLayout.addComponent(iconLayout);
		
		iconLayout.setWidth("64px");
		
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.setSpacing(true);
		horizontalLayout.addComponent(verticalLayout);
		horizontalLayout.setExpandRatio(verticalLayout, 1);
		
		addCategory(rootCategory, verticalLayout);
		
		if(rootCategory.getCategories() != null && !rootCategory.getCategories().isEmpty()) {
			for(Category c : rootCategory.getCategories()) {
				addCategory(c, verticalLayout);
			}
		}
		
		setCompositionRoot(horizontalLayout);
	}
	
	@Override
	public void attach() {
		super.attach();
		if(rootCategory.getIcon() != null) {
			StreamResource streamResource = new StreamResource(new StreamResource.StreamSource() {
				private static final long serialVersionUID = 1L;
				public InputStream getStream() {
					return new ByteArrayInputStream(rootCategory.getIcon());
				}
			}, rootCategory.getName() + ".png", getApplication());
			
			iconLayout.addComponent(new Embedded(null, streamResource));
		} else {
			iconLayout.addComponent(new Embedded("", new ThemeResource("category.png")));
		}
	}
	
	private void addCategory(final Category category, VerticalLayout verticalLayout) {
		Label titleLabel;
		
		if(category.getCategory() == null) {
			titleLabel = new Label("<h1>" + category.getName() + "</h1>", Label.CONTENT_XHTML);
		} else {
			titleLabel = new Label("<h3>" + category.getName() + "</h3>", Label.CONTENT_XHTML);
		}
		

		verticalLayout.addComponent(titleLabel);
		
		if(category.getDescription() != null && !category.getDescription().isEmpty()) {
			Label descLabel = new Label(category.getDescription(), Label.CONTENT_XHTML);
			descLabel.setSizeFull();
			verticalLayout.addComponent(descLabel);
		}

		FileContainer fileContainer = CisContainerFactory.getFileContainer();
		final IndexedContainer container = new IndexedContainer(fileContainer.listByProjectIdAndCategoryId(project.getId(), category.getId()));
		
		if(container.size() <= 0 && !category.getAllowUpload()) {
			verticalLayout.addComponent(new Label(CisConstants.uiNoFiles + "<br/><br/>", Label.CONTENT_XHTML));
			
		} else {
			Table table = new Table();
			table.setContainerDataSource(container);
			table.setPageLength(0);
			table.setSizeFull();
			
			table.addContainerProperty("date", Date.class, null, CisConstants.uiFileDate, null, null);
			table.addContainerProperty("description", String.class, "", CisConstants.uiFileDescription, null, null);
			table.addContainerProperty("fileSize", String.class, "", CisConstants.uiFileSize, null, null);
			table.addContainerProperty("download", String.class, "", CisConstants.uiDownloadFile, null, null);
			
			table.addGeneratedColumn("date", new Table.ColumnGenerator() {
				private static final long serialVersionUID = 1L;
				public Component generateCell(Table source, Object itemId, Object columnId) {
					Date date = ((File) itemId).getDate();
					if(date == null) {
						return new Label("");
					}
					
					return new Label(date.toString());
				}
			});
			
			table.addGeneratedColumn("description", new Table.ColumnGenerator() {
				private static final long serialVersionUID = 1L;
				public Component generateCell(Table source, Object itemId, Object columnId) {
					return new Label(((File) itemId).getDescription());
				}
			});
			
			table.addGeneratedColumn("fileSize", new Table.ColumnGenerator() {
				private static final long serialVersionUID = 1L;
				public Component generateCell(Table source, Object itemId, Object columnId) {
					return new Label("" + (((File) itemId).getData().length / 1024) + " KB");
				}
			});
			
			table.addGeneratedColumn("download", new Table.ColumnGenerator() {
				private static final long serialVersionUID = 1L;
				public Component generateCell(Table source, Object itemId, Object columnId) {
					final File file = (File) itemId;
					return new Link(CisConstants.uiDownloadFile, new StreamResource(new StreamResource.StreamSource() {
						private static final long serialVersionUID = 1L;
						public InputStream getStream() {
							return new ByteArrayInputStream(file.getData());
						}
					}, file.getName(), getApplication()));
				}
			});
			
			if(category.getAllowUpload()) {
				verticalLayout.addComponent(new UploadComponent(project, category));
				Label uploadedLabel = new Label(CisConstants.uiUploadedFiles);
				uploadedLabel.setContentMode(Label.CONTENT_XHTML);
				verticalLayout.addComponent(uploadedLabel);
			}
			
			table.setColumnExpandRatio("description", 1);
			table.setColumnWidth("date", 90);
			
			verticalLayout.addComponent(table);
			verticalLayout.addComponent(new Label("<br/>", Label.CONTENT_XHTML));
		}
		
	}
	
}
