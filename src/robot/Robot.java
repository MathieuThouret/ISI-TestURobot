package robot;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static robot.Direction.*;
import static robot.Instruction.*;

public class Robot {

    private Coordinates position;
    private Direction direction;
    private boolean isLanded;
    private RoadBook roadBook;
    private Battery battery;
    private LandSensor landSensor;
    /**
     * Energie ideale consommee pour la realisation d'une action. 
     */
    private final double energyConsumption; 

    public Robot() {
        this(1.0);
    }

    public Robot(double energyConsumption) {
        isLanded = false;
        this.energyConsumption = energyConsumption;
        this.battery=new Battery();
        this.landSensor=new LandSensor(new Random());
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public LandSensor getLandSensor() {
        return landSensor;
    }

    public void setLandSensor(LandSensor landSensor) {
        this.landSensor = landSensor;
    }

    public void land(Coordinates landPosition) {
        position = landPosition;
        direction = NORTH;
        isLanded = true;
    }

    public int getXposition() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        return position.getX();
    }

    public int getYposition() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        return position.getY();
    }

    public Direction getDirection() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        return direction;
    }

    public void moveForward() throws UnlandedRobotException, InsufficientChargeException {
        if (!isLanded) throw new UnlandedRobotException();
        Coordinates nextPosition = MapTools.nextForwardPosition(position, direction);
        double energyConsumptionModulee;
        energyConsumptionModulee = landSensor.getPointToPointEnergyCoefficient(position, nextPosition)*energyConsumption;
        try {
            battery.use(battery.getChargeLevel() - energyConsumptionModulee);
        } catch (InsufficientChargeException ex) {
            Logger.getLogger(Robot.class.getName()).log(Level.SEVERE, null, ex);
            throw new InsufficientChargeException();
        }
        position=nextPosition;
    }

    public void moveBackward() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        position = MapTools.nextBackwardPosition(position, direction);
    }

    public void turnLeft() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        direction = MapTools.counterclockwise(direction);
    }

    public void turnRight() throws UnlandedRobotException {
        if (!isLanded) throw new UnlandedRobotException();
        direction = MapTools.clockwise(direction);
    }

    public void setRoadBook(RoadBook roadBook) {
        this.roadBook = roadBook;
    }

    public void letsGo() throws UnlandedRobotException, InsufficientChargeException {
        while (roadBook.hasInstruction()) {
            Instruction nextInstruction = roadBook.next();
            if (nextInstruction == FORWARD) moveForward();
            else if (nextInstruction == BACKWARD) moveBackward();
            else if (nextInstruction == TURNLEFT) turnLeft();
            else if (nextInstruction == TURNRIGHT) turnRight();
        }
    }

    public void moveTo(Coordinates destination) throws UnlandedRobotException, InsufficientChargeException {
        if (!isLanded) throw new UnlandedRobotException();
        RoadBook book = RoadBookCalculator.calculateRoadBook(direction, position, destination, new ArrayList<Instruction>());
        setRoadBook(book);
        letsGo();
    }


}