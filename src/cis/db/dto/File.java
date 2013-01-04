package cis.db.dto;


import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import enterpriseapp.hibernate.annotation.CrudField;
import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.annotation.Downloadable;
import enterpriseapp.hibernate.dto.Dto;


/**
 * The persistent class for the file database table.
 * 
 */
@Entity
@Table(name="file")
@CrudTable(filteringPropertyName="name")
public class File extends Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    @Temporal(TemporalType.DATE)
	private Date date;

	private String description;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@CrudField(showInTable=false)
	@Lob
	@Downloadable(propertyFileName="name")
	@Basic(fetch = LAZY)
	private byte[] data;

	//bi-directional many-to-one association to Category
    @ManyToOne
	@JoinColumn(name="category", nullable=false)
	private Category category;

	//bi-directional many-to-one association to Project
    @ManyToOne
	@JoinColumn(name="project")
	private Project project;
    
    public File() {
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

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
}