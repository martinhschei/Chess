package Config;

import Helpers.*;

import java.io.*;
import java.util.Observable;
import java.util.Properties;

public class GameSettings extends Observable {

    private Player player;
    private String ip;
    private String nickName;
    private String localPlayerWhite;
    private String localPlayerBlack;
    private final Properties properties = new Properties();
    private final File configFile = new File( System.getProperty("user.dir") + "\\src\\Config\\config.xml");
    private boolean isLocalGame;

    public void loadConfig(){

        InputStream inputStream;
        try {

            inputStream = new FileInputStream(configFile);
            properties.loadFromXML(inputStream);

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ip = properties.getProperty("ip");
        this.nickName = properties.getProperty("nickName");
        this.localPlayerWhite = properties.getProperty("localPlayerWhite");
        this.localPlayerBlack = properties.getProperty("localPlayerBlack");
    }


    public GameSettings()
    {
        player = new Player("player1", true);
        localPlayerWhite = "Player1";
        localPlayerBlack = "Player2";
        loadConfig();
    }

    public void saveSettings () {
        properties.setProperty("ip", ip);
        properties.setProperty("nickName", nickName);
        properties.setProperty("localPlayerWhite", localPlayerWhite);
        properties.setProperty("localPlayerBlack", localPlayerBlack);

        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(configFile);
            properties.storeToXML(outputStream,"");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIp(String ip)
    {
        player.setIp(ip);
        this.ip = ip;
    }

    public void startNetworkGame ()
    {
        player.setReady(true);
        isLocalGame = false;
            setChanged();
            notifyObservers(this);
    }
    public void startLocalGame ()
    {
        isLocalGame = true;
        setChanged();
        notifyObservers(this);
    }

    public boolean getIsLocalGame(){
        return isLocalGame;
    }

    public void setPlayer(Player arg)
    {
        player = arg;
        this.nickName = arg.getName();
    }

    public void setHost()
    {
        player.setHost();
    }

    public String getIp() { return ip; }

    public String getNickName() { return nickName; }

    public String getLocalPlayerWhite() { return localPlayerWhite; }

    public String getLocalPlayerBlack() { return localPlayerBlack; }

    public Player getPlayer() {
        return this.player;
    }
}
