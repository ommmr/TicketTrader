package io.pp1.users;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserRepository userRepository;
	
	private List<User> user;
	
	public UserService(List<User> user) {
		this.user = user;
	}
	
	
	public List<User> getUsers(){
		return user;
	}
	
	public boolean userExist(Integer id){
		
		return userRepository.existsById(id);
	}

	public void post(User user) {		
		userRepository.save(user);
	}
	
	public boolean userLogin(User user) {
		
		String temp = userRepository.getPassByNetID(user.getNet_Id());
		
		if(user.getPassword().equals(temp))
			return true;
		
		return false;
	}
}
