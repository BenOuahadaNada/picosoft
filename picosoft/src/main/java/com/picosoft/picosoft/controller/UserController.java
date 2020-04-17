package com.picosoft.picosoft.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.picosoft.picosoft.dao.DepartementRepository;
import com.picosoft.picosoft.dao.UserRepository;
import com.picosoft.picosoft.module.Departement;
import com.picosoft.picosoft.module.User;

@Transactional
@RestController
@RequestMapping(value="api/user")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DepartementRepository deptRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcryptpasswordEncoder;
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/id/{id}")
	public User getUserById(@PathVariable(value="id") Long Id){
		return userRepository.findById(Id).get(); 
	}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/searchUser/{prenom}")
	public ResponseEntity<List<User>> SearchUser(@RequestParam(required = false)String prenom){
		try {
			List<User> user= userRepository.findByPrenom(prenom);
			if(user.isEmpty()) {
				return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
			}
		    return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception e){
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/all")
	public List<User> getAllUser(){
			return userRepository.findAll();
		}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@GetMapping(value="/allDepartement")
	public List<Departement> getAllDepartement(){
			return deptRepository.findAll();
		}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PostMapping(value="/ajouterUser")
	public User AjouterUser(@Valid @RequestBody User user) {
		/*System.out.println(user.getNom());
		System.out.println(user.getPrenom());
		if(user.getDepartement() != null )
		System.out.println(user.getDepartement().getDeptName());
		System.out.println(user.getEmail());
		if(user.getRole() != null)
		System.out.println(user.getRole().getRole());
		System.out.println(user.getGender());
		System.out.println(user.getPassword());
		if(user.getPolitique() != null)
		System.out.println(user.getPolitique().getNom());*/
		user.setPassword(bcryptpasswordEncoder.encode("12345678"));
		return userRepository.save(user);
	}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@PutMapping(value="/updateUser/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userUpdated) throws Exception {
		User user= userRepository.findById(id).orElseThrow(()->new Exception("user not found"));
		user.setNom(userUpdated.getNom());
		user.setPrenom(userUpdated.getPrenom());
		user.setEmail(userUpdated.getEmail());
		user.setGender(userUpdated.getGender());
		user.setPassword(userUpdated.getPassword());
		user.setDepartement(userUpdated.getDepartement());
		user.setPolitique(userUpdated.getPolitique());
		user.setRole(userUpdated.getRole());
		User userModified=userRepository.save(user);
		return ResponseEntity.ok(userModified);
	}
	
	@PreAuthorize("hasAuthority('responsable_rh')")
	@DeleteMapping(value="/deleteUser/{id}")
	public Map<String,Boolean> deleteUser(@PathVariable Long id) throws Exception{
		User user=userRepository.findById(id).orElseThrow(()->new Exception("user not found"));
		userRepository.delete(user);
		Map<String,Boolean> response= new HashMap<>();
		response.put("Deleted successfully", Boolean.TRUE);
		return response;
	}
}
