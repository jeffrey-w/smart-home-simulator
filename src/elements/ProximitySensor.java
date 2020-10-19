package elements;
/**
 * The {@code ProximitySensor} class represents a ProximitySensor object. This sensor is able to inform
 * the user if there is anything that is within the sensor's proximity with a range of x meters.
 *
 * @author Philippe Vo
 */
public class ProximitySensor extends Sensor {

    private boolean objectDetected = false;
    private boolean obstacleFlag = false;

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
     * Sets the reading this {@code ProximitySensor} shall return when queried.
     *
     * @param obstacle if {@code true} this {@code ProximitySensor} will indicated an obstruction
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