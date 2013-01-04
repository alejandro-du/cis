package cis.db.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import enterpriseapp.hibernate.annotation.CrudTable;
import enterpriseapp.hibernate.annotation.Downloadable;
import enterpriseapp.hibernate.dto.Dto;


/**
 * The persistent class for the project database table.
 * 
 */
@Entity
@Table(name="project")
@CrudTable(filteringPropertyName="name")
public class Project extends Dto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;
	
	@Column(nullable=false)
	private Date start;
	
	@Column
	private Date end;
	
	@Column(nullable=false)
	private boolean showTickets;
	
	@Column(nullable=false)
	private boolean showSuggestions;
	
	@Column(nullable=false)
	private boolean sendNewFileNotificationsToCustomers;
	
	@Column(nullable=false)
	private boolean sendNewTicketNotificationsToCis;
	
	@Column
	private BigDecimal cost;
	
	@Column
	private Integer columns;
	
	@Column
	@Lob
	@Downloadable(propertyFileName = "name", fileNameSufix=".png")
	private byte[] icon;

	@Column(length=1024)
	private String comments;
	
	@Column(nullable = false)
	private boolean locked;

	//bi-directional many-to-one association to File
	@OneToMany(mappedBy="project")
	private Set<File> files;

	//bi-directional many-to-one association to Ticket
	@OneToMany(mappedBy="project")
	private Set<Ticket> tickets;

	//bi-directional many-to-many association to User
    @ManyToMany
	@JoinTable(
		name="user_has_project"
		, joinColumns={
			@JoinColumn(name="project")
		}
		, inverseJoinColumns={
			@JoinColumn(name="customer")
		}
	)
	private Set<User> users;

	//bi-directional many-to-many association to Category
    @ManyToMany
	@JoinTable(
		name="category_has_project"
		, joinColumns={
			@JoinColumn(name="project")
			}
		, inverseJoinColumns={
			@JoinColumn(name="category")
			}
		)
	@OrderBy("ordering")
	private Set<Category> categories;

    public Project() {
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

	public Set<File> getFiles() {
		return this.files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}
	
	public Set<Ticket> getTickets() {
		return this.tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public boolean getShowSuggestions() {
		return showSuggestions;
	}

	public void setShowSuggestions(boolean showSuggestions) {
		this.showSuggestions = showSuggestions;
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public boolean getShowTickets() {
		return showTickets;
	}

	public void setShowTickets(boolean showTickets) {
		this.showTickets = showTickets;
	}

	public boolean getSendNewFileNotificationsToCustomers() {
		return sendNewFileNotificationsToCustomers;
	}

	public void setSendNewFileNotificationsToCustomers(boolean sendNewFileNotificationsToCustomers) {
		this.sendNewFileNotificationsToCustomers = sendNewFileNotificationsToCustomers;
	}

	public boolean getSendNewTicketNotificationsToCis() {
		return sendNewTicketNotificationsToCis;
	}

	public void setSendNewTicketNotificationsToCis(boolean sendNewTicketNotificationsToCis) {
		this.sendNewTicketNotificationsToCis = sendNewTicketNotificationsToCis;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean getLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

}