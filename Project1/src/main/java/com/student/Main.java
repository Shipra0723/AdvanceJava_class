package com.student;
import java.sql.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String URL ="jdbc:mysql://localhost:3306/student";
        String USER = "root";
        String PASSWORD = "Shipra@218";
        try {

            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);

            PreparedStatement pr=con.prepareStatement("insert into studentdata values (?,?,?)");
            System.out.println("Enter id  : \n name :\n Domain :");
            Scanner sc = new Scanner(System.in);


            pr.setInt(1,sc.nextInt());
            sc.nextLine();
            pr.setString(2,sc.nextLine());
            pr.setString(3,sc.nextLine());

            //4th Step(Execute Query)
            int rowsaffect = pr.executeUpdate();
            System.out.println(rowsaffect);
            if(rowsaffect>0){
                System.out.println("Data inserted");
            }
            con.close();


        } catch ( SQLException e) {
            throw new RuntimeException(e);
        }


    }
}