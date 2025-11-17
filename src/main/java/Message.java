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
    ArrayList<String> messageHashes = new ArrayList<>();
    ArrayList<String> sentMessages = new ArrayList<>();
    ArrayList<String> disregardedMessages = new ArrayList<>();
    ArrayList<String> messageFlag = new ArrayList<>();
    ArrayList<String> recepients = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    StringBuilder info = new StringBuilder();
    boolean sessionActive = true;
    String searchIndex;
    int over;
    public void mainMenu(){ //action selection menu
        Object[] options = {"    1    ","    2    ","   3    ","   4   ","  5  "};
        int menuSelection = JOptionPane.showOptionDialog(null, "Select An Option: \n1. Send Messages\n2. Display Deleted messages\n3.Search for messages\n5.Delete a messagd using Hash\n4.Quit",
        "Welcome to QuickChat",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options,
        options[0]);
        switch (menuSelection) {
            case 0:
                communicate();
                break;
            case 1:
                JOptionPane.showMessageDialog(null,"Disregarded "+ printDeletedMessages()); //part 3 feature
                break;
            case 2:
                searchIndex = JOptionPane.showInputDialog(null, "Insert Message ID:");
                JOptionPane.showMessageDialog(null, searchMessageID(searchIndex));
            case 3:
                searchIndex = JOptionPane.showInputDialog("Insert Message Hash:");
                JOptionPane.showMessageDialog(null, deleteMessage(searchIndex));
            case 4:
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
      messageIDs.add(TempmessageID);
      messageID.append(TempmessageID);
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
    public String createMessageHash(String message, String id){ //creates message hash
        String[] hashWords = message.split(" "); //removes  spaces in the messages to prepare for hash generatiin 
        messageNumber ++; //increments message number to generate correct hash
        String messageHash = id.substring(0,2)+":"+messageNumber+":"+hashWords[0]+hashWords[hashWords.length-1];
        return messageHash;
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
        Object[] options = {"   1   ","   2   ","   3   ","CANCEL"}; //array of options
        this.Option = JOptionPane.showOptionDialog(null,  //pane with options for actions
        "1.Send Message\n2.Store Message\n3. Disregard Message",
        "Choose what to do with your message",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        options, options[0]);
        switch(Option){
            case 0:
            JOptionPane.showMessageDialog(null, checkMessageLength(message)); //calls message length  checker
             //for future reference in part 3(arrays)
               storeMessage();
               totalMessagesSent++; //increment for final window display
               storedMessages.add(message);
               messageIDs.add(createMessageID()); //adds the message into array
               for(int i = 0; i<storedMessages.size();i++){
                if(storedMessages.get(i) == message){
                   messageHashes.add(createMessageHash(message, messageIDs.get(i))); //adds message hash into hash array
                }
               }
               messageFlag.add("sent");
               recepients.add(RecepientcellNo);
               names.add(recepientName);
               return "Message Sent Successfully";
            case 1:
               storeMessage();
               storedMessages.add(message);
               messageIDs.add(createMessageID());
               for(int i = 0; i<storedMessages.size();i++){
                if(storedMessages.get(i) == message){
                   messageHashes.add(createMessageHash(message, messageIDs.get(i))); //adds message hash into hash array
                }
               }
               messageFlag.add("stored");
               recepients.add(RecepientcellNo);
               names.add(recepientName);
               return "Message Successully stored";
            case 2:
               storedMessages.add(message);
               messageIDs.add(createMessageID());
               for(int i = 0; i<storedMessages.size();i++){
                if(storedMessages.get(i) == message){
                   messageHashes.add(createMessageHash(message, messageIDs.get(i))); //adds message hash into hash array
                }
               }
               messageFlag.add("disregarded");
               recepients.add(RecepientcellNo);
               names.add(recepientName);
               return "Message Successully disregarded";
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
            messageObject.put("recipientCell", recepients.get(i));
            messageObject.put("recepientName", names.get(i));
            messageObject.put("message", storedMessages.get(i));
            messageObject.put("messageFlag", messageFlag.get(i));
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

        System.out.println("Messages successfully stored in JSON file.");

    } catch (Exception e) {
        System.out.println("Error saving messages to JSON: " + e.getMessage());
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
    public String printSentMessages(){ //prints the SENT messages and leaves out stored messages
        StringBuilder sentMessages = new StringBuilder();
        for(int i = 0; i < messageFlag.size();i++){
            if(messageFlag.get(i) == "sent"){
            sentMessages.append("Name: "+names.get(i)).append(" | Cell No: "+recepients.get(i)).append(" | ID: ").append(messageIDs.get(i)).append(" | Message: ").append(storedMessages.get(i)).append("\n");
            }
        }
        return sentMessages.toString();
    }
    public String printDeletedMessages(){
        StringBuilder sentMessages = new StringBuilder();
        for(int i = 0; i < messageFlag.size();i++){
            if(messageFlag.get(i) == "disregarded"){
            sentMessages.append("Name: "+names.get(i)).append(" | Cell No: "+recepients.get(i)).append(" | ID: ").append(messageIDs.get(i)).append(" | Message: ").append(storedMessages.get(i)).append("\n");
            }
        }
        return sentMessages.toString();
    }
    public int returnTotalMessages(){
        return totalMessagesSent;
     } //shows how many messages were sent
    public String searchMessageID(String index){
        boolean found = false;
        StringBuilder result = new StringBuilder();
        for(int i = 0; i<messageIDs.size();i++){
            if(index.equals(messageIDs.get(i))){
            found = true;
            result.append("Name: "+names.get(i)).append(" | Cell No: "+recepients.get(i)).append(" | ID: ").append(messageIDs.get(i)).append(" | Message: ").append(storedMessages.get(i)).append(" | ").append(messageFlag.get(i)).append("\n");
            break;
            }
        }
        if(found)      
        return result.toString();
        else return "Message or ID not found";
    }
    public String deleteMessage(String index){
        boolean found = false;
        for(int i = 0;i<messageHashes.size();i++){
            if(messageHashes.get(i) == index){
                storedMessages.remove(i);
                messageHashes.remove(i);
                messageIDs.remove(i);
                recepients.remove(i);
                names.remove(i);
                messageFlag.remove(i);
                found = true;
            }
        }
        if(found)
        return "Message successfully deleted";
        else return "Message not found";
    }
    public String returnLongestMessage(){
        String longest = storedMessages.get(0);
        for(int i = 1; i<storedMessages.size();i++){
            if(storedMessages.get(i).length()>longest.length()){
                longest = storedMessages.get(i);
            }
        }
        return longest;
    }
}

