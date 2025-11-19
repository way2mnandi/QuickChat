import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
public class MessageTest {
    @Test
    void TestRecepientCellValidator(){
        assertTrue(Message.checkRecepientCell("+27718693002\r"));
        assertFalse(Message.checkRecepientCell("08575975889")); //tests cell no validation
    }
    @Test
    void testMessageIDvalidator(){
        Message msg = new Message();
        assertTrue(Message.checkMessageID(msg.createMessageID()));
        assertFalse(Message.checkMessageID("000000"));
    }
    @Test
    void testMessageLengthSuccess(){ //tests message length validator on success
        Message msg = new Message();
        String expectedMessage = "Message sent.";
        String result = msg.checkMessageLength("Hi Mike, can you join us for dinner tonight");
        assertEquals(expectedMessage, result);
    }
    @Test
    void testMessageLengthFailure(){ //tests message length validation on failure
        Message msg = new Message();
        String expectedMessage = "Message exceeds 250 characters by "+10+", please reduce size";
        String result = msg.checkMessageLength("A".repeat(260));
        assertEquals(expectedMessage, result);
    }
    @Test
    void messageHashSuccess(){ //tests message hash creation
        Message msg = new Message();
        String expectedMessage = String.format("12:1:HITONIGHT");
        String result = msg.createMessageHash(("Hi Mike, can you join us for dinner tonight").toUpperCase(), "1234567890");
        assertEquals(expectedMessage, result);
    }
    @Test
    void testMessageIDGeneration(){ //tests id generation
        Message msg = new Message();
        String result = msg.createMessageID();
        assertTrue(result.matches("\\d{10}"));
    }
    @Test
    void testMessageDeleteFailure(){ //tests a failed message delete attempt
        Message msg = new Message();
        String ID = msg.createMessageID(); //generates new ID within the program to run hash genereation
        String hash = msg.createMessageHash("Where are you? You are late! I have asked you to be on time", ID);
        //creates hash using generated ID + test data
        assertEquals("Message not found", msg.deleteMessage(hash));
    }
    @Test
    void testMessageDeleteSuccess(){ //test message delete successful attempt
        Message msg = new Message();
        String ID = msg.createMessageID();
        String hash = msg.createMessageHash("Where are you? You are late! I have asked you to be on time", ID);
        msg.storedMessages.add("Where are you? You are late! I have asked you to be on time");
        msg.messageHashes.add(hash); msg.messageIDs.add(ID); msg.recepients.add("+27838884567");
        msg.names.add("test2");msg.messageFlag.add("stored"); //fully populates arrays to test delete function accuracy
        assertEquals("Message ("+msg.storedMessages.get(0)+") successfully deleted", msg.deleteMessage(hash));
    }
    @Test
    void longestMessageTest(){ //tests the longest message checker
        Message msg = new Message();
        msg.storedMessages.add("Did you get the cake?");
        msg.storedMessages.add("Where are you? You are late! I have asked you to be on time.");
        msg.storedMessages.add("Yohoooo, I am at your gate.");
        msg.storedMessages.add("It is dinner time!"); //populates message arrays only
        assertEquals("Where are you? You are late! I have asked you to be on time.",msg.returnLongestMessage());

    }
    @Test
    void searchMessageIDTest(){
        Message msg = new Message();
        String ID;
        ID = msg.createMessageID();
        String hash = msg.createMessageHash("It is dinner time!", ID);
        msg.storedMessages.add("It is dinner time!");
        msg.messageHashes.add(hash); msg.messageIDs.add(ID); msg.recepients.add("0838884567");
        msg.names.add("test4");msg.messageFlag.add("sent"); //fully populates arrays to test delete function accuracy
        String expectedMessage = "Name: test4 | Cell No: 0838884567 | ID: "+ID+" | Message: It is dinner time! | sent"+"\n";
        assertEquals(expectedMessage, msg.searchMessageID(ID));
    }
    @Test
    void sentMessageSearchTest(){
        Message msg = new Message();
        msg.storedMessages.add("Did you get the cake?"); msg.names.add("test1"); msg.recepients.add("+27834557896");msg.messageIDs.add(msg.createMessageID());msg.messageFlag.add("sent");
        msg.storedMessages.add("Where are you? You are late! I have asked you to be on time.");msg.names.add("test2"); msg.recepients.add("+27838884567");msg.messageIDs.add(msg.createMessageID());msg.messageFlag.add("stored");
        msg.storedMessages.add("Yohoooo, I am at your gate.");msg.names.add("test3"); msg.recepients.add("+27834484567");msg.messageIDs.add(msg.createMessageID());msg.messageFlag.add("disregarded");
        msg.storedMessages.add("It is dinner time!"); msg.names.add("test4"); msg.recepients.add("0838884567");msg.messageIDs.add(msg.createMessageID());msg.messageFlag.add("sent");
        String expectedMessage = "Name: test1 | Cell No: +27834557896 | ID: "+msg.messageIDs.get(0)+" | Message: Did you get the cake?\n"+
                                 "Name: test4 | Cell No: 0838884567 | ID: "+msg.messageIDs.get(3)+" | Message: It is dinner time!\n";
        assertEquals(expectedMessage, msg.printSentMessages());
    }
    @Test
    void recepientSearchTest(){
        Message msg = new Message();
        msg.storedMessages.add("Did you get the cake?"); msg.names.add("test1"); msg.recepients.add("+27834557896");msg.messageIDs.add(msg.createMessageID());msg.messageFlag.add("sent");
        msg.storedMessages.add("Where are you? You are late! I have asked you to be on time.");msg.names.add("test2"); msg.recepients.add("+27838884567");msg.messageIDs.add(msg.createMessageID());msg.messageFlag.add("stored");
        msg.storedMessages.add("Yohoooo, I am at your gate.");msg.names.add("test3"); msg.recepients.add("+27834484567");msg.messageIDs.add(msg.createMessageID());msg.messageFlag.add("disregared");
        msg.storedMessages.add("It is dinner time!"); msg.names.add("test4"); msg.recepients.add("0838884567");msg.messageIDs.add(msg.createMessageID());msg.messageFlag.add("sent");
        msg.storedMessages.add("Ok, I am leaving without you"); msg.names.add("test5"); msg.recepients.add("+27838884567");msg.messageIDs.add(msg.createMessageID());msg.messageFlag.add("stored");
        String expectedMessage = "Where are you? You are late! I have asked you to be on time.\nOk, I am leaving without you\n";
        assertEquals(expectedMessage, msg.searchByRecepient("27838884567"));
    }

    
    
}
