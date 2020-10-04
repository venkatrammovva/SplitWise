package com.scaler.cloneSplitWise;

import com.scaler.cloneSplitWise.services.ExpenseService;
import com.scaler.cloneSplitWise.services.GroupService;
import com.scaler.cloneSplitWise.services.UserService;

public class Application {
	private UserService userService; 
	private GroupService groupService;
	private ExpenseService expenseService; 
	
	public Application() {
		userService = new UserService();
		groupService = new GroupService();
		expenseService = new ExpenseService(userService, groupService);
	}

	public UserService getUserService() {
		return userService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public ExpenseService getExpenseService() {
		return expenseService;
	}
}
