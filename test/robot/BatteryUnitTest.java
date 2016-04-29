package robot;

import static org.junit.Assert.*;
import org.junit.Test;


public class BatteryUnitTest {

    @Test
    public void testCharge() {
        Battery cell = new Battery();
        assertEquals(100f, cell.getChargeLevel(),0);
        cell.charge();
        assertEquals(111f, cell.getChargeLevel(),0);
    }

    @Test
    public void testUseOK() throws InsufficientChargeException{
        Battery cell = new Battery();
        assertEquals(100f, cell.getChargeLevel(),0);
        cell.use(10.0);
        assertEquals(90f, cell.getChargeLevel(), 0);
    }
  
    @Test (expected = InsufficientChargeException.class)
    public void testUseFail() throws InsufficientChargeException {
        Battery cell = new Battery();
        cell.use(110.0);
    }
    
    @Test
    public void testTimeSufficientExactly(){
        Battery cell = new Battery();
        long time = cell.timeToSufficientCharge(111.0);
        assertEquals(time, 1000);
    }
    
    @Test
    public void testTimeSufficientRounded(){
        Battery cell = new Battery();
        long time = cell.timeToSufficientCharge(125.2);
        assertEquals(time, 3000);
    }
    
    @Test
    public void testTimeSufficientNegative(){
        Battery cell = new Battery();
        long time = cell.timeToSufficientCharge(-125.2);
        assertEquals(time, 0);
    }
}