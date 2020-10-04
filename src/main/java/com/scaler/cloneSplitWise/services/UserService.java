package com.scaler.cloneSplitWise.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.scaler.cloneSplitWise.models.User;

public class UserService {
	private Map<Integer, User> userRepo = new HashMap<>();
	private int idsUsed = 0;

	public Integer addUser(String userName) {
		User user = new User(++idsUsed, userName);
		userRepo.put(user.getId(), user);
		return user.getId();
	}

	public User findUser(int id) throws Exception {
		if (userRepo.containsKey(id)) {
			return userRepo.get(id);
		}
		throw new Exception("User not found");
	}
	
	public Set<Integer> getAllUserIds() {
		return userRepo.keySet();
	}
}
