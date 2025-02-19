package cp.spring.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
	
	Map<String,List<String>> usernamePasswordRole=new HashMap<>();
	
	UserServiceImpl(){
		usernamePasswordRole.put("jack",List.of("jill","ADMIN"));
		usernamePasswordRole.put("nagen",List.of("test","CUSTOMER"));
		usernamePasswordRole.put("guest",List.of("kill","GUEST"));
	}
	
	public List<String> findByUsername(String username){
		return usernamePasswordRole.get(username);
	}

}
