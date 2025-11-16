import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
public class MessageTest {
    @Test
    void TestRecepientCellValidator(){
        assertTrue(Message.checkRecepientCell("+27718693002\r"));
        assertFalse(Message.checkMessageID("08575975889")); //tests cell no validation
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
        String expectedMessage = "Message ready to send.";
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
        String expectedMesssage = "0000000000";
        String result = msg.createMessageID().replaceAll("\\d{10}", "0000000000");
        assertEquals(expectedMesssage, result);
    }

    
    
}
