package algorithms;

import characteristics.IFrontSensorResult;
import characteristics.IRadarResult;
import characteristics.Parameters;
import robotsimulator.Brain;

public class Stage3 extends Brain {
    //---PARAMETERS---//
    private static final double HEADINGPRECISION = 0.001;
    private static final double ANGLEPRECISION = 0.1;
    private static final int ROCKY = 0x1EADDA;
    private static final int CARREFOUR = 0x5EC0;
    private static final int DARTY = 0x333;
    private static final int UNDEFINED = 0xBADC0DE;

    private static int indice = 0;
    private int id = 0;

    //---VARIABLES---//

    private double myX,myY;
    private int whoAmI;

    private boolean isSouth;

    private boolean firstTurn;
    private boolean secondTurn;
    private boolean thirdTurn;
    private int count = 21;


    //---CONSTRUCTORS---//
    public Stage3() {
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
        firstTurn = false;
        secondTurn = false;
        thirdTurn = false;

        isSouth=false;


    }

    public void step() {
        if (whoAmI == ROCKY) {
            if (!isSouth) {
                if (!isDirection(Parameters.SOUTH)) {
                    stepTurn(Parameters.Direction.RIGHT);
                } else {
                    if (detectFront().getObjectType() == IFrontSensorResult.Types.WALL) {
                        isSouth = true;
                    } else {
                        myMove();
                    }
                }
            } else {

                if (myX < 750) {
                    if(!isDirection(Parameters.EAST)){
                        stepTurn(Parameters.Direction.LEFT);
                    } else {
                        myMove();
                        myX++;
                    }
                } else
                if (myX == 750){
                    if (!firstTurn){
                        if (count<=0 && isDirection(Parameters.EAST)){
                            firstTurn = true;
                            count=21;
                        } else {
                            stepTurn(Parameters.Direction.LEFT);
                            count--;
                        }
                    } else {
                        myMove();
                        myX++;
                    }
                }else
                if (myX < 1500) {
                    myMove();
                    myX++;
                } else
                if (myX == 1500){
                    if (!secondTurn){
                        if (count<=0 && isDirection(Parameters.EAST)){
                            secondTurn = true;
                            count=21;
                        } else {
                            stepTurn(Parameters.Direction.LEFT);
                            count--;
                        }
                    } else {
                        myMove();
                        myX++;
                    }
                }else
                if (myX < 2250) {
                    myMove();
                    myX++;
                } else
                if (myX == 2250){
                    if (!thirdTurn){
                        if (count<=0 && isDirection(Parameters.EAST)){
                            thirdTurn = true;
                            count=21;
                        } else {
                            stepTurn(Parameters.Direction.LEFT);
                            count--;
                        }
                    } else {
                        myMove();
                        myX++;
                    }
                } else
                if (firstTurn && secondTurn && thirdTurn){
                    sendLogMessage("Everybody was kung-fu fightiiiiiiiin'");
                }

            }
        }
    }

    private void myMove(){
        move();
    }
    private boolean isHeading(double dir){
        return Math.abs(Math.sin(getHeading()-dir))<HEADINGPRECISION;
    }
    private boolean isDirection(double dir) {
        return isSameDirection(modulePI(this.getHeading()),dir);
    }

    private double modulePI(double dir){
        if(dir > 2*Math.PI){
            while(dir > 2 * Math.PI)
                dir = dir - (2 * Math.PI);
        } else if ( dir < 0 ) {
            while (dir < 0)
                dir = dir + (2 * Math.PI);
        }
        return dir;
    }
    private boolean isSameDirection(double dir1, double dir2){
        return Math.abs(dir1-dir2)<=ANGLEPRECISION;
    }
}
