/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RC_Student_Lab
 */
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
public class Message {
    String RecepientcellNo;
    String recepientName;
    int messagesSent;
    int messageNumber = 0;
    String message;
    int retry = 3;
    ArrayList<String> storedMessages = new ArrayList<>();
    ArrayList<String> messageIDs = new ArrayList<>();
    ArrayList<String> messageHashes = new ArrayList<>();
    StringBuilder info = new StringBuilder();
    boolean sessionActive = true;
    public void mainMenu(){
        Object[] options = {"|   1   |","|   2   |","|   3   |"};
        int menuSelection = JOptionPane.showOptionDialog(null, "Select An Option: \n1. Send Messages\n2. Display old messages\n3.Quit",
        "Welcome to QuickChat",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options,
        options[0]);
        switch (menuSelection) {
            case 0:
                communicate();
                break;
            case 1:
                JOptionPane.showMessageDialog(null,"Coming Soon.");
                break;
            case 2:
                sessionActive = false;
                System.exit(0);
            default:
                break;
        }
    }
    public void communicate(){
        retry = 3;
        while(retry > 0){
        RecepientcellNo = JOptionPane.showInputDialog("Insert recepient cellphone number.");
        if(checkRecepientCell(RecepientcellNo)){
            JOptionPane.showMessageDialog(null, "Recepient number successfully captured");
            retry =3;
            break;
        }
        else{ 
            retry --;
            if(retry > 0){
            JOptionPane.showMessageDialog(null,"Try again, "+retry+" attempt(s) remaining!","ALERT!",JOptionPane.ERROR_MESSAGE);
            }
        }
        }
        if(retry <= 0){
            JOptionPane.showMessageDialog(null,"Too many attempts, try again later.","PROGRAM EXITING...",JOptionPane.ERROR);
            System.exit(0);
        }
        recepientName = JOptionPane.showInputDialog("Insert Recepient name: ");
        String input = JOptionPane.showInputDialog("How many messages would you like to send?");
        messagesSent = Integer.parseInt(input);
        for (int i = 0; i < messagesSent; i++){
            writeMessage();
            storeMessageInfo();
            info.append("ID: ").append(messageIDs.get(i)).append(" | Hash: ").append(messageHashes.get(i)).append(" | Message: ").append(storedMessages.get(i)).append("\n");
        }

    }
    public static boolean checkRecepientCell(String recepientNo){
       boolean format = false;
        boolean length = false;
        recepientNo = recepientNo.replaceAll("[\\s-]", ""); //removes number spaces
        if(recepientNo.matches("^(\\+27|27)\\d{9}")){  //OpenAI. 2025. ChatGPT. Available at: https://chat.openai.com/[Accessed 7 September 2025].
            length = true;
            format = true;
        }
        return length&&format;
    }
    public String createMessageID(){
      boolean idValid = false;
      Random ID = new Random();
      StringBuilder messageID = new StringBuilder();
      while(!idValid){
      long id = 1000000000L +(long)(ID.nextDouble()*8999999999L);
      String TempmessageID = String.valueOf(id);
      if(checkMessageID(TempmessageID)){
      messageID.append(TempmessageID);
      messageIDs.add(TempmessageID);
      idValid = true;
      }
      else{
        System.out.println("ID Generator failed, trying again....");
        System.exit(0);
      }
      }
      return messageID.toString();
    }
    public static boolean checkMessageID(String checkID){
        return checkID.length()<=10;
    }
    public String createMessageHash(){
        String[] hashWords = message.split(" ");
        messageNumber ++;
        return createMessageID().substring(0,2)+";"+messageNumber+":"+hashWords[0]+hashWords[hashWords.length-1];
    }
    public void writeMessage(){
        message = JOptionPane.showInputDialog("INSERT YOUR MESSAGE HERE: ");
        message = message.toUpperCase();
    }
    public void storeMessageInfo(){
        storedMessages.add(message);
        messageHashes.add(createMessageHash());
    }
    public String printMessage(){
        StringBuilder sentMessages = new StringBuilder();
        for(int i = 0; i < messagesSent;i++){
            sentMessages.append("ID: ").append(messageIDs.get(i)).append(" | Message: ").append(storedMessages.get(i)).append("\n");
        }
        return sentMessages.toString();
    }
}

