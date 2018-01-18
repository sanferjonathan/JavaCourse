import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class RampObserver {

    private CO2Sensor mySensor;
    private Car currentCar;
    private Socket currentSocket;
    private InetAddress serverAddress;
    public static final int PORT = 2000;
    //4. YOUR CODE HERE(A)
    // the executor service may be here or locally in MeasurementProcess


    static private CO2Sensor identifyMySensor() {
        return new CO2Sensor(100);
    }

    private String identifyPlate() {
        return "BLA123";
    }

    private String identifyModel() {
        return "VW Golf";
    }

    // the constructor sets and initializes the sensor,
    // and all the other information that the RampObserver needs
    // for setting up the connection to the TestManager
    public RampObserver (CO2Sensor sensor, String serverAddressString)
            throws UnknownHostException {
            this.mySensor = sensor;
            this.serverAddress.getByName(serverAddressString);
    }

    // the following should be a nested non-static class that implements the callable
    // interface. It implements the MeasurementProcess that is controlled by
    //RampObserver Client.
    private class MeasurementProcess implements Callable {
        private Measurements measuredData;
        private int noSamples;

         private MeasurementProcess (int no){
             noSamples = no;
             measuredData = new Measurements(currentCar);
         }

        // This method is called when the process is active. It collects sensor
        // values and stores them in a Measurement object, which it returns.
        //2. YOUR CODE HERE(B)

        // this method is called when a new car is detected. It creates a new Car instance,
        // establishes a connection to the server and creates the appropriate readers and
        // writers.
        // it sends the Car object to the TestManager and receives a value relevant for
        // sensor configuring. After starting the MeasurementProcess is finished, it takes
        // the results and sends the Measurements to the server.
        public void startNewCarTest(String licencePlate, String model) throws IOException {
            currentCar = new Car(licencePlate, model);
            currentSocket = new Socket(serverAddress, PORT);

            OutputStream out = currentSocket.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer = new BufferedWriter(writer);

            InputStream in = currentSocket.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in, "UTF-8"));


            //3. YOUR CODE HERE(C)
            while (true) {
                String inline = reader.readLine();
                if(inline == null){
                    writer.close();
                    break;
                }
                int noSamples = 100;

                try {
                    noSamples = Integer.parseInt(inline);
                    writer.write(noSamples);
                } catch (NumberFormatException e) {
                    System.out.println("Sent sensor configuration is not a number!");
                }

                Measurements results = triggerMeasurementProcess(this, noSamples);
                //3. YOUR CODE HERE(D)
            }
        }
        // this method creates a MeasurementProcess instance and manages it. It saves
        // the returned Measurements and handles all steps around the callable process such
        // submitting it to a executorservice.
        // It returns the Measurements Object.
        public Measurements triggerMeasurementProcess(RampObserver ramp, int noSamples) {

            //4. YOUR CODE HERE(B)
            Measurements bla = new Measurements(currentCar);
            try {
                //4. YOUR CODE HERE(C)
            }
            catch (InterruptedException e) {
                System.out.println("Ups, something interrupted me, while waiting...");
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }
            //4. YOUR CODE HERE(D)
            return bla;
        }

        @Override
        public Object call() throws Exception {
            return null;
        }
    }

    public static void main(String[] args) {
        RampObserver testsuite;
        try {
            testsuite = new RampObserver(
                    RampObserver.identifyMySensor(), "CO2Test.se");
            while (true) {
                try {
                    testsuite.startNewCarTest(testsuite.identifyPlate(),
                            testsuite.identifyModel());
                }
                catch (Exception e) {
                    System.out.println("hm....");
                }
            }
        }
        catch (UnknownHostException e1) {
            e1.printStackTrace();
        }
    }
}
