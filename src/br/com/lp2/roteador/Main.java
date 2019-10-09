package br.com.lp2.roteador;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        Roteador[][] root = new Roteador[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String ip = "192.168.0." + (3*i + j + 1);
                root[i][j] = new Roteador();
                root[i][j].setIP(ip);
                root[i][j].setMAC("00:00:00:00:00:00");
            }
        }
        
        try {
            RandomAccessFile com = new RandomAccessFile("comunicação.txt", "r");
            int fonte = 0;
            String line = null;
            String destino = null;
            
            while (true) {
                line = com.readLine();
                if (line == null) break;
                char x = line.charAt(10);
                fonte = x - '0';
                destino = line.substring(12, 23);
                int i = line.indexOf(" ", 24);
                int qtd = Integer.parseInt(line.substring(24, i));
                String txt = line.substring(i+1);
                
                Pacote tmp = new Pacote(txt, destino);
                for (int c = 0; c < qtd; c++) {
                    if (c == 0) tmp.setStart();
                    if (c == qtd-1) tmp.setEnd();
                    
                    root[fonte/3][fonte%3].set(tmp);
                    root[fonte/3][fonte%3].setIn();
                    tmp.reset();
                }
            }
            com.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Arquivo fonte não encontrado. É necessário que o arquivo 'comunicação.txt' esteja na pasta deste programa.");
        } catch (IOException ioe) {
            System.out.println("Erro na leitura");
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int saida = root[i][j].roteamento();
                if (saida != -1) saida -= '0';
                while (saida != -1) {
                    if (3*i + j == saida-1) break;
                    saida--;
                    root[i][j].setOut();
                    Pacote dest = root[i][j].get();
                    root[saida/3][saida%3].set(dest);
                    root[saida/3][saida%3].setIn();
                    saida = root[i][j].roteamento();
                    if (saida != -1) saida -= '0';
                }
            }
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    int saida = root[i][j].roteamento();
                    if (saida != -1) saida -= '0';
                    String file_name = "192.168.0." + saida + ".txt";
                    RandomAccessFile output = new RandomAccessFile(file_name, "rw");
                    while (saida != -1) {
                        root[i][j].setOut();
                        Pacote txt = root[i][j].get();
                        output.writeChars(txt.getDados() + "\n");
                        saida = root[i][j].roteamento();
                        if (saida != -1) saida -= '0';
                    }
                    output.close();
                } catch (FileNotFoundException fnfe) {
                    System.out.println("Arquivo fonte não encontrado");
                } catch (IOException ioe) {
                    System.out.println("Erro na leitura");
                }
            }
        }

        try {
            File garbage = new File("192.168.0.-1.txt");
            if (garbage.delete()) System.out.println("Roteamento concluído");
            else System.out.println("Erro");
        } catch (SecurityException e) {
            System.out.println("Arquivo não pode ser deletado");
        }
    }
}
