package com.scaler.cloneSplitWise;

import com.scaler.cloneSplitWise.models.ConsolidatedExpenseData;
import com.scaler.cloneSplitWise.services.ExpenseService;
import com.scaler.cloneSplitWise.services.GroupService;
import com.scaler.cloneSplitWise.services.ReportService;
import com.scaler.cloneSplitWise.services.UserService;

public class Application {
	private UserService userService; 
	private GroupService groupService;
	private ExpenseService expenseService; 
	private ReportService reportService;
	private ConsolidatedExpenseData data;
	
	public Application() {
		userService = new UserService();
		groupService = new GroupService();
		
		data = new ConsolidatedExpenseData();
		reportService = new ReportService(data, userService);
		expenseService = new ExpenseService(userService, groupService, data);
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
	
	public ReportService getReportService() {
		return reportService;
	}
}
