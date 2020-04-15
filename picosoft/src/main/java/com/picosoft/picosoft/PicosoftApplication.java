package com.picosoft.picosoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.picosoft.picosoft.dao.RoleRepository;
import com.picosoft.picosoft.dao.UserRepository;
import com.picosoft.picosoft.module.Role;
import com.picosoft.picosoft.module.User;
import com.picosoft.picosoft.service.UserService;

@SpringBootApplication
public class PicosoftApplication  implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(PicosoftApplication.class, args);
	}
	/*Autowired
	private UserService userService;
	@Autowired
	private RoleRepository roles;
*/
	@Override
	public void run(String... args) throws Exception {
		    //  Role role = new Role(null , "admin", null);
		    //  this.roles.save(role);
			//  this.userService.addUser(new User(null, "nouha", "ben ouahada" , "femme","nada@gmail.com", null, null, null , role ));
				
	}
}
