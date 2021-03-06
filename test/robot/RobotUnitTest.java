package robot;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;
import static org.mockito.Matchers.anyDouble;
import org.mockito.Mockito;


import static robot.Direction.WEST;
import static robot.Direction.EAST;


public class RobotUnitTest {

    @Test
    public void testLand() throws UnlandedRobotException {
        Robot robot = new Robot();
        robot.land(new Coordinates(3,0));
        assertEquals(3, robot.getXposition());
        assertEquals(0, robot.getYposition());
    }

    @Test(expected = UnlandedRobotException.class)
    public void testRobotMustBeLandedBeforeAnyMove() throws Exception {
        Robot robot = new Robot();
        robot.moveForward();
    }

    @Test
    public void testMoveForward() throws UnlandedRobotException, InsufficientChargeException {
        Robot robot = new Robot();
        robot.land(new Coordinates(3, 0));
        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();
        robot.moveForward();
        assertEquals(currentXposition, robot.getXposition());
        assertEquals(currentYposition+1, robot.getYposition());

    }

    @Test
    public void testMoveBackward() throws UnlandedRobotException {
        Robot robot = new Robot();
        robot.land(new Coordinates(3,0));
        int currentXposition = robot.getXposition();
        int currentYposition = robot.getYposition();
        robot.moveBackward();
        assertEquals(currentXposition, robot.getXposition());
        assertEquals(currentYposition - 1, robot.getYposition());
    }

    @Test
    public void testTurnLeft() throws UnlandedRobotException {
        Robot robot = new Robot();
        robot.land(new Coordinates(3, 0));
        robot.turnLeft();
        assertEquals(WEST, robot.getDirection());
    }

    @Test
    public void testTurnRight() throws UnlandedRobotException {
        Robot robot = new Robot();
        robot.land(new Coordinates(3, 0));
        robot.turnRight();
        assertEquals(EAST, robot.getDirection());
    }

    @Test
    public void testFollowInstruction() throws UnlandedRobotException, InsufficientChargeException {
        Robot robot = new Robot();
        robot.land(new Coordinates(5, 7));
        robot.setRoadBook(new RoadBook(Arrays.asList(Instruction.FORWARD, Instruction.FORWARD, Instruction.TURNLEFT, Instruction.FORWARD)));
        robot.letsGo();
        assertEquals(4, robot.getXposition());
        assertEquals(9, robot.getYposition());
    }
    
    @Test (expected = InsufficientChargeException.class)
    public void testNewMoveForwardFail() throws InsufficientChargeException, UnlandedRobotException {
        Robot robot = new Robot();
        robot.land(new Coordinates(5, 7));        
        Battery mockBattery = Mockito.mock(Battery.class);
        Mockito.doThrow(InsufficientChargeException.class).when(mockBattery).use(anyDouble());
        robot.setBattery(mockBattery);
        robot.moveForward();
    }
}
