package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class mod_TestCustomPacket extends BaseMod{
    

    public mod_TestCustomPacket() 
    {
    }

    public String getName() 
    {
        return "scb";
    }

    public String getVersion() 
    {
        return "1.0";
    }

    public void load() 
    {
        ModLoader.setInGameHook(this, true, false);
        ModLoader.registerPacketChannel(this, "scb");
        ModLoader.registerPacketChannel(this, "walls");

    }
    
    public boolean onTickInGame(float f, Minecraft mc)
    {

        return true;
    }
    

    public static void sendMessage(String channel, String message, NetClientHandler netclienthandler) {
        Packet250CustomPayload plugin = new Packet250CustomPayload();
        plugin.channel = channel;
        plugin.data = message.getBytes();
        plugin.length = message.length();

		netclienthandler.addToSendQueue(plugin);
    }

    
    // ModLoader @ MC 1.2.5
    public void receiveCustomPacket(Packet250CustomPayload packet250custompayload)
    {
        if (packet250custompayload.channel.equalsIgnoreCase("scb"))
        {
            handleMCMessage(packet250custompayload.data);
        }else if (packet250custompayload.channel.equalsIgnoreCase("walls"))
        {
            handleMCMessage(packet250custompayload.data);
        } 
    }
    
    // ModLoader @ MC 1.3+
    public void clientCustomPayload(NetClientHandler clientHandler, Packet250CustomPayload packet250custompayload)
    {
        receiveCustomPacket(packet250custompayload);
    }
    
    
    private void handleMCMessage(byte[] message)
    {
        System.out.println("Custom Packet Received: " + message[0]);
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

}