package com.panimalar.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.panimalar.app.model.Account;
import com.panimalar.app.model.Admin;
import com.panimalar.app.model.Branch;
import com.panimalar.app.model.Holder;
import com.panimalar.app.model.JWTRequest;
import com.panimalar.app.model.JWTResponse;
import com.panimalar.app.model.Manager;
import com.panimalar.app.model.MoneyTransaction;
import com.panimalar.app.repository.*;
import com.panimalar.app.service.BankDetailService;
import com.panimalar.app.service.NotificationMailService;
import com.panimalar.app.service.OtpService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BankController {
	
	
	private Logger logger = Logger.getLogger(BankController.class.getName());
	
	@Autowired 
	AdminRepository adminRepository;
	
	@Autowired
	ManagerRepository managerRepository;
	
	@Autowired
	HolderRepository holderRepository;
	
	@Autowired 
	BranchRepository branchRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AuthorizationRepository authorizationRepository;
	
	@Autowired
	MoneyTransactionRepository transactionRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired 
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private com.panimalar.app.config.JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private BankDetailService userDetailsService;

	@Autowired
	private OtpService otpService;
	
	
	
	//Authenticate URL
	
	@PostMapping("/authenticate")
	public ResponseEntity<JWTResponse> gerateAuthenticationToken(@RequestBody JWTRequest authenticationRequest)  {
		authenticate(authenticationRequest.getUsername(),authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		GrantedAuthority authority =  (GrantedAuthority) userDetails.getAuthorities().toArray()[0];
		return ResponseEntity.ok(new JWTResponse(token,authority.getAuthority()));
	}
	
	private void authenticate(String username,String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
		} catch(DisabledException e) {
			throw new DisabledException("User Disabled", e);
		} catch(BadCredentialsException e) {
			throw new BadCredentialsException("Invalid credentials",e);
		}
	}

	@GetMapping("/get")
	public String getDemo() {
		return "Hello World";
	}
	
	//Admin URLs
	
	@PostMapping("/addAdmin")
	public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
		if(admin == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		admin.getAuthorization().setAuthorizedRole(roleRepository.findById(1l).get());
		String secret = admin.getAuthorization().getAuthorizationPass();
		admin.getAuthorization().setAuthorizationPass(bCryptPasswordEncoder.encode(secret));
		authorizationRepository.save(admin.getAuthorization());
		adminRepository.save(admin);
		return ResponseEntity.ok(admin);
	}
	
	
	@PostMapping("/api/admin/addManager")
	public ResponseEntity<Manager> addManager(@RequestBody Manager manager) {
		if(manager == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		manager.getAuthorization().setAuthorizedRole(roleRepository.findById(2l).get());
		String secret = manager.getAuthorization().getAuthorizationPass();
		manager.getAuthorization().setAuthorizationPass(bCryptPasswordEncoder.encode(secret));
		authorizationRepository.save(manager.getAuthorization());
		managerRepository.save(manager);
		return ResponseEntity.ok(manager);
	}
	
	@PutMapping("/api/admin/updateManager")
	public ResponseEntity<Manager> updateManager(@RequestBody Manager manager) {
		if(manager == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		manager.getAuthorization().setAuthorizedRole(roleRepository.findById(2l).get());
		managerRepository.save(manager);
		return ResponseEntity.ok(manager);
	}
	
	@PostMapping("/api/admin/addBranch")
	public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {
		branchRepository.save(branch);
		return ResponseEntity.ok(branch);
	}
	
	@PostMapping("/api/admin/sampleBranch")
	public ResponseEntity<Branch> sampleBranch(@RequestBody Branch branch) {
		branchRepository.save(branch);
		return ResponseEntity.ok(branch);
	}
	
	
	@GetMapping("/api/admin/getAllManager")
	public ResponseEntity<List<Manager>> getAllManagers() {
		return ResponseEntity.ok(managerRepository.findAll());
	}
	
	
	//User URLs
	
	@PostMapping("/api/createUser")
	public ResponseEntity<Holder> createHolder(@RequestBody Holder holder) {
		if(holder == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		holder.getAuthorization().setAuthorizedRole(roleRepository.findById(3l).get());
		String secret = holder.getAuthorization().getAuthorizationPass();
		holder.getAuthorization().setAuthorizationPass(bCryptPasswordEncoder.encode(secret));
		holder.getHolderAccount().setAccountNumber(holder.getPhone());
		holder.getHolderAccount().setActive(false);
	    holder.getHolderAccount().setVerified(true);
	    otpService.generateOtp(holder);
	    accountRepository.save(holder.getHolderAccount());
		authorizationRepository.save(holder.getAuthorization());
		holderRepository.save(holder);
		return ResponseEntity.ok(holder);
	}
	
	
	@PostMapping("/api/validateOtp/{email}")
	public ResponseEntity<HttpStatus> validateOtp(@RequestBody HashMap<String,Integer> request,@PathVariable String email) {
		Holder holder = holderRepository.findByEmail(email);
		if(otpService.validateOTP(email, request.get(email))) {
			holder.getHolderAccount().setVerified(true);
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
		    return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/api/getAllBranches")
	public ResponseEntity<List<Branch>> getAllBranches() {
		return ResponseEntity.ok(branchRepository.findAll());
	}
	
	
	
	// Manager URL's
	
	@GetMapping("/api/manager/getActiveAccounts") 
	public ResponseEntity<List<Account>> getActiveAccounts(Authentication authentication){
		String username = authentication.getName();
		Manager manager = managerRepository.findByEmail(username).get();
		Branch branch = branchRepository.findByBranchManager(managerRepository.findById(manager.getId()).get());
		return ResponseEntity.ok(accountRepository.findByActive(branch.getBranchId()));
	}
	
	@GetMapping("/api/manager/getNonActiveAccounts") 
	public ResponseEntity<List<Account>> getNonActiveAccounts(Authentication authentication){
		String username = authentication.getName();
		Manager manager = managerRepository.findByEmail(username).get();
		Branch branch = branchRepository.findByBranchManager(managerRepository.findById(manager.getId()).get());
		return ResponseEntity.ok(accountRepository.findByNonActive(branch.getBranchId()));
	}
	
	@GetMapping("/api/manager/getHolder/{accountId}")
	public ResponseEntity<Holder> viewHolder(@PathVariable Long accountId) {
        return ResponseEntity.ok(holderRepository.findByHolderAccount(accountRepository.findById(accountId).get()).get());  		
	}
	
	@PostMapping("/api/manager/setActive/{accountId}")
	public ResponseEntity<HttpStatus> setActive(@PathVariable Long accountId) {
		Account account = accountRepository.findById(accountId).get();
		account.setActive(true);
		accountRepository.save(account);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	
	//Holder URL's
	
	@PostMapping("/api/holder/transfer")
	public ResponseEntity<HttpStatus> transferMoney(@RequestBody MoneyTransaction transaction) {
		Account account = accountRepository.findByAccountNumber(transaction.getFromAccount()).get();
		Optional<Account> toAccount  = accountRepository.findByAccountNumber(transaction.getToAccount());
		if(account.getBalance()<=transaction.getTransactionAmount()) {
			logger.info("Low Balance"  + transaction.getFromAccount());
			return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
		} else if(!toAccount.isPresent()){
			    logger.info("Reciver Not Available" + transaction.getToAccount());
		    	return ResponseEntity.ok(HttpStatus.NOT_FOUND);
		}
		Long previousBalance = toAccount.get().getBalance();
		Long fromBalance = account.getBalance();
		toAccount.get().setBalance(previousBalance + transaction.getTransactionAmount());
		account.setBalance(fromBalance-transaction.getTransactionAmount());
		accountRepository.save(toAccount.get());
		accountRepository.save(account);
		transactionRepository.save(transaction);
		return ResponseEntity.ok(HttpStatus.OK);
	}
	
	@GetMapping("/api/holder/{accountNumber}")
	public ResponseEntity<List<MoneyTransaction>> getAllTransaction(@PathVariable Long accountNumber) {
		return ResponseEntity.ok(transactionRepository.findAllTransaction(accountNumber));
	}
	
	@GetMapping("/api/holder/getAccount/{accountNumber}")
	public ResponseEntity<Account> getAccountDetails(@PathVariable Long accountNumber) {
		return ResponseEntity.ok(accountRepository.findByAccountNumber(accountNumber).get());
	}
	
	@PostMapping("/api/holder/deposit/{amount}/{accountNumber}")
	public ResponseEntity<HttpStatus> depositAmount(@PathVariable Long amount,@PathVariable Long accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber).get();
		Long balance = account.getBalance();
		account.setBalance(balance + amount);
		accountRepository.save(account);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
