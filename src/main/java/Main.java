
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_lab
 */
public class Main {
    public static void main(String[] args) {
        Login user1 = new Login();
        Message u1 = new Message();
        user1.getInfo();
        boolean loginStatus;
        JOptionPane.showMessageDialog(null, user1.registerUser());
        if(user1.login){
         JOptionPane.showMessageDialog(null, user1.returnLoginStatus());
        }
        else {
            System.exit(0);
        }
        u1.restoreMemory(); //calls for memory restoration after a successful login(failed login kills the program so this only works after successful login)
        loginStatus = user1.activeStatus; //boolean to validate login status
        while(loginStatus){
        loginStatus = u1.sessionActive;
        u1.mainMenu();
        }
    }
}
