package com.panimalar.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.panimalar.app.model.Admin;
import com.panimalar.app.model.Holder;
import com.panimalar.app.model.Manager;
import com.panimalar.app.repository.AccountRepository;
import com.panimalar.app.repository.AdminRepository;
import com.panimalar.app.repository.HolderRepository;
import com.panimalar.app.repository.ManagerRepository;

@Service
public class BankDetailService implements UserDetailsService{

	@Autowired
	ManagerRepository managerRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	HolderRepository holderRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		UserBuilder builder = null;
		Optional<Holder> holder = null;
		Optional<Manager> manager = null;
		Optional<Admin> admin = null;
		if(username.matches("\\d+")) {
			if(accountRepository.findByAccountNumber(Long.parseLong(username)).isPresent()) {
			    holder = holderRepository.findByHolderAccount(accountRepository.findByAccountNumber(Long.parseLong(username)).get());
			    if(holder.isPresent()) {
			    	builder = User.withUsername(username).password(holder.get().getAuthorization().getAuthorizationPass());
			    	String[] authorities = new String[1];
			    	authorities[0] = holder.get().getAuthorization().getAuthorizedRole().getRoleName();
			    	builder.authorities(authorities);
			    }
			} else {
				throw new UsernameNotFoundException("User Not Found");
			}
		} else {
			manager = managerRepository.findByEmail(username);
			admin = adminRepository.findByEmail(username);
			if(manager.isPresent()) {
			    builder = User.withUsername(username).password(manager.get().getAuthorization().getAuthorizationPass());
			    String[] authorities = new String[1];
	    	    authorities[0] = manager.get().getAuthorization().getAuthorizedRole().getRoleName();
	    	    builder.authorities(authorities);
			} else if(admin.isPresent()) {	
			    builder = User.withUsername(username).password(admin.get().getAuthorization().getAuthorizationPass());
			    String[] authorities = new String[1];
	    	    authorities[0] = admin.get().getAuthorization().getAuthorizedRole().getRoleName();
	    	    builder.authorities(authorities);
			} else {	
				throw new UsernameNotFoundException("User Not Found");
			}
		} 
		return builder.build();
	}

}
