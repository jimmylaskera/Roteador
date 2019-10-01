package br.com.lp2.roteador;

public abstract class DispositivoDeRede {
    protected String IP;
    protected String MAC;


    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }
}
