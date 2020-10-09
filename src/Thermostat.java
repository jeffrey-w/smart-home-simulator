public class Thermostat extends Sensor {
    private double temprature;
    public double getTemprature(){
        return temprature;
    }
    public void updateTemprature(double temperature){
        this.temprature = temperature;
    }
}
