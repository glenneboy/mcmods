package net.minecraft.src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.minecraft.client.Minecraft;

public class mod_TestCustomPacket extends BaseMod{
    

	private static final File gmDirectory = new File(Minecraft.getMinecraftDir(), "/Minecade_GM/");
	private static final File gmLog = new File(gmDirectory, "Minecade_250Packets.txt");

	
    public mod_TestCustomPacket() 
    {
    }

    public String getName() 
    {
        return "mod_TestCustomPacket";
    }

    public String getVersion() 
    {
        return "0.1";
    }

    public void load() 
    {
        ModLoader.setInGameHook(this, true, false);
        ModLoader.registerPacketChannel(this, "scb");
        ModLoader.registerPacketChannel(this, "walls");

    }


    
    // ModLoader @ MC 1.3+
    public void clientCustomPayload(NetClientHandler clientHandler, Packet250CustomPayload packet250custompayload)
    {
    	this.storeAction("CLIENT Packet 250 received!!");

    }
    
    
    public void serverCustomPayload(NetServerHandler clientHandler, Packet250CustomPayload packet250custompayload){
			
    	this.storeAction("SERVER Packet 250 received!!");
    	
    }
    

    public static void sendMessage(String channel, String message, NetClientHandler netclienthandler) {
        Packet250CustomPayload plugin = new Packet250CustomPayload();
        plugin.channel = channel;
        plugin.data = message.getBytes();
        plugin.length = message.length();

		netclienthandler.addToSendQueue(plugin);
    }

    
    // ModLoader @ MC 1.2.5
    public void serverConnect(NetClientHandler netclienthandler) {
        sendMessage("scb", "REGISTER", netclienthandler);
        sendMessage("walls", "REGISTER", netclienthandler);
    }

    // ModLoader @ MC 1.3+
    public void clientConnect(NetClientHandler netclienthandler) {
        serverConnect(netclienthandler);
    }

    
    /**
     * store the action the GM took.....
     * @throws IOException
     */
    public void storeAction(String aAction){

        try {
        	this.gmDirectory.mkdir();

            if (gmLog.exists() || gmLog.createNewFile())
            {
            
        	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(gmLog, true)));
        	    out.println(aAction);
        	    out.close();
            }
            
          
        }catch (IOException ioe){
        	
        }
    	
    }
       
}