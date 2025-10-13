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
    int Option;
    String input;
    int totalMessagesSent;
    String RecepientcellNo;
    String recepientName;
    int messagesSent;
    int messageNumber = 0;
    String message;
    int retry = 3; //inistializing variables and constants
    boolean messageLength = false;
    ArrayList<String> storedMessages = new ArrayList<>();
    ArrayList<String> messageIDs = new ArrayList<>();
    ArrayList<String> messageHashes = new ArrayList<>(); //arraylist logic to prep for part 3
    StringBuilder info = new StringBuilder();
    boolean sessionActive = true;
    int over;
    public void mainMenu(){ //action selection menu
        Object[] options = {"    1    ","    2    ","   3    "};
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
                JOptionPane.showMessageDialog(null,"Coming Soon."); //part 3 feature
                break;
            case 2:
                sessionActive = false;
                System.exit(0); //exits program when user selects exit
            default:
                break;
        }
    }
    public void communicate(){
        retry = 3;
        while(retry > 0){
        RecepientcellNo = JOptionPane.showInputDialog("Insert recepient cellphone number.");
        if(checkRecepientCell(RecepientcellNo)){
            JOptionPane.showMessageDialog(null, "Recepient number successfully captured"); //prompts user for necessary info
            retry =3; //resets retry variable after successful data capture
            break;
        }
        else{ 
            retry --; //increment to reduce try amounts
            if(retry > 0){
            JOptionPane.showMessageDialog(null,"Try again, "+retry+" attempt(s) remaining!","ALERT!",JOptionPane.ERROR_MESSAGE);
            }
        }
        }
        if(retry <= 0){
            JOptionPane.showMessageDialog(null,"Too many attempts, try again later.","PROGRAM EXITING...",JOptionPane.ERROR);
            System.exit(0); //exits program on too many attempts
        }
        recepientName = JOptionPane.showInputDialog("Insert Recepient name: ");
         input = JOptionPane.showInputDialog("How many messages would you like to send?"); 
        messagesSent = Integer.parseInt(input); //parses message amount to int(Joption only allows String entries)
        for (int i = 0; i < messagesSent; i++){
        writeMessage(); //calls for message writing method for every message consecutively and exits loop when complete
        }

    }
    public static boolean checkRecepientCell(String recepientNo){ //module to validate recepient phone number
       boolean format = false;
        boolean length = false;
        recepientNo = recepientNo.replaceAll("[\\s-]", ""); //removes number spaces
        if(recepientNo.matches("^(\\+27|27)\\d{9}")){  //OpenAI. 2025. ChatGPT. Available at: https://chat.openai.com/[Accessed 7 September 2025].
            length = true;
            format = true;
        }
        return length&&format;
    }
    public String createMessageID(){ //generates message    ID's using random creation
      boolean idValid = false;
      Random ID = new Random();
      StringBuilder messageID = new StringBuilder();
      while(!idValid){ //ensures correct id is generated, runs until it is
      long id = 1000000000L +(long)(ID.nextDouble()*8999999999L); //sets lenght and value rules for auto generated id
      String TempmessageID = String.valueOf(id); //sends id to another string to validate
      if(checkMessageID(TempmessageID)){
      messageID.append(TempmessageID);
      messageIDs.add(TempmessageID);
      idValid = true;
      }
      else{
        System.out.println("ID Generator failed, trying again...."); //retries id generation on fail
      }
      }
      return messageID.toString();
    }
    public static boolean checkMessageID(String checkID){ //checks id length
        return checkID.length() ==10;
    }
    public String createMessageHash(String message){ //creates message hash
        String[] hashWords = message.split(" "); //removes  spaces in the messages to prepare for hash generatiin 
        messageNumber ++; //increments message number to generate correct hash
        return createMessageID().substring(0,2)+":"+messageNumber+":"+hashWords[0]+hashWords[hashWords.length-1];
    }
    public void writeMessage(){ //method to write the message
        while(messagesSent > 0){ //runs until all required messages are processed
        message = JOptionPane.showInputDialog("INSERT YOUR MESSAGE HERE : ");
        message = message.toUpperCase(); //converts message to uppercase for the hash
        sendMessage();
        messagesSent --; //increment to accommodate while loop
        }
    }
    public String sendMessage(){ //message action menu
        Object[] options = {"   1   ","   2   ","   3   "}; //array of options
        this.Option = JOptionPane.showOptionDialog(null,  //pane with options for actions
        "1.Send Message\n2.Store Message\n3.Delete Message",
        "Choose what to do with your message",
        JOptionPane.YES_NO_CANCEL_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options, options[0]);
        switch(Option){
            case 0:
            JOptionPane.showMessageDialog(null, checkMessageLength(message)); //calls message length  checker
            totalMessagesSent++; //increment for final window display
            storedMessages.add(message); //adds the message into array
            messageHashes.add(createMessageHash(message)); //adds message hash into hash array
             //for future reference in part 3(arrays)
               storeMessage();
               return "Message Sent Successfully";
            case 1:
               storeMessage();
               return "Message Successully stored";
            case 2:
               return "Message Deleted";
            default:
               return "Message Successfully stored";
        }
    }
    public String checkMessageLength(String message){ //ensures message length is not above 50
         if(message.length()>=250){
            messageLength = false;
            this.over = message.length() - 250;
            return "Message exceeds 250 characters by "+over+", please reduce size";
         }
         else {
            messageLength = true;
            return "Message ready to send.";
         }
    }
    public void storeMessage(){
        try {
        // Use org.json to build JSON objects
        org.json.JSONArray jsonArray = new org.json.JSONArray();

        // Loop through stored messages and pair them with their IDs and hashes
        for (int i = 0; i < storedMessages.size(); i++) {
            org.json.JSONObject messageObject = new org.json.JSONObject();
            messageObject.put("messageID", messageIDs.get(i));
            messageObject.put("messageHash", messageHashes.get(i));
            messageObject.put("recipient", RecepientcellNo);
            messageObject.put("message", storedMessages.get(i));
            jsonArray.put(messageObject);
        }

        // Wrap in a parent object
        org.json.JSONObject root = new org.json.JSONObject();
        root.put("messages", jsonArray);

        // Write JSON file to project root
        java.io.FileWriter file = new java.io.FileWriter("messages.json");
        file.write(root.toString(4)); // formatted output
        file.flush();
        file.close();

        JOptionPane.showMessageDialog(null, "Messages successfully stored in JSON file.");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error saving messages to JSON: " + e.getMessage());
        e.printStackTrace();
    }
    /**
    *Reference:
    * OpenAI. 2025. ChatGPT. [Online]. 
    * Available at: https://chat.openai.com/ 
    * [Accessed 13 October 2025].
    * 
    * JSON Code generated with assistance from ChatGPT (OpenAI, 2025).
    */
        

    }
    public String printMessage(){ //prints the SENT messages and leaves out stored messages
        StringBuilder sentMessages = new StringBuilder();
        for(int i = 0; i < storedMessages.size();i++){
            sentMessages.append("ID: ").append(messageIDs.get(i)).append(" | Message: ").append(storedMessages.get(i)).append("\n");
        }
        return recepientName+"("+RecepientcellNo+")\n"+sentMessages.toString();
    }
    public int returnTotalMessages(){
        return totalMessagesSent; //shows how many messages were sent
    }
}

