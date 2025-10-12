import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
public class MessageTest {
    @Test
    void testValidRecepientNumber(){
        assertTrue(Message.checkRecepientCell("+27718693002"), "valid recepient phone number should return true");
    }
    
}
