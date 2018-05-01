import java.io.*;
import java.util.InvalidPropertiesFormatException;
import java.util.Observable;
import java.util.Properties;

class GameSettings extends Observable {

    private Player player = null;
    private String ip;
    private String nickName;
    private Properties properties = new Properties();
    private File configFile = new File( System.getProperty("user.dir") + "\\src\\config.xml");

    public void loadConfig(){

        InputStream inputStream;
        try {

            inputStream = new FileInputStream(configFile);
            properties.loadFromXML(inputStream);

            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ip = properties.getProperty("ip");
        this.nickName = properties.getProperty("nickName");
    }


    public GameSettings()
    {
        player = new Player("player1", true);
        loadConfig();
    }

    public void saveSettings () {
        properties.setProperty("ip", ip);
        properties.setProperty("nickName", nickName);

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

    public void setReady (boolean ready)
    {
        player.setReady(ready);
        if (ready)
        {
            setChanged();
            notifyObservers(player);
        }
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

    public  String getNickName() {return nickName; }

    public Player getPlayer() {
        return this.player;
    }
}
