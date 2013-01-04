package cis.db.container;

import java.util.ArrayList;
import java.util.List;

import cis.db.dto.Project;
import cis.db.dto.Ticket;
import cis.db.dto.User;
import enterpriseapp.hibernate.DefaultHbnContainer;


@SuppressWarnings("unchecked")
public class TicketContainer extends DefaultHbnContainer<Ticket> {

	private static final long serialVersionUID = 1L;

	public TicketContainer() {
		super(Ticket.class);
	}
	
	public List<Ticket> listClosedByUser(User user) {
		ArrayList<Ticket> list = new ArrayList<Ticket>();
		
		for(Project p : user.getProjects()) {
			list.addAll(query("from Ticket where project = ? and closed is true order by date desc", new Object[] {p}));
		}
		
		return list;
	}

}
