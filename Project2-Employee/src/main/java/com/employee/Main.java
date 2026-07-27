package com.employee;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        EmployeeDAO dao = new EmployeeDAO();

        while (true) {

            System.out.println("\n**** Employee Management System ****");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();

                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();

                    Employee emp = new Employee(id, name, dept, salary);

                    dao.addEmployee(emp);
                    break;

                case 2:
                    dao.viewEmployees();
                    break;

                case 3:
                    System.out.print("Enter Employee ID: ");
                    id = sc.nextInt();

                    dao.searchEmployee(id);
                    break;

                case 4:
                    System.out.print("Enter Employee ID: ");
                    id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter New Name: ");
                    name = sc.nextLine();

                    System.out.print("Enter New Department: ");
                    dept = sc.nextLine();

                    System.out.print("Enter New Salary: ");
                    salary = sc.nextDouble();

                    emp = new Employee(id, name, dept, salary);

                    dao.updateEmployee(emp);
                    break;

                case 5:
                    System.out.print("Enter Employee ID: ");
                    id = sc.nextInt();

                    dao.deleteEmployee(id);
                    break;

                case 6:
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}