package cis.db.dto;


import java.io.Serializable;
import java.util.Date;

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

import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.dto.Dto;



/**
 * The persistent class for the ticket database table.
 * 
 */
@Entity
@Table(name="ticket")
@CrudTable(filteringPropertyName="title")
public class Ticket extends Dto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

    @Lob()
	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String priority;

	@Column(nullable = false)
	private String title;

	//bi-directional many-to-one association to Project
    @ManyToOne
	@JoinColumn(name="project", nullable=false)
	private Project project;
    
    @Column(nullable=false)
    private boolean closed;
    
    public Ticket() {
    }

	@Override
	public String toString() {
		return title;
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

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public boolean getClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}
	
}