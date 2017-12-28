import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class ServerClient implements Runnable {
    protected Socket anslutning = null;
    String namn = null;
    Integer id = null;
    private Thread Trad;

    DataInputStream din = null;
    OutputStream outToClient = null;
    DataOutputStream out = null;
	List<Pedestrian> pedestrianList = Collections.synchronizedList(new ArrayList<Pedestrian>());

    public ServerClient(Socket anslutning, String namn, int id) throws IOException {
        this.anslutning = anslutning;
        this.namn = namn;
        this.id = id;

        this.din = new DataInputStream(anslutning.getInputStream());
        this.outToClient = anslutning.getOutputStream();
        this.out = new DataOutputStream(outToClient);
    }
    
    public void SkickaTillKlient(String meddelande) throws IOException {
        this.out.writeUTF(meddelande);
    }
    
    public boolean isNumeric(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }

    public void UpdatePedestrianList(String[] pedestriansInfoList) throws IOException {
    	if(pedestriansInfoList != null && pedestriansInfoList.length > 0 && pedestriansInfoList[0] != "")
    	{
    		List<Pedestrian> tempList = Collections.synchronizedList(new ArrayList<Pedestrian>());
	        for (String line: pedestriansInfoList)
	        {

	            String[] vars = line.split(";");
	            if(vars.length > 1)//1
	            {
	            if(vars[0] != "" && vars[1] != "" && vars[2] != "" && isNumeric(vars[0]) && isNumeric(vars[1]) && isNumeric(vars[2]) )
	            {
	            Pedestrian p = new Pedestrian(Integer.parseInt(vars[0]), Double.parseDouble(vars[1]), Double.parseDouble(vars[2]));
	            synchronized(this.pedestrianList) {
					 tempList.add(p);
	            }
	            }
	            }
	        }
	        if(tempList.size() >= 1)
	        {
	        	this.pedestrianList.clear();
	        	this.pedestrianList = tempList;
	        }
    	}
    }

    public String[] TaEmotFranKlient() throws IOException {
        String[] empty = null;
        String line;
        line = din.readUTF();
        if (line == "done" || line == "") {
        	this.pedestrianList.clear();
            return empty;
        } 
        else 
        {
            String[] pedestriansInfoList; 
            pedestriansInfoList = line.split("/");
            return pedestriansInfoList;
        }
    }
    
    
    public void Listen() throws IOException
    {
    	this.SkickaTillKlient("id" + this.id.toString());
        while(true)
        {
            String[] pedestriansInfoList = null;
            while (pedestriansInfoList == null)
            {
                pedestriansInfoList = this.TaEmotFranKlient();
                if(pedestriansInfoList != null)
                {
	                this.UpdatePedestrianList(pedestriansInfoList);
	                pedestriansInfoList = null;
	                break;
                }
            }
        }
    }


    public void run()
    {
    	try
    	{
			this.Listen();
		}
    	catch (IOException e)
    	{
			e.printStackTrace();
		}
    }

    public void start() {
        System.out.println("Starting " + namn);
        if (Trad == null) {
            Trad = new Thread(this, namn);
            Trad.start();
        }
    }
}