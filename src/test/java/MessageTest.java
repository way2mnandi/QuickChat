import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
public class MessageTest {
    @Test
    void TestRecepientCellValidator(){
        assertTrue(Message.checkRecepientCell("+27718693002\r"));
        assertFalse(Message.checkMessageID("08575975889"));
    }
    @Test
    void testMessageLengthSuccess(){
        Message msg = new Message();
        String expectedMessage = "Message ready to send.";
        String result = msg.checkMessageLength("Hi Mike, can you join us for dinner tonight");
        assertEquals(expectedMessage, result);
    }
    @Test
    void testMessageLengthFailure(){
        Message msg = new Message();
        String expectedMessage = "Message exceeds 250 characters by "+10+", please reduce size";
        String result = msg.checkMessageLength("A".repeat(260));
        assertEquals(expectedMessage, result);
    }
    @Test
    void messageHashSuccess(){
        Message msg = new Message();
        String expectedMessage = String.format("00:0:HITONIGHT");
        String result = msg.createMessageHash("Hi Mike, can you join us for dinner tonight").toUpperCase().replaceAll("\\d{2}:\\d", "00:0");
        assertEquals(expectedMessage, result);
    }
    @Test
    void testMessageIDGeneration(){
        Message msg = new Message();
        String expectedMesssage = "0000000000";
        String result = msg.createMessageID().replaceAll("\\d{10}", "0000000000");
        assertEquals(expectedMesssage, result);
    }

    
    
}
