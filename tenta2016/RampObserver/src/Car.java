import java.io.Serializable;

public class Car implements Serializable {
    private String licensePlate, model;

    public Car (String plate, String mo) {
        this.licensePlate = plate;
        this.model = mo;
    }

    public String toString(){
        return new Car(licensePlate, model).toString();
    }
}
