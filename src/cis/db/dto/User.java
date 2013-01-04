package cis.db.dto;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import enterpriseapp.hibernate.annotation.CrudField;
import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.dto.Dto;



/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@CrudTable(filteringPropertyName="login")
public class User extends Dto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String cellPhoneNumber;

	@CrudField(isEmail=true)
	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String login;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@CrudField(showInTable=false, isPassword=true)
	private String password;

	private String phoneNumber;

	@Column(nullable = false)
	private String role;
	
	@Column(nullable = false)
	private boolean usersAccess = false;
	
	@Column(nullable = false)
	private boolean projectsAccess = false;
	
	@Column(nullable = false)
	private boolean ticketsAccess = false;
	
	@Column(nullable = false)
	private boolean filesAccess = false;
	
	@Column(nullable = false)
	private boolean categoriesAccess = false;
	

	//bi-directional many-to-many association to Project
    @ManyToMany
	@JoinTable(
		name="user_has_project"
		, joinColumns={
			@JoinColumn(name="customer")
			}
		, inverseJoinColumns={
			@JoinColumn(name="project")
			}
		)
	private Set<Project> projects;

    public User() {
    }

	@Override
	public String toString() {
		return login;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Object id) {
		this.id = (Long) id;
	}

	public String getCellPhoneNumber() {
		return this.cellPhoneNumber;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Project> getProjects() {
		return this.projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public boolean getUsersAccess() {
		return usersAccess;
	}

	public void setUsersAccess(boolean usersAccess) {
		this.usersAccess = usersAccess;
	}

	public boolean getProjectsAccess() {
		return projectsAccess;
	}

	public void setProjectsAccess(boolean projectsAccess) {
		this.projectsAccess = projectsAccess;
	}

	public boolean getTicketsAccess() {
		return ticketsAccess;
	}

	public void setTicketsAccess(boolean ticketsAccess) {
		this.ticketsAccess = ticketsAccess;
	}

	public boolean getFilesAccess() {
		return filesAccess;
	}

	public void setFilesAccess(boolean filesAccess) {
		this.filesAccess = filesAccess;
	}

	public boolean getCategoriesAccess() {
		return categoriesAccess;
	}

	public void setCategoriesAccess(boolean categoriesAccess) {
		this.categoriesAccess = categoriesAccess;
	}
	
}