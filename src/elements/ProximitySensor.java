package src.elements;
/**
 * The {@code ProximitySensor} class represents an ProximitySensor object. This sensor is able to inform
 * the user if their is anything that is within the sensors proximity with a range of x meters.
 *
 * @author Philippe Vo
 */
public class ProximitySensor extends Sensor {

    private boolean objectDetected = false;
    private boolean obstacleFlag = false;

    public ProximitySensor() {
    }

    /**
     * Detects if there are any obstacles
     */
    public boolean detect() {
        if(this.obstacleFlag){
            objectDetected = true;
        }
        else{
            objectDetected = false;
        }
        return objectDetected;
    }

    /**
     * Allows to set if there is an obstacle in front of sensor
     * @param obstacle
     */
    public boolean setObstacle(boolean obstacle) {
        if(obstacle){
            this.obstacleFlag = true;
        }
        else{
            this.obstacleFlag = false;
        }

        return this.obstacleFlag;
    }
}