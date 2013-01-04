package cis.db.container;

import java.io.Serializable;

import cis.db.dto.User;
import enterpriseapp.Utils;
import enterpriseapp.hibernate.DefaultHbnContainer;

@SuppressWarnings("unchecked")
public class UserContainer extends DefaultHbnContainer<User> {

	private static final long serialVersionUID = 1L;

	public UserContainer() {
		super(User.class);
	}
	
	@Override
	public Serializable saveOrUpdateEntity(User user) {
		
		if(user.getId() == null) {
			user.setPassword(Utils.getMD5Hash(user.getPassword()));
			
		} else {
			String previousPassword = getPasswordByUserId(user.getId());
			
			if(!previousPassword.equals(user.getPassword())) {
				user.setPassword(Utils.getMD5Hash(user.getPassword()));
			}
		}
		
		return super.saveOrUpdateEntity(user);
	}
	
	@Override
	public Serializable saveEntity(User user) {
		user.setPassword(Utils.getMD5Hash(user.getPassword()));
		
		return super.saveOrUpdateEntity(user);
	}
	
	public String getPasswordByUserId(long userId) {
		return (String) singleSpecialQuery("select password from User where id = ?", new Object[] {userId});
	}
	
	public User getByLoginAndPassword(String login, String password) {
		password = Utils.getMD5Hash(password);
		return singleQuery("from User where login = ? and password = ?", new Object[] {login, password});
	}
	
	public User getByLogin(String login) {
		return singleQuery("from User where login = ?", new Object[] {login});
	}

}
