package com.picosoft.picosoft.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.picosoft.picosoft.dao.UserRepository;
import com.picosoft.picosoft.module.User;
@Service
public class UserService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository; 
	
	/*@Autowired
	private BCryptPasswordEncoder bcryptpasswordEncoder;*/

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user=this.userRepository.findByEmail(username);
		if(user == null) throw new UsernameNotFoundException(username);
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority(user.getRole().getRole()));
			return new  org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
						
		
	}
	/*public User addUser(User user)
	{
		user.setPassword(bcryptpasswordEncoder.encode("12345678"));
		return userRepository.save(user);
	}*/
}
