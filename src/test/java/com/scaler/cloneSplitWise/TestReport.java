package com.scaler.cloneSplitWise;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.scaler.cloneSplitWise.models.PaySplit;

public class TestReport {
	
	@Test
	public void basicTest() throws Exception {
		Application app = new Application();
		app.getUserService().addUser("venkat");
		app.getUserService().addUser("bhav");
		app.getUserService().addUser("kiriti");
		app.getUserService().addUser("karthik");
		Set<Integer> borrowers = new HashSet<>();
		borrowers.add(2);
		borrowers.add(1);
		borrowers.add(3);
		borrowers.add(4);
		
		PaySplit split1 = new PaySplit(1, 60);
		PaySplit split2 = new PaySplit(3, 40);
		Set<PaySplit> splits= new HashSet<>();
		splits.add(split1);
		splits.add(split2);
		
		app.getExpenseService().addExpense(splits, borrowers, "Restaurant bill");
		app.getExpenseService().printReport(1);
		System.out.println("#######################################################");
		app.getExpenseService().printReport(3);
		
	}
	
	@Test
	public void basicTest2() throws Exception {
		Application app = new Application();
		app.getUserService().addUser("venkat");
		app.getUserService().addUser("bhav");
		app.getUserService().addUser("abc");
		
		Set<Integer> borrowers = new HashSet<>();
		borrowers.add(2);
		borrowers.add(1);
		
		PaySplit split1 = new PaySplit(1, 100);
		PaySplit split2 = new PaySplit(3, 20);
		Set<PaySplit> splits= new HashSet<>();
		splits.add(split1);
		splits.add(split2);
		
		app.getExpenseService().addExpense(splits, borrowers, "Restaurant bill");
		app.getExpenseService().printReport(1);
		System.out.println("#######################################################");
		app.getExpenseService().printReport(2);
		System.out.println("#######################################################");
		app.getExpenseService().printReport(3);
		
	}
	
	@Test
	public void basicTest3() throws Exception {
		Application app = new Application();
		app.getUserService().addUser("venkat");
		app.getUserService().addUser("bhav");
		app.getUserService().addUser("abc");
		
		Set<Integer> borrowers = new HashSet<>();
		borrowers.add(2);
		borrowers.add(1);
		borrowers.add(3);
		
		PaySplit split1 = new PaySplit(1, 100);
		PaySplit split2 = new PaySplit(3, 20);
		Set<PaySplit> splits= new HashSet<>();
		splits.add(split1);
		splits.add(split2);
		
		app.getExpenseService().addExpense(splits, borrowers, "Restaurant bill");
		app.getExpenseService().printReport(1);
		System.out.println("#######################################################");
		app.getExpenseService().printReport(2);
		System.out.println("#######################################################");
		app.getExpenseService().printReport(3);
		
	}
	@Test
	public void groupTest() throws Exception {
		Application app = new Application();
		app.getUserService().addUser("venkat");
		app.getUserService().addUser("bhav");
		app.getUserService().addUser("abc");
		
		Set<Integer> groupMembers = new HashSet<>();
		groupMembers.add(1);
		groupMembers.add(2);
		groupMembers.add(3);
		
		app.getGroupService().addGroup("dummy", groupMembers);
		
		PaySplit split1 = new PaySplit(1, 100);
		PaySplit split2 = new PaySplit(3, 20);
		Set<PaySplit> splits= new HashSet<>();
		splits.add(split1);
		splits.add(split2);
		
		app.getExpenseService().addExpense(splits, 1, "Restaurant bill");
		app.getExpenseService().printReport(1);
		System.out.println("#######################################################");
		app.getExpenseService().printReport(2);
		System.out.println("#######################################################");
		app.getExpenseService().printReport(3);
		
	}
	
	@Test
	public void testOverallReport() throws Exception {
		Application app = new Application();
		app.getUserService().addUser("venkat");
		app.getUserService().addUser("bhav");
		app.getUserService().addUser("abc");
		
		Set<Integer> groupMembers = new HashSet<>();
		groupMembers.add(1);
		groupMembers.add(2);
		groupMembers.add(3);
		
		app.getGroupService().addGroup("dummy", groupMembers);
		
		PaySplit split1 = new PaySplit(1, 100);
		PaySplit split2 = new PaySplit(3, 20);
		Set<PaySplit> splits= new HashSet<>();
		splits.add(split1);
		splits.add(split2);
		
		app.getExpenseService().addExpense(splits, 1, "Restaurant bill");
		PaySplit split1_1 = new PaySplit(1, 100);
		PaySplit split2_1 = new PaySplit(3, 20);
		Set<PaySplit> splits1 = new HashSet<>();
		splits1.add(split2_1);
		splits1.add(split1_1);
		app.getExpenseService().addExpense(splits1, 1, "Restaurant bill next week");
		app.getExpenseService().printOverallReport();
		
	}
}
