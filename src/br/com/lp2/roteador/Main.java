package br.com.lp2.roteador;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {

    public static void main(String[] args) {
        Roteador[][] root = new Roteador[3][3];
        Roteador set = new Roteador();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String ip = "192.168.0." + (3*i + j + 1);
                set.setIP(ip);
                set.setMAC("00:00:00:00:00:00");
                root[i][j] = set;
                System.out.println(root[0][0].getIP());
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
                fonte = (int) line.charAt(10) - 1;
                destino = line.substring(12, 23);
                int i = line.indexOf(" ", 24);
                int qtd = Integer.parseInt(line.substring(24, i));
                String txt = line.substring(i+1);
                
                Pacote tmp = new Pacote(txt, destino);
                for (int c = 0; c < qtd; c++) {
                    if (c == 0) tmp.setStart();
                    if (c == qtd-1) tmp.setEnd();

                    set.set(tmp);
                    set.setIn();
                    root[fonte/3][fonte%3] = set;
                    tmp.reset();
                }
            }
            com.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Arquivo fonte não encontrado");
        } catch (IOException ioe) {
            System.out.println("Erro na leitura");
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                set = root[i][j];
                int saida = set.roteamento();
                while (saida != -1) {
                    if (3*i + j == saida-1) break;
                    saida--;
                    set.setOut();
                    Pacote dest = set.get();
                    Roteador r = root[saida/3][saida%3];
                    r.set(dest);
                    r.setIn();
                    root[saida/3][saida%3] = r;
                    root[i][j] = set;
                    saida = set.roteamento();
                }
            }
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    set = root[i][j];
                    int saida = set.roteamento();
                    String file_name = "192.168.0." + saida + ".txt";
                    RandomAccessFile output = new RandomAccessFile(file_name, "rw");
                    while (saida != -1) {
                        set.setOut();
                        Pacote txt = set.get();
                        output.writeChars(txt.getDados() + "\n");
                        root[i][j] = set;
                        saida = set.roteamento();
                    }
                    output.close();
                } catch (FileNotFoundException fnfe) {
                    System.out.println("Arquivo destino não encontrado");
                } catch (IOException ioe) {
                    System.out.println("Erro na leitura");
                }
            }
        }
    }
}
