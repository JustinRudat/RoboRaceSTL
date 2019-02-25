package algorithms;

import robotsimulator.Brain;

public class Stage2 extends Brain {
    //---PARAMETERS---//
    private static final double HEADINGPRECISION = 0.001;
    private static final double ANGLEPRECISION = 0.1;
    private static final int ROCKY = 0x1EADDA;
    private static final int CARREFOUR = 0x5EC0;
    private static final int DARTY = 0x333;
    private static final int UNDEFINED = 0xBADC0DE;

    //---VARIABLES---//
    private boolean turnNorthTask,turnLeftTask;
    private double endTaskDirection;
    private double myX,myY;
    private boolean isMoving;
    private int whoAmI;

    @Override
    public void activate() {

    }

    @Override
    public void step() {

    }
}
