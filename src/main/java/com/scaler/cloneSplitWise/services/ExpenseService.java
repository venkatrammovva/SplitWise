package com.scaler.cloneSplitWise.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.scaler.cloneSplitWise.models.Expense;
import com.scaler.cloneSplitWise.models.PaySplit;

public class ExpenseService {
	List<Expense> expenses;
	Map<Integer, Map<Integer, Double>> creditMap;
	Map<Integer, Map<Integer, Double>> debitMap;
	UserService userService;
	GroupService groupService;
	private int idsUsed = 0;

	public ExpenseService(UserService userService, GroupService groupService) {
		this.userService = userService;
		this.groupService = groupService;
		creditMap = new HashMap<>();
		debitMap = new HashMap<>();
		expenses = new ArrayList<>();
	}

	public void addExpense(Set<PaySplit> lenders, Set<Integer> debitors, String expnseName) {
		double totalAmount = 0;
		for (PaySplit eachPaySplit : lenders) {
			totalAmount += eachPaySplit.getAmount();
		}
		double costPerPerson = totalAmount / debitors.size();
		Comparator<PaySplit> comp = (a, b) -> new Double(b.getAmount() - a.getAmount()).intValue();
		PriorityQueue<PaySplit> queue = new PriorityQueue<>(comp);
		HashMap<Integer, Double> toPayAmount = new HashMap<>();
		for (PaySplit eachPaySplit : lenders) {
			if (debitors.contains(eachPaySplit.getUserId()) && eachPaySplit.getAmount() < costPerPerson) {
				double amountRemaining = costPerPerson - eachPaySplit.getAmount();
				toPayAmount.put(eachPaySplit.getUserId(), amountRemaining);
			} else if (debitors.contains(eachPaySplit.getUserId())) {
				eachPaySplit.setAmount(eachPaySplit.getAmount() - costPerPerson);
				queue.add(eachPaySplit);
				debitors.remove(eachPaySplit.getUserId());
			} else {
				queue.add(eachPaySplit);
			}
		}
		updateBalances(debitors, costPerPerson, queue, toPayAmount);
		Expense exp = new Expense(++idsUsed, expnseName, lenders, debitors);
		expenses.add(exp);
	}

	private void updateBalances(Set<Integer> debitors, double costPerPerson, PriorityQueue<PaySplit> queue,
			HashMap<Integer, Double> toPayAmount) {
		for (Integer eachDebitor : debitors) {
			double borrowedAmount = toPayAmount.containsKey(eachDebitor) ? toPayAmount.get(eachDebitor) : costPerPerson;
			if (!debitMap.containsKey(eachDebitor)) {
				debitMap.put(eachDebitor, new HashMap<>());
			}
			Map<Integer, Double> debitor = debitMap.get(eachDebitor);
			while (borrowedAmount > 0) {
				PaySplit lender = queue.remove();
				int lenderId = lender.getUserId();
				double amountLent = lender.getAmount();
				if (!creditMap.containsKey(lenderId)) {
					creditMap.put(lenderId, new HashMap<>());
				}

				Map<Integer, Double> creditor = creditMap.get(lenderId);
				double amountToBeProcesed = Math.min(borrowedAmount, amountLent);
				creditor.put(eachDebitor, creditor.getOrDefault(eachDebitor, 0.0) + amountToBeProcesed);
				debitor.put(lenderId, debitor.getOrDefault(lenderId, 0.0) + amountToBeProcesed);
				if (borrowedAmount < amountLent) {
					lender.setAmount(lender.getAmount() - borrowedAmount);
					queue.add(lender);
				}
				borrowedAmount -= amountToBeProcesed;
			}
		}
	}

	public void addExpense(Set<PaySplit> lenders, int groupId, String expnseName) throws Exception {
		for (PaySplit eachLender : lenders) {
			if (!groupService.isMemberOfGroup(groupId, eachLender.getUserId())) {
				throw new Exception("UserId " + eachLender.getUserId() + " is not part of the group");
			}

		}
		Set<Integer> debitors = new HashSet<>();
		debitors.addAll(groupService.findGroup(groupId).getMembers());
		addExpense(lenders, debitors, expnseName);
	}

	public void printReport(int userId) throws Exception {
		if (creditMap.containsKey(userId)) {
			Map<Integer, Double> debitors = creditMap.get(userId);
			String lenderName = userService.findUser(userId).getUserName();
			for (Integer eachBorrower : debitors.keySet()) {
				String borrowerName = userService.findUser(eachBorrower).getUserName();
				System.out.println(borrowerName + " owes " + lenderName + " Rs." + debitors.get(eachBorrower));
			}
		}
		if (debitMap.containsKey(userId)) {
			Map<Integer, Double> debitors = debitMap.get(userId);
			String borrowerName = userService.findUser(userId).getUserName();
			for (Integer eachLender : debitors.keySet()) {
				String lenderName = userService.findUser(eachLender).getUserName();
				System.out.println(borrowerName + " owes " + lenderName + " Rs." + debitors.get(eachLender));
			}
		}
	}

	public void printOverallReport() throws Exception {
		for (Integer eachUser : userService.getAllUserIds()) {
			printReport(eachUser);
			System.out.println("#######################################################");
		}
	}
}
