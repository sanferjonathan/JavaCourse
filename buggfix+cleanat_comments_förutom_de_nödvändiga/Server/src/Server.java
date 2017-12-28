

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.io.*;
import javafx.application.Application;


public class Server implements Runnable
{
	private ServerSocket server;
	private Socket anslutning;
	public Integer antalklienter = 0;
	private Thread Trad;
	private String tradNamn;
	private Integer timeStamp = 0;
	long pastTime = 0L;
	private Integer spawnedBluePedestrians = 1;
	private Integer spawnedRedPedestrians = 1;

	ArrayList<ServerClient> klientLista = new ArrayList<ServerClient>();
	List<Pedestrian> pedestrianList = null;

	public String meddelande = new String("");

	Server(String name)
	{
		this.pedestrianList = Main.GetPedestrianList();
		tradNamn = name;
		System.out.println("Creating " +  tradNamn );
	}

	public void onUpdate(){
		long currTime = System.currentTimeMillis();
		if(currTime >= (pastTime + Main.GetSpawnRate())) {
			this.spawnCheck();
			pastTime = currTime + Main.GetSpawnRate();
		}
	}

	public void spawnCheck(){
		Double blueRandY = Math.random() * Main.GetCanvasY();
		Double redRandY = Math.random() * Main.GetCanvasY();
		Pedestrian p1 = new Pedestrian(1, 100.0, blueRandY);
		Pedestrian p2 = new Pedestrian(0, Main.GetCanvasX() - 100.0, redRandY);

		boolean pCollide = false;
		boolean p2Collide = false;
		synchronized(this.pedestrianList) {
			for(Pedestrian p : this.pedestrianList)
			{

				if (p1.isColliding(p))
				{
					pCollide = true;
				}
				if(p2.isColliding(p)) {
					p2Collide = true;
				}
				if(pCollide || p2Collide)
					break;
			}
		}

		if(!pCollide)
		{

			if(this.spawnedBluePedestrians <= Main.GetMaxBluePedestrians() && this.spawnedBluePedestrians <= this.spawnedRedPedestrians) {
				synchronized(this.pedestrianList) {
					this.pedestrianList.add(new Pedestrian(p1.id, p1.xCenter, p1.yCenter));
					this.spawnedBluePedestrians++;
				}
			}


		}

		if(!p2Collide)
		{
			if(this.spawnedRedPedestrians <= Main.GetMaxRedPedestrians() && this.spawnedRedPedestrians <= this.spawnedBluePedestrians) {
				synchronized(this.pedestrianList) {
					this.pedestrianList.add(new Pedestrian(p2.id, p2.xCenter, p2.yCenter));
					this.spawnedRedPedestrians++;
				}
			}
		}


	}

	public void goalCheck(){
		synchronized(this.pedestrianList) {
			for(Pedestrian pedestrian : this.pedestrianList){
				if(pedestrian.getXCenter() > Main.GetCanvasX()-50.0 || pedestrian.getXCenter() < 50){
					pedestrian.kill();
					System.out.println("hit! goalCheck()");
					if(Main.GetGoalCounter() < (Main.GetMaxBluePedestrians()+Main.GetMaxBluePedestrians()) )
						Main.IncGoalCounter();
				}
			}
		}
	}

	public void collisionCheck(){
		synchronized(this.pedestrianList) {
			for(int i = 0; i < this.pedestrianList.size(); i++){
				for(int j = 0; j < this.pedestrianList.size(); j++){
					if(i != j) {
						if(this.pedestrianList.get(j).alive) {
							if (this.pedestrianList.get(i).isColliding(this.pedestrianList.get(j))) {
								this.pedestrianList.get(i).kill();
								this.pedestrianList.get(j).kill();
								Main.IncCollisionCounter();
							}
						}
					}
				}
			}
		}
	}

	public void removePedestrian(){
		synchronized(this.pedestrianList) {
			Iterator<Pedestrian> iterator = this.pedestrianList.iterator();
			while (iterator.hasNext()){
				if(!iterator.next().alive){
					iterator.remove();
				}
			}
		}
	}


	public void run()
	{
		try {
			Listen();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void start ()
	{
		if (Trad == null)
		{
			Trad = new Thread (this, tradNamn);
			Trad.start ();
		}
	}

	public void Listen() throws IOException, InterruptedException
	{
		this.server = new ServerSocket(1337,5);
		this.server.setReuseAddress(true);
		System.out.println("B�rjar v�nta p� klienter");

		this.VantaPaAnslutning();
		spawnCheck();
		while(true)
		{
			System.out.println("");
			if(Main.GetRun())
			{
				Thread.sleep(40);
				this.Broadcast();
				Thread.sleep(40);
				this.VantaPaSvar();
				this.UppdateraData();
				this.goalCheck();
				this.collisionCheck();
				this.removePedestrian();
				this.onUpdate();
				Main.SetTimeSteps(++this.timeStamp);
				System.out.println("");
			}
		}
	}

	public void VantaPaSvar() throws IOException, InterruptedException
	{
		System.out.println("VantaPaSvar()");
		Integer antalSvar = 0;
		while(antalSvar < this.antalklienter)
		{
			antalSvar = 0;
			for(ServerClient klient : this.klientLista)
			{

				if(!klient.pedestrianList.isEmpty())
				{
					antalSvar++;
				}
			}
		}
	}


	public void VantaPaAnslutning() throws IOException
	{
		while(this.antalklienter < 2)
		{
			this.anslutning = server.accept();
			this.antalklienter++;
			String namn = "TradNr"+antalklienter.toString();
			ServerClient e = new ServerClient(anslutning, namn, this.antalklienter-1);
			new Thread(e).start();
			this.klientLista.add(e);
		}
	}

	private String GeneratePedestrianList() throws IOException
	{
		String pedestrianList = "";

		while(pedestrianList == "" && !this.pedestrianList.isEmpty()) {
			synchronized(this.pedestrianList)
			{

				Iterator<Pedestrian> iterator = this.pedestrianList.iterator();
				while (iterator.hasNext())
				{
					Pedestrian p = iterator.next();
					pedestrianList += p.id.toString() + ";" + p.xCenter.toString() + ";" + p.yCenter.toString() + "/";
				}
			}
		}
		return pedestrianList;
	}

	private void Broadcast() throws IOException
	{
		System.out.println("Broadcast()");
		String pedestrians = GeneratePedestrianList();
		for(ServerClient klient : this.klientLista)
		{
			klient.SkickaTillKlient(pedestrians);
		}
	}


	private void UppdateraData() throws IOException
			, InterruptedException
	{
		Thread.sleep(10);
		System.out.println("UppdateraData(): pedestrianList data: " + this.pedestrianList);
		this.pedestrianList.clear();
		for(ServerClient Serverklient : this.klientLista)
		{
			synchronized(Serverklient.pedestrianList) {
				if(!Serverklient.pedestrianList.isEmpty())
				{
					for(Pedestrian Pedestrian : Serverklient.pedestrianList)
					{
						Pedestrian ped = new Pedestrian(Pedestrian.id, Pedestrian.xCenter, Pedestrian.yCenter);
						Main.GetPedestrianList().add(ped);
					}
				}
			}
		}
	}
}





