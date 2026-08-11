package com.transaction;

import java.sql.*;
import java.util.Scanner;

public class Employee {

     static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employee_db",
                    "root",
                    "Shipra@218"
            );
            System.out.println("Database Connected Successfully!");

            int choice;

            do {
                System.out.print("\n**** Employee & Bank ****");
                System.out.print("\n1. Add Employee");
                System.out.print("\n2. View Employee");
                System.out.print("\n3. Search Employee");
                System.out.print("\n4. Update Employee");
                System.out.print("\n5. Delete Employee");
                System.out.print("\n6. Bank Services");
                System.out.print("\n7. Batch Processing");
                System.out.print("\n8. Exit");

                System.out.print("Enter Choice: ");
                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        try {
                            con.setAutoCommit(false);

                            System.out.print("Enter Employee ID: ");
                            int id = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Enter Employee Name: ");
                            String name = sc.nextLine();

                            System.out.print("Enter Department: ");
                            String department = sc.nextLine();

                            System.out.print("Enter Salary: ");
                            double salary = sc.nextDouble();
                            sc.nextLine();

                            System.out.print("Enter Contact Number: ");
                            String contact = sc.nextLine();

                            System.out.print("Enter Email: ");
                            String email = sc.nextLine();

                            System.out.print("\nSelect Account Type");
                            System.out.print("\n1. Savings");
                            System.out.print("\n2. Current");
                            System.out.print("Enter Choice: ");

                            int accountChoice = sc.nextInt();
                            String accountType;

                            if (accountChoice == 1) {
                                accountType = "Savings";
                            } else if (accountChoice == 2) {
                                accountType = "Current";
                            } else {
                                System.out.print("\nInvalid Account Type!");
                                con.rollback();
                                break;
                            }

                            String insertQuery =
                                    "INSERT INTO employee_db.employee" +
                                            "(emp_id, emp_name, department, salary, contact, email) " +
                                            "VALUES (?, ?, ?, ?, ?, ?)";

                            PreparedStatement ps = con.prepareStatement(insertQuery);
                            ps.setInt(1, id);
                            ps.setString(2, name);
                            ps.setString(3, department);
                            ps.setDouble(4, salary);
                            ps.setString(5, contact);
                            ps.setString(6, email);

                            int employeeRows = ps.executeUpdate();
                            String accountNo = "ACC" + id;
                            String bankQuery =
                                    "INSERT INTO employee_db.bank_account" +
                                            "(account_no, emp_id, balance, account_type) " +
                                            "VALUES (?, ?, ?, ?)";

                            PreparedStatement bankPs = con.prepareStatement(bankQuery);
                            bankPs.setString(1, accountNo);
                            bankPs.setInt(2, id);
                            bankPs.setDouble(3, 0.0);
                            bankPs.setString(4, accountType);

                            int bankRows = bankPs.executeUpdate();

                            if (employeeRows > 0 && bankRows > 0) {
                                con.commit();

                                System.out.print("\nEmployee Added Successfully!");
                                System.out.print("\nBank Account Created Successfully!");

                                System.out.print("\nAccount Number  : " + accountNo);
                                System.out.print("\nAccount Type    : " + accountType);
                                System.out.print("\nOpening Balance : Rs. 0");

                                System.out.print("\nEmployee Rows Affected : " + employeeRows);
                                System.out.print("\nBank Rows Affected     : " + bankRows);

                                System.out.println("Transaction Committed!");

                            } else {
                                con.rollback();

                                System.out.print("\nEmployee/Bank Account Creation Failed!");
                                System.out.println("Transaction Rolled Back!");
                            }

                        } catch (Exception e) {
                            try {
                                con.rollback();
                            } catch (SQLException ex) {
                                System.out.println(ex.getMessage());
                            }
                            System.out.print("\nError: " + e.getMessage());
                            System.out.println("Transaction Rolled Back!");

                        } finally {
                            try {
                                con.setAutoCommit(true);
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;

                    case 2:

                        System.out.print("View All Employee: \n");
                        String viewQuery = "SELECT * FROM employee_db.employee";

                        PreparedStatement viewPs = con.prepareStatement(viewQuery);
                        ResultSet rs = viewPs.executeQuery();

                        System.out.println("\n**** Employee List ****");
                        while (rs.next()) {

                            System.out.print("Emp_ID       : " + rs.getInt("emp_id"));
                            System.out.print("\nEmp_Name   : " + rs.getString("emp_name"));
                            System.out.print("\nEmail      : " + rs.getString("email"));
                            System.out.print("\nContact    : " + rs.getString("contact"));
                            System.out.print("\nDepartment : " + rs.getString("department"));
                            System.out.print("\nSalary     : " + rs.getDouble("salary"));
                            System.out.println("--------------------------------");
                        }
                        break;

                    case 3:

                        System.out.print("Enter Employee ID to Search: ");
                        int searchId = sc.nextInt();

                        String searchQuery = "SELECT * FROM employee_db.employee WHERE emp_id = ?";
                        PreparedStatement searchPs = con.prepareStatement(searchQuery);
                        searchPs.setInt(1, searchId);

                        ResultSet searchRs = searchPs.executeQuery();

                        if (searchRs.next()) {

                            System.out.print("\n----- Employee Found -----");

                            System.out.print("Emp_ID       : " + searchRs.getInt("emp_id"));
                            System.out.print("\nEmp_Name   : " + searchRs.getString("emp_name"));
                            System.out.print("\nEmail      : " + searchRs.getString("email"));
                            System.out.print("\nContact    : " + searchRs.getString("contact"));
                            System.out.print("\nDepartment : " + searchRs.getString("department"));
                            System.out.print("\nSalary     : " + searchRs.getDouble("salary"));

                        } else {
                            System.out.println("Employee Not Found!");
                        }
                        break;

                    case 4:

                        System.out.print("Enter Employee Emp_ID to Update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter New Emp_Name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter New Email: ");
                        String newEmail = sc.nextLine();

                        System.out.print("Enter New Contact: ");
                        String newPhone = sc.nextLine();

                        System.out.print("Enter New Department: ");
                        String newDepartment = sc.nextLine();

                        System.out.print("Enter New Salary: ");
                        double newSalary = sc.nextDouble();

                        String updateQuery =
                                "UPDATE employee_db.employee SET employee.emp_name=?, email=?, contact=?, department=?, salary=? WHERE employee.emp_id=?";

                        PreparedStatement updatePs = con.prepareStatement(updateQuery);

                        updatePs.setString(1, newName);
                        updatePs.setString(2, newEmail);
                        updatePs.setString(3, newPhone);
                        updatePs.setString(4, newDepartment);
                        updatePs.setDouble(5, newSalary);
                        updatePs.setInt(6, updateId);

                        int updateRows = updatePs.executeUpdate();

                        if (updateRows > 0) {
                            System.out.print("Employee Updated Successfully!\n");
                            System.out.println("Rows Affected : " + updateRows);
                        } else {
                            System.out.print("Employee Not Found!\n");
                            System.out.println("Rows Affected : 0");
                        }
                        break;

                    case 5:

                        System.out.print("Enter Employee ID to Delete: ");
                        int deleteId = sc.nextInt();

                        String deleteQuery = "DELETE FROM employee_db.employee WHERE employee.emp_id = ?";

                        PreparedStatement deletePs = con.prepareStatement(deleteQuery);

                        deletePs.setInt(1, deleteId);

                        int deleteRows = deletePs.executeUpdate();

                        if (deleteRows > 0) {
                            System.out.print("Employee Deleted Successfully!\n");
                            System.out.println("Rows Affected : " + deleteRows);

                        } else {
                            System.out.print("Employee Not Found!\n");
                            System.out.println("Rows Affected : 0");
                        }
                        break;

                    case 6:

                        int bankChoice;
                        do {
                            System.out.print("\n******** Bank Services ********");
                            System.out.print("\n1. Deposit Money");
                            System.out.print("\n2. Withdraw Money");
                            System.out.print("\n3. Transfer Money");
                            System.out.print("\n4. Check Balance");
                            System.out.print("\n5. Transaction History");
                            System.out.print("\n6. Back");

                            System.out.print("Enter Choice: ");
                            bankChoice = sc.nextInt();

                            switch (bankChoice) {

                                case 1:
                                    System.out.print("\nDeposit Money");
                                    System.out.print("\n----- Deposit Money -----");

                                    System.out.print("Enter Account Number: ");
                                    sc.nextLine();
                                    String depositAccount = sc.nextLine();

                                    System.out.print("Enter Amount to Deposit: ");
                                    double depositAmount = sc.nextDouble();

                                    if (depositAmount <= 0) {
                                        System.out.print("\nInvalid Amount!");
                                        break;
                                    }

                                    String depositQuery =
                                            "UPDATE employee_db.bank_account SET balance = balance + ? WHERE account_no = ?";

                                    PreparedStatement depositPs = con.prepareStatement(depositQuery);
                                    depositPs.setDouble(1, depositAmount);
                                    depositPs.setString(2, depositAccount);

                                    int depositRows = depositPs.executeUpdate();
                                    if (depositRows > 0) {
                                        String historyQuery = "INSERT INTO employee_db.transaction_history " + "(account_no, transaction_type, amount) VALUES (?, ?, ?)";

                                        PreparedStatement historyPs = con.prepareStatement(historyQuery);
                                        historyPs.setString(1, depositAccount);
                                        historyPs.setString(2, "CREDIT");
                                        historyPs.setDouble(3, depositAmount);

                                        int historyRows = historyPs.executeUpdate();

                                        System.out.print("\nMoney Deposited Successfully!");
                                        System.out.print("Amount Credited : Rs. " + depositAmount);
                                        System.out.println("Rows Affected   : " + depositRows);

                                    } else {
                                        System.out.print("\nAccount Not Found!");
                                        System.out.println("Rows Affected : 0");
                                    }
                                    break;

                                case 2:

                                    System.out.print("\n----- Withdraw Money -----");
                                    System.out.print("Enter Account Number: ");
                                    sc.nextLine();
                                    String withdrawAccount = sc.nextLine();

                                    System.out.print("Enter Amount to Withdraw: ");
                                    double withdrawAmount = sc.nextDouble();

                                    if (withdrawAmount <= 0) {
                                        System.out.print("\nInvalid Amount!");
                                        break;
                                    }
                                    String balanceQuery = "SELECT balance FROM employee_db.bank_account WHERE account_no = ?";

                                    PreparedStatement balancePs = con.prepareStatement(balanceQuery);
                                    balancePs.setString(1, withdrawAccount);
                                    ResultSet balanceRs = balancePs.executeQuery();

                                    if (balanceRs.next()) {
                                        double currentBalance = balanceRs.getDouble("balance");
                                        System.out.print("\nCurrent Balance : Rs. " + currentBalance);

                                        if (currentBalance >= withdrawAmount) {
                                            String withdrawQuery = "UPDATE employee_db.bank_account SET balance = balance - ? WHERE account_no = ?";

                                            PreparedStatement withdrawPs = con.prepareStatement(withdrawQuery);
                                            withdrawPs.setDouble(1, withdrawAmount);
                                            withdrawPs.setString(2, withdrawAccount);

                                            int withdrawRows = withdrawPs.executeUpdate();
                                            if (withdrawRows > 0) {
                                                String withdrawHistoryQuery = "INSERT INTO employee_db.transaction_history " + "(account_no, transaction_type, amount) " + "VALUES (?, ?, ?)";

                                                PreparedStatement withdrawHistoryPs = con.prepareStatement(withdrawHistoryQuery);

                                                withdrawHistoryPs.setString(1, withdrawAccount);
                                                withdrawHistoryPs.setString(2, "DEBIT");
                                                withdrawHistoryPs.setDouble(3, withdrawAmount);

                                                int historyRows = withdrawHistoryPs.executeUpdate();
                                                double remainingBalance = currentBalance - withdrawAmount;

                                                System.out.print("\nMoney Withdrawn Successfully!");
                                                System.out.print("\nAmount Debited    : Rs. " + withdrawAmount);
                                                System.out.print("\nRemaining Balance : Rs. " + remainingBalance);
                                                System.out.println("Rows Affected     : " + withdrawRows);
                                            }
                                        } else {
                                            System.out.print("\nInsufficient Balance!");
                                            System.out.println("Available Balance : Rs. " + currentBalance);
                                        }
                                    } else {
                                        System.out.print("\nAccount Not Found!");
                                    }
                                    break;

                                case 3:

                                    System.out.print("\n----- Transfer Money -----");
                                    sc.nextLine();

                                    System.out.print("Enter Sender Account Number: ");
                                    String senderAccount = sc.nextLine();

                                    System.out.print("Enter Receiver Account Number: ");
                                    String receiverAccount = sc.nextLine();

                                    System.out.print("Enter Amount to Transfer: ");
                                    double transferAmount = sc.nextDouble();

                                    if (transferAmount <= 0) {
                                        System.out.print("\nInvalid Amount!");
                                        break;
                                    }

                                    if (senderAccount.equals(receiverAccount)) {
                                        System.out.print("\nSender and Receiver Account cannot be same!");
                                        break;
                                    }

                                    try {

                                        con.setAutoCommit(false);
                                        String senderQuery = "SELECT balance FROM employee_db.bank_account WHERE account_no = ?";

                                        PreparedStatement senderPs = con.prepareStatement(senderQuery);
                                        senderPs.setString(1, senderAccount);

                                        ResultSet senderRs = senderPs.executeQuery();
                                        if (!senderRs.next()) {
                                            System.out.println("Sender Account Not Found!");
                                            con.rollback();
                                            break;
                                        }

                                        double senderBalance = senderRs.getDouble("balance");
                                        String receiverQuery = "SELECT account_no FROM employee_db.bank_account WHERE account_no = ?";

                                        PreparedStatement receiverPs = con.prepareStatement(receiverQuery);
                                        receiverPs.setString(1, receiverAccount);

                                        ResultSet receiverRs = receiverPs.executeQuery();
                                        if (!receiverRs.next()) {
                                            System.out.print("\nReceiver Account Not Found!");
                                            con.rollback();
                                            break;
                                        }
                                        if (senderBalance < transferAmount) {
                                            System.out.print("\nInsufficient Balance!");
                                            System.out.print("\nAvailable Balance : Rs. " + senderBalance);
                                            con.rollback();
                                            break;
                                        }

                                        String debitQuery = "UPDATE employee_db.bank_account " + "SET balance = balance - ? " + "WHERE account_no = ?";

                                        PreparedStatement debitPs = con.prepareStatement(debitQuery);
                                        debitPs.setDouble(1, transferAmount);
                                        debitPs.setString(2, senderAccount);

                                        int debitRows = debitPs.executeUpdate();
                                        String creditQuery = "UPDATE employee_db.bank_account " + "SET balance = balance + ? " + "WHERE account_no = ?";

                                        PreparedStatement creditPs = con.prepareStatement(creditQuery);
                                        creditPs.setDouble(1, transferAmount);
                                        creditPs.setString(2, receiverAccount);

                                        int creditRows = creditPs.executeUpdate();
                                        String senderHistoryQuery = "INSERT INTO employee_db.transaction_history" + "(account_no, transaction_type, amount) " + "VALUES (?, ?, ?)";

                                        PreparedStatement senderHistoryPs = con.prepareStatement(senderHistoryQuery);
                                        senderHistoryPs.setString(1, senderAccount);
                                        senderHistoryPs.setString(2, "TRANSFER_OUT");
                                        senderHistoryPs.setDouble(3, transferAmount);

                                        int senderHistoryRows = senderHistoryPs.executeUpdate();

                                        String receiverHistoryQuery = "INSERT INTO employee_db.transaction_history " + "(account_no, transaction_type, amount) " + "VALUES (?, ?, ?)";
                                        PreparedStatement receiverHistoryPs =con.prepareStatement(receiverHistoryQuery);

                                        receiverHistoryPs.setString(1, receiverAccount);
                                        receiverHistoryPs.setString(2, "TRANSFER_IN");
                                        receiverHistoryPs.setDouble(3, transferAmount);

                                        int receiverHistoryRows =receiverHistoryPs.executeUpdate();
                                        if (debitRows > 0 && creditRows > 0 && senderHistoryRows > 0 && receiverHistoryRows > 0) {
                                            con.commit();

                                            System.out.print("\nTransfer Successful!");
                                            System.out.print("\nTransferred Amount : Rs. " + transferAmount);
                                            System.out.print("\nFrom Account       : " + senderAccount);
                                            System.out.print("\nTo Account         : " + receiverAccount);
                                            System.out.print("\nBank Rows Affected : " +(debitRows + creditRows));
                                            System.out.print("\nHistory Rows Added  : "+(senderHistoryRows + receiverHistoryRows));
                                            System.out.println("Transaction Committed Successfully!");
                                        } else {
                                            con.rollback();
                                            System.out.print("\nTransfer Failed!");
                                            System.out.print("\nTransaction Rolled Back!");
                                        }

                                    } catch (Exception e) {
                                        try {
                                            con.rollback();
                                        } catch (SQLException ex) {
                                            System.out.println(ex.getMessage());
                                        }

                                        System.out.print("\nSomething Went Wrong!");
                                        System.out.print("\nTransaction Rolled Back!");
                                        System.out.println("Reason : " + e.getMessage());

                                    } finally {
                                        try {
                                            con.setAutoCommit(true);
                                        } catch (SQLException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    break;

                                case 4:
                                    System.out.print("\n----- Check Balance -----");
                                    sc.nextLine();

                                    System.out.print("Enter Account Number: ");
                                    String checkAccount = sc.nextLine();
                                    String checkBalanceQuery ="SELECT balance FROM employee_db.bank_account WHERE account_no = ?";

                                    PreparedStatement checkBalancePs =con.prepareStatement(checkBalanceQuery);
                                    checkBalancePs.setString(1, checkAccount);
                                    ResultSet checkBalanceRs =checkBalancePs.executeQuery();

                                    if (checkBalanceRs.next()) {
                                        double balance =checkBalanceRs.getDouble("balance");

                                        System.out.print("\nAccount Number  : " + checkAccount);
                                        System.out.println("Current Balance : Rs. " + balance);

                                    } else {
                                        System.out.println("\nAccount Not Found!");
                                    }
                                    break;

                                case 5:
                                    System.out.print("\n----- Transaction History -----");
                                    sc.nextLine();
                                    System.out.print("Enter Account Number: ");
                                    String historyAccount = sc.nextLine();
                                    String historyQuery = "SELECT * FROM employee_db.transaction_history" + " WHERE account_no = ? " + "ORDER BY transaction_date DESC";

                                    PreparedStatement historyPs = con.prepareStatement(historyQuery);
                                    historyPs.setString(1, historyAccount);
                                    ResultSet historyRs = historyPs.executeQuery();

                                    boolean found = false;
                                    System.out.print("\n==============================================");
                                    System.out.print("\\nTransaction History of : " + historyAccount);
                                    System.out.println("==============================================");

                                    while (historyRs.next()) {
                                        found = true;
                                        System.out.print("\nTransaction ID   : " + historyRs.getInt("transaction_id"));
                                        System.out.print("\nTransaction Type : " + historyRs.getString("transaction_type"));
                                        System.out.print("\nAmount           : Rs. " + historyRs.getDouble("amount"));
                                        System.out.print("\nDate             : " + historyRs.getTimestamp("transaction_date"));
                                        System.out.println("----------------------------------------------");
                                    }
                                    if (!found) {
                                        System.out.println("No Transaction History Found!");
                                    }
                                    break;

                                case 6:
                                    System.out.print("\nReturning to Main Menu...");
                                    break;

                                default:
                                    System.out.println("Invalid Choice!");
                            }

                        } while (bankChoice != 6);
                        break;

                    case 7:
                        int batchChoice;
                        do {
                            System.out.print("\n******** Batch Processing ********");
                            System.out.print("\n1. Batch Employee Registration");
                            System.out.print("\n2. Batch Salary Credit");
                            System.out.print("\n3. Back");

                            System.out.print("Enter Choice: ");
                            batchChoice = sc.nextInt();

                            switch (batchChoice) {

                                case 1:

                                    System.out.print("\n----- Batch Employee Registration -----");
                                    System.out.print("How many employees do you want to add? ");
                                    int totalEmployees = sc.nextInt();
                                    sc.nextLine();

                                    String batchInsertQuery = "INSERT INTO employee_db.employee " + "(emp_id, emp_name, department, salary, contact, email) " + "VALUES (?, ?, ?, ?, ?, ?)";
                                    PreparedStatement batchPs = con.prepareStatement(batchInsertQuery);

                                    for (int i = 1; i <= totalEmployees; i++) {
                                        System.out.println("\nEnter Details of Employee " + i);

                                        System.out.print("Employee ID: ");
                                        int batchId = sc.nextInt();
                                        sc.nextLine();

                                        System.out.print("Employee Name: ");
                                        String batchName = sc.nextLine();

                                        System.out.print("Department: ");
                                        String batchDepartment = sc.nextLine();

                                        System.out.print("Salary: ");
                                        double batchSalary = sc.nextDouble();
                                        sc.nextLine();

                                        System.out.print("Contact Number: ");
                                        String batchContact = sc.nextLine();

                                        System.out.print("Email: ");
                                        String batchEmail = sc.nextLine();

                                        batchPs.setInt(   1, batchId);
                                        batchPs.setString(2, batchName);
                                        batchPs.setString(3, batchDepartment);
                                        batchPs.setDouble(4, batchSalary);
                                        batchPs.setString(5, batchContact);
                                        batchPs.setString(6, batchEmail);
                                        batchPs.addBatch();

                                        System.out.println("\nEmployee " + i + " added to Batch.");
                                    }

                                    int[] batchResult = batchPs.executeBatch();

                                    System.out.print("\nBatch Executed Successfully!");
                                    System.out.print("\nTotal Employees Added : " + batchResult.length);
                                    System.out.print("\nRows Affected:");
                                    for (int i = 0; i < batchResult.length; i++) {
                                        System.out.println("Employee " + (i + 1) + " : " + batchResult[i]);
                                    }
                                    break;

                                case 2:

                                    System.out.print("\n----- Batch Salary Credit -----");
                                    try {
                                        con.setAutoCommit(false);

                                        String salarySelectQuery = "SELECT emp_id, salary FROM employee_db.employee";

                                        PreparedStatement salarySelectPs = con.prepareStatement(salarySelectQuery);
                                        ResultSet salaryRs = salarySelectPs.executeQuery();
                                        String salaryCreditQuery = "UPDATE bank_account " + "SET balance = balance + ? " + "WHERE account_no = ?";

                                        PreparedStatement salaryCreditPs = con.prepareStatement(salaryCreditQuery);

                                        int employeeCount = 0;
                                        while (salaryRs.next()) {
                                            int salaryEmpId = salaryRs.getInt("emp_id");

                                            double employeeSalary = salaryRs.getDouble("salary");
                                            String salaryAccount = "ACC" + salaryEmpId;

                                            salaryCreditPs.setDouble(1, employeeSalary);
                                            salaryCreditPs.setString(2, salaryAccount);
                                            salaryCreditPs.addBatch();
                                            employeeCount++;
                                            System.out.println(salaryAccount + " -> Rs. " + employeeSalary + " added to Batch");
                                        }
                                        if (employeeCount == 0) {
                                            System.out.print("\nNo Employees Found!");
                                            con.rollback();
                                            break;
                                        }

                                        int[] salaryResult = salaryCreditPs.executeBatch();
                                        con.commit();
                                        System.out.print("\nBatch Salary Credit Successful!");
                                        System.out.print("\nTotal Employees Processed : " + salaryResult.length);
                                        System.out.println("\nRows Affected:");

                                        for (int i = 0; i < salaryResult.length; i++) {
                                            System.out.println("Employee " + (i + 1) + " : " + salaryResult[i]);
                                        }
                                        System.out.println("\nTransaction Committed Successfully!");
                                    } catch (Exception e) {
                                        try {
                                            con.rollback();
                                        } catch (SQLException ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                        System.out.print("\nBatch Salary Credit Failed!");
                                        System.out.print("\nTransaction Rolled Back!");
                                        System.out.println("Reason : " + e.getMessage());

                                    } finally {
                                        try {
                                            con.setAutoCommit(true);
                                        } catch (SQLException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    break;

                                case 3:
                                    System.out.print("\nReturning to Main Menu...");
                                    break;

                                default:
                                    System.out.println("Invalid Choice!");
                            }
                        } while (batchChoice != 3);

                    case 8:
                        System.out.print("Exiting Employee Majdoor work!!!\n");
                        System.out.print("Thank you :)\n");
                        con.close();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid Choice!");
                }
            } while (choice != 8);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        sc.close();
    }
}