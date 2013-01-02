package net.minecraft.src;
import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.text.*;

import net.minecraft.client.Minecraft;


public class mod_coords extends BaseMod
{       
    public mod_coords()
    {

    	ShowCoords = new KeyBinding("Show Coordinates", 25);
    	DoRun = new KeyBinding("Run", 16);
    	mcMe = ModLoader.getMinecraftInstance();

    }
    
    public static int nthOccurrence(String str, int c, int n) {
        int pos = str.indexOf(c, 0);
        while (n-- > 0 && pos != -1)
            pos = str.indexOf(c, pos+1);
        return pos;
    }
    
    public boolean onTickInGame(float f, Minecraft mc)
    {
    	Double playerX = Math.floor(mc.thePlayer.posX);
        Double playerY = Math.floor(mc.thePlayer.posY);
        Double playerZ = Math.floor(mc.thePlayer.posZ);
            
    	String x = "X: " + playerX.intValue();
    	String y = "X: " + playerY.intValue();
    	String z = "X: " + playerZ.intValue();

    	if (ShowingCoords){
	    	mc.fontRenderer.drawString(x, 2, 2, 0xffffff);
	    	mc.fontRenderer.drawString(y, 2, 12, 0xffffff);
	    	mc.fontRenderer.drawString(z, 2, 22, 0xffffff);
	    	mc.fontRenderer.drawString(mcMe.thePlayer.username, 2, 32, 0xffffff);
	    	mc.fontRenderer.drawString("Hit: " + mcMe.thePlayer.lastDamage, 2, 42, 0xffffff);
	    	mc.fontRenderer.drawString("Wall Kills: " + playerKills, 2, 52, 0xffffff);
	    	
			DecimalFormat df = new DecimalFormat("#.##");
	    	mc.fontRenderer.drawString("Walls K:D: "+df.format(playerDK), 2, 62, 0xffffff);
	    					
	    	
    	}    
    	
        return true;
    }

    public void keyboardEvent(KeyBinding event)
    {
    	

    	try{    	
    		// 
    		URL playerStatsURL = new URL("http://w.lcga.me/walls_api2.php?stats=glennEboy");
//    		URL playerStatsURL = new URL("http://w.lcga.me/walls_api2.php?stats="+mcMe.thePlayer.username);
    		URLConnection myConnection = playerStatsURL.openConnection();
            BufferedReader statsStreamIn = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
			String inputLine;

			while ((inputLine = statsStreamIn.readLine()) != null){ 
				playerStats = inputLine;
				System.out.println("--------------> pstats    -" + playerStats+"-");
			}
			
			// ["0","170","91","2876","121","22"]

			if (playerStats.indexOf(",") > 0 ){
			
				System.out.println("testtttttttttt ----> "+ (playerStats.indexOf(",",0)+2));
	            System.out.println("length ----> "+ playerStats.length());
	            //System.out.println("-------------->    1"+inputLine.indexOf(32,0));
	            
				int indexOfKills = playerStats.indexOf(",")+2;
				playerKills = playerStats.substring(indexOfKills,(playerStats.indexOf(",",indexOfKills)-1));
				
				System.out.println("Kills-------------->    "+playerStats.substring(indexOfKills,(playerStats.indexOf(",",indexOfKills)-1)));
	
				int indexOfDeaths = playerStats.indexOf(",", indexOfKills)+2;
			
				playerDeaths = playerStats.substring(indexOfDeaths,(playerStats.indexOf(",",indexOfDeaths)-1));

				System.out.println("Deaths-------------->    "+playerStats.substring(indexOfDeaths,(playerStats.indexOf(",",indexOfDeaths)-1)));

		        playerDK = Double.valueOf(playerKills)/Double.valueOf(playerDeaths);
			}else{
				playerKills = "0";
			}
			statsStreamIn.close();
    	}catch (IOException ioe){
    		System.err.println("Caught IOException: " + ioe.getMessage());
    	}
    	
    	// if its P - 25 key for coordinates
    	System.out.println("keycode " + event.keyCode);
    	if (event.keyCode == 25){
    		if (!ShowingCoords) {
    			ShowingCoords = true;
    		}else{
    			ShowingCoords = false;
    		}
    	}
    	mcMe.thePlayer.setSprinting(true);
    }

    public String getVersion()
    {
        return "1.0.1";
    }

    public void load()
    {
        ModLoader.setInGameHook(this, true, false);
        ModLoader.registerKey(this, ShowCoords, false);
        ModLoader.registerKey(this, DoRun, false);
        ModLoader.addLocalization("ShowCoords", "Show Coordinates");
    }

    static String playerKills, playerDeaths;
    static Double playerDK;
    static String playerStats;
    static double playerX;
    static double playerY;
    static double playerZ;
    private KeyBinding ShowCoords, DoRun;
    private boolean ShowingCoords;
    protected net.minecraft.src.FontRenderer fr;
    private Minecraft mcMe;
}


