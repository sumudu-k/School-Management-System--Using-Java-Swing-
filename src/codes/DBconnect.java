/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codes;

import java.sql.Connection; //video one
//import com.sun.jdi.connect.spi.Connection; //default one

import java.sql.DriverManager; //vide one
// have to crate new drivermanager class //default one

import javax.swing.JOptionPane; // default and video one

/**
 *
 * @author Sumudu Lakshan
 */
public class DBconnect {
    public static Connection connect(){
        Connection conn = null; // create connection variable called conn      
        try{
            Class.forName("com.mysql.jdbc.Driver"); //Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaschoolmanagementsystemupgraded","root",""); 
            //"jdbc:mysql://localhost:3306/javaproject1"--> get from services tab
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }                  
        return conn; 
    }   
}






