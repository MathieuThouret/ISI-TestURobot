/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robot;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author mathieu
 */
public class LandSensorTest {
    
    @Test
    public void testDistanceOK(){
        LandSensor sensor = new LandSensor(new Random());
        Coordinates a = new Coordinates(0,0);
        Coordinates b = new Coordinates(4,3);
        double dist = sensor.distance(a,b);
        assertEquals(5.0, dist, 0);
    }
    
    @Test
    public void testEnergyCoefficient(){
        Coordinates a = new Coordinates(0,0);
        Coordinates b = new Coordinates(4,3);
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextDouble()).thenReturn(1.0);
        LandSensor sensor = new LandSensor(mockRandom);
        assertEquals(2.0, sensor.getPointToPointEnergyCoefficient(a, b), 0.0);
    }
}