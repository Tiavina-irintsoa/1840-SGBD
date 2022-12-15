package connection;
public class HostPort {
    int port;
    String host;
    public int getPort() {
        return port;
    }
    public void setPort(String port) throws Exception{
        if(port.matches("[+-]?\\d*(\\.\\d+)?")==false){      //si le port est un nombre
            throw new Exception("Port invalide");
        }
        int p=Integer.valueOf(port);
        if(port.length()==0){         
            throw new Exception("Le port est nul");
        }
        if(p<0 || p>9999){                  // si le port est negatif ou depasse les 4 chiffres
            throw new Exception("Port invalide");       
        }
        
        this.port = Integer.valueOf(port);
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
}
