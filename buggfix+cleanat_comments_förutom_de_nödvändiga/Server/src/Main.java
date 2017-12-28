import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main
{
	
	public static List<Pedestrian> pedestrianList = Collections.synchronizedList(new ArrayList<Pedestrian>());
	public static Integer collisionCounter = 0;
	public static Integer goalCounter = 0;
	public static boolean run = false;
    public static Integer canvasY = 400;
    public static Integer canvasX = 1200;
    public static Integer radie = 20; //diameter
    public static Integer timeSteps = 0;
    public static Integer spawnRate = 1500;
	public static Integer maxBluePedestrians = 10;
	public static Integer maxRedPedestrians = 10;
	
	
    public static Integer GetMaxBluePedestrians()
    {
    	return maxBluePedestrians;
    }
    
    public static Integer GetMaxRedPedestrians()
    {
    	return maxRedPedestrians;
    }	
	
    
    public static Integer GetSpawnRate()
    {
    	return spawnRate;
    }
    
    public static void SetSpawnRate(Integer t)
    {
    	timeSteps = t;
    }	
    
    public static Integer GetTimeSteps()
    {
    	return timeSteps;
    }
    
    public static void SetTimeSteps(Integer t)
    {
    	timeSteps = t;
    }
    
    public static Integer GetRadie()
    {
    	return radie;
    }
    
    public static void SetRadie(Integer r)
    {
    	radie = r;
    }
    
    public static Integer GetCanvasY()
    {
    	return canvasY;
    }
    
    public static void SetCanvasY(Integer canvas)
    {
    	canvasY = canvas;
    }
    
    public static Integer GetCanvasX()
    {
    	return canvasX;
    }
    
    public static void SetCanvasX(Integer canvas)
    {
    	canvasX = canvas;
    }
    
	
	public static void ToggleRun()
	{
		if(run == true)
			run = false;
		else
			run = true;
		System.out.println("Toggled!!!!!!!!!");
	}
	public static boolean GetRun()
	{
		return run;
	}
	
	public static List<Pedestrian> GetPedestrianList()
	{
		return Main.pedestrianList;
	}
	
	public static Integer GetCollisionCounter()
	{
		return collisionCounter;
	}
	public static void IncCollisionCounter()
	{
		collisionCounter++;
	}
	public static Integer GetGoalCounter()
	{
		return goalCounter;
	}
	public static void IncGoalCounter()
	{
		goalCounter++;
	}

    public static void main(String[] args)
    {
	
        try 
        {
            javafx.application.Application.launch(GUI.class);

        }
        finally
        {

        }
    }
}