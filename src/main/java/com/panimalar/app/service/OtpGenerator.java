package com.panimalar.app.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;


@Service
public class OtpGenerator {
    private static final Integer EXPIRE_TIME = 5;
    private LoadingCache<String, Integer> otpCache;
    
    public OtpGenerator() {
    	super();
    	otpCache = CacheBuilder.newBuilder()
    			.expireAfterWrite(EXPIRE_TIME, TimeUnit.MINUTES)
    			.build(new CacheLoader<String, Integer>() {
    				@Override
    				public Integer load(String s) throws Exception {
    					return 0;
    				}
    			});
    }
    
    public Integer generateOTP(String key) {
    	Random random = new Random();
    	int OTP = 100000 + random.nextInt(900000);
    	otpCache.put(key, OTP);
    	return OTP;
    }
    
    public Integer getOTPByKey(String key) {
    	try {
    		return otpCache.get(key);
    	} catch(ExecutionException e) {
    	    return 	-1;
    	} 
    }
    
    public void clearOTPCache(String key) {
    	otpCache.invalidate(key);
    }
    
    public Boolean isGenerated(String key) {
    	Integer otp = otpCache.getIfPresent(key);
    	if(otp==null) {
    		return false;
    	} 
    	return true;
    }
}
