package br.com.lp2.roteador;

public class Main {

    public static void main(String[] args) {
        Roteador[][] root = new Roteador[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String ip = "192.168.0." + (3*i + j);
                root[i][j].setIP(ip);
                root[i][j].setMAC("00:00:00:00:00:00");
            }
        }
    }
}
