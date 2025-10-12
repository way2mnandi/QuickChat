import java.util.Scanner;

import javax.swing.JOptionPane;
public class Login{
    public static final boolean loggedIn = false;
    String username;
    String password;
    String cellPhone;
    String firstName;
    String lastName;  //Initializing input variables
    boolean userOk;
    boolean passOk;
    boolean cellOk;
    boolean login;
    Scanner reader = new Scanner(System.in);
    boolean activeStatus;
    public void getInfo(){
        firstName = JOptionPane.showInputDialog("Insert Your First name");
        lastName = JOptionPane.showInputDialog("Insert Your Last name");;

        int retry = 3;  //constant for retry logic
        while(retry > 0 && !userOk){
         username = JOptionPane.showInputDialog("Insert Username");
         userOk = checkUser(username);
         if(userOk){
            JOptionPane.showMessageDialog(null, "Username successfully captured.");
            retry = 3;  //resets constant to move onto next method
          }
         else {
            retry --; //reduces tries to stop users from entering incompatible credentials repeatedly
              JOptionPane.showMessageDialog(null, "Username is not correctly formatted"+"\n"+"Please ensure that your username has an underscore and is no more than 5 characters in length.");
            if(retry > 0)
            JOptionPane.showMessageDialog(null, "Try again, "+retry+" attempts remaining");
          } //display prompt for what went wrong
        }
        while(retry > 0 && !passOk){
         password = JOptionPane.showInputDialog("Insert Password");
         passOk = checkPassword(password);
          if(passOk){
            JOptionPane.showMessageDialog(null, "Password Successfully captured.");
            retry = 3;
          }
          else {
            retry --;
            JOptionPane.showMessageDialog(null, "Password is not correctly formatted"+"\n"+"Please ensure that the password contains atleast eight characters, a capital letter, a number and a special character");
            if(retry > 0)
            JOptionPane.showMessageDialog(null, "Try again, "+retry+" attempts remaining");
          }
        }
        while(retry > 0 && !cellOk){
         cellPhone = JOptionPane.showInputDialog("Insert Cellphone number");
         cellOk = checkNumber(cellPhone);
          if(cellOk){
            JOptionPane.showMessageDialog(null,"Cellphone number successfully captured.");
            retry = 3;
          }
          else {
            retry --;
            JOptionPane.showMessageDialog(null, "Cellphone incorrectly formatted or does not contain international code");
            if(retry > 0)
            JOptionPane.showMessageDialog(null,"Try again, "+retry+" attempts remaining");
          }
        }
    }
    public static boolean checkUser(String name){
        return name.contains("_")&&name.length()<=5; //returns name validation results
    }
    public static boolean checkPassword(String passkey){
        if (passkey.length()<8){
            return false;  //returns false immediately if passkey is too short
        }
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;
        boolean hasUppercase = false;
        String chars = "~!@#$%^&*()_+{}'`'|:<>,.?/";
        for(char c : passkey.toCharArray()){ //converts password to character arrays for validationg eache character
            if(Character.isDigit(c)){
                hasNumber = true; //checks if passkey has a number
            }
            if(Character.isUpperCase(c)){
                hasUppercase = true; //checks is passkey has an uppercase letter
            }
            if(chars.contains(Character.toString(c))){
                hasSpecialCharacter = true; //conversts static chars to arrays and checks if passkey contains any of them
            }
        }
        return hasNumber&&hasSpecialCharacter&&hasUppercase;
    }
    public static boolean checkNumber(String Number){ //checks if number is correct length and begins with country code
        boolean format = false;
        boolean length = false;
        Number = Number.replaceAll("[\\s-]", ""); //removes number spaces
        if(Number.matches("^(\\+27|27)\\d{9}")){  //OpenAI. 2025. ChatGPT. Available at: https://chat.openai.com/[Accessed 7 September 2025].
            length = true;
            format = true;
        }
        return length&&format;
    }
    public String registerUser(){  //returns validation feedback
        StringBuilder errors = new StringBuilder();  //builds string errors according to which iinputs the user got wrong
        if(!userOk) {
            errors.append ("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n");
        }
        if(!passOk){
            errors.append ("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
        }
        if(!cellOk){
            errors.append("Cell phone number incorrectly formatted or does not contain international code.\n");
        }
        if(!cellOk||!userOk||!passOk){
            return  errors.toString();
        }
        else{
            this.login = true;
            return "Registration successful.";
        }
    }
    public boolean loginUser(){
        boolean loginStatus = false;
        int retry = 3; //setting login retry attempt limits
        JOptionPane.showMessageDialog(null, "Welcome to the login page");
        while(!loginStatus && retry > 0){
        String currentname = JOptionPane.showInputDialog("Insert your username");
        String currentpassword = JOptionPane.showInputDialog("Insert your password");
        if(currentname.equals(username)&&currentpassword.equals(password)){
            loginStatus = true;
            retry = 3;
        }
        else {
            retry--;
            if(retry > 0)
            JOptionPane.showMessageDialog(null, "Username or password incorrect, please try again. ("+retry+" attempts remaining)"); //prompts for retries with how many are left
        }
        }
        return loginStatus;
    }
    public String returnLoginStatus(){
        boolean loggedIn = loginUser();
        if(loggedIn){
            activeStatus = true;
            return "Welcome "+firstName+" "+lastName+" it is great to see you again.";  //greets user at successful login
        }
        else{
            return "Login Unsuccessful";
        }
    }
   
}