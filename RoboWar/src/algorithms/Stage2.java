package algorithms;

import characteristics.IFrontSensorResult;
import characteristics.IRadarResult;
import characteristics.Parameters;
import robotsimulator.Brain;

public class Stage2 extends Brain {
    //---PARAMETERS---//
    private static final double HEADINGPRECISION = 0.001;
    private static final double ANGLEPRECISION = 0.001;
    private static final int ROCKY = 0x1EADDA;
    private static final int CARREFOUR = 0x5EC0;
    private static final int DARTY = 0x333;
    private static final int UNDEFINED = 0xBADC0DE;
    private double dimX ;
    private double dimY ;
    private static int indice = 0;
    private int id = 0;

    //---VARIABLES---//
    private boolean turnNorthTask,turnLeftTask, turnRightTask;
    private double endTaskDirection;
    private double myX,myY;
    private boolean isMoving;
    private int whoAmI;
    private boolean stopped;
    private boolean endedX;
    private boolean endedY;


    //---CONSTRUCTORS---//
    public Stage2() {
        super();

    }


    public double getSecBotRange() {
        return Parameters.teamASecondaryBotFrontalDetectionRange;
    }

    public double getMainBotRange() {
        return Parameters.teamAMainBotFrontalDetectionRange;
    }
    //---ABSTRACT-METHODS-IMPLEMENTATION---//
    public void activate() {
        //ODOMETRY CODE
        id = indice;
        indice++;
        if(id ==2) {
            whoAmI = ROCKY;
        } else {
            whoAmI = UNDEFINED;
        }

//        for (IRadarResult o: detectRadar())
//            if (isSameDirection(o.getObjectDirection(),Parameters.NORTH)) whoAmI=UNDEFINED;
        if (whoAmI == ROCKY){
            myX=Parameters.teamAMainBot3InitX;
            myY=Parameters.teamAMainBot3InitY;
        } else {
            myX=0;
            myY=0;
        }

        //INIT
        turnNorthTask=false;
        turnLeftTask=false;
        isMoving=false;
        endedX=false;
        endedY=false;
        stopped = false;
    }
    public void step() {
        if (whoAmI == ROCKY) {
            if (!endedY) {
                if (!isDirection(Parameters.SOUTH)) {
                    turnRightTask = true;
                    stepTurn(Parameters.Direction.RIGHT);
                } else {
                    if (detectFront().getObjectType() == IFrontSensorResult.Types.WALL) {
                        endedY = true;
                        dimY = myY + getMainBotRange() - 1;
                    } else {
                        myMove();
                        myY++;
                    }
                }
            } else
            if (!endedX) {
                if (!isDirection(Parameters.EAST)) {
                    turnLeftTask = true;
                    stepTurn(Parameters.Direction.LEFT);
                } else {
                    if (detectFront().getObjectType() == IFrontSensorResult.Types.WALL) {
                        endedX = true;
                        this.dimX = myX + getMainBotRange() - 1;
                    } else {
                        myMove();
                        myX++;
                    }
                }
            } else
            if (endedX && endedY && !stopped) {
                System.out.println("X range  = " + dimX);
                System.out.println("Y range  = " + dimY);
                stopped = true;
            }
        }
    }

    private void myMove(){
        isMoving=true;
        move();
    }
    private boolean isHeading(double dir){
        return Math.abs(Math.sin(getHeading()-dir))<HEADINGPRECISION;
    }
    private boolean isDirection(double dir) {
        return isSameDirection(this.getHeading(),dir);
    }
    private boolean isSameDirection(double dir1, double dir2){
        return Math.abs(dir1-dir2)<=ANGLEPRECISION;
    }
}
