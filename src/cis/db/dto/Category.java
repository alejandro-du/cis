package cis.db.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.annotation.Downloadable;
import enterpriseapp.hibernate.dto.Dto;



/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@CrudTable(filteringPropertyName="name")
public class Category extends Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	private Integer ordering;

	private boolean allowUpload;
	
	private String description;
	
	@Column
	@Lob
	@Downloadable(propertyFileName = "name", fileNameSufix=".png")
	private byte[] icon;

	//bi-directional many-to-one association to Category
    @ManyToOne
	@JoinColumn(name="category")
	private Category category;

	//bi-directional many-to-one association to Category
	@OneToMany(mappedBy="category")
	@OrderBy("ordering")
	private List<Category> categories;

	//bi-directional many-to-one association to File
	@OneToMany(mappedBy="category")
	private Set<File> files;
	
    public Category() {
    }

	@Override
	public String toString() {
		return name;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Object id) {
		this.id = (Long) id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrdering() {
		return this.ordering;
	}

	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}

	public boolean getAllowUpload() {
		return this.allowUpload;
	}

	public void setAllowUpload(boolean allowUpload) {
		this.allowUpload = allowUpload;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public Set<File> getFiles() {
		return this.files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

}