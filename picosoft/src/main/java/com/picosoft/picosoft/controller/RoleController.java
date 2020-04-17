package com.picosoft.picosoft.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picosoft.picosoft.dao.RoleRepository;
import com.picosoft.picosoft.module.Role;

@RestController
@Transactional
@RequestMapping(value = "api/user")
public class RoleController {
	@Autowired
	RoleRepository roleRepository;
	
	@PreAuthorize("hasAnyAuthority('admin','responsable_rh')")
	@GetMapping(value="/allRole")
    public List<Role> getAllRole(){
        return roleRepository.findAll();
    }
	
	@PreAuthorize("hasAnyAuthority('admin','responsable_rh')")
	@GetMapping(value="/role/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) throws Exception{
        Role role = roleRepository.findById(id).orElseThrow(()->new Exception("Le role n'existe pas"));
        return ResponseEntity.ok().body(role);
    }
	
	@PreAuthorize("hasAnyAuthority('admin','responsable_rh')")
	@PostMapping(value="/ajouterRole")
    public Role AjouterRole(@Valid @RequestBody Role role){
        return roleRepository.save(role);
    }
	
	@PreAuthorize("hasAuthority('admin')")
	@PutMapping(value="/updateRole/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @Valid @RequestBody Role roleDetails) throws Exception{
        Role role = roleRepository.findById(id).orElseThrow(()->new Exception("Le role n'existe pas"));
        role.setRole(roleDetails.getRole());
        roleRepository.save(role);
        return ResponseEntity.ok(role);
    }
	
	@PreAuthorize("hasAnyAuthority('admin','responsable_rh')")
	@DeleteMapping(value="/deleteRole/{id}")
    public Map<String,Boolean> deleteRole(@PathVariable Long id) throws Exception{
        Role role = roleRepository.findById(id).orElseThrow(()->new Exception("Le role n'existe pas"));
        roleRepository.delete(role);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Le role est supprimé!",Boolean.TRUE);
        return response;
    }

}
