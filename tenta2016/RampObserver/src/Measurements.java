import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class Measurements implements Serializable {
    private Car forCar;
    private HashMap<Date, Double> data = new HashMap<>();
    private double cachedAverage;

    public Measurements (Car theCar) {
        this.forCar = theCar;
    }

    public double getAverageSampleValue() {
        this.cachedAverage = 0;
        this.data.forEach((k,v) -> cachedAverage += v);
        this.cachedAverage /= data.size();
        return this.cachedAverage;
    }

    public void addValue(double val) {
        Calendar myCalendar =
                new GregorianCalendar(2018, 1, 07);
        Date myDate = myCalendar.getTime();
        this.data.put(myDate, val);
    }
}
