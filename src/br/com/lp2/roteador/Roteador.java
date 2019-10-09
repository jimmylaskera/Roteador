package br.com.lp2.roteador;
import java.util.ArrayList;

public class Roteador extends DispositivoDeRede implements Roteamento {
    private ArrayList<Pacote> p1;
    private ArrayList<Pacote> p2;
    private ArrayList<Pacote> p3;
    private ArrayList<Pacote> p4;
    private Pacote in_out;
    
    public Roteador() {
        p1 = new ArrayList<Pacote>();
        p2 = new ArrayList<Pacote>();
        p3 = new ArrayList<Pacote>();
        p4 = new ArrayList<Pacote>();
        in_out = new Pacote();
    }        

    public void setIn () {
        if (p1.size() == 0 || p1.get(0).getDados().equals(in_out.getDados())) {
            p1.add(in_out);
        }
        else if (p1.size() > 0 && !p1.get(0).getDados().equals(in_out.getDados())) {
            p1.add(in_out);
        }
        else if (p2.size() > 0 && !p2.get(0).getDados().equals(in_out.getDados())) {
            p1.add(in_out);
        }
        else if (p3.size() > 0 && !p3.get(0).getDados().equals(in_out.getDados())) {
            p1.add(in_out);
        }
    }
    
    public Pacote get() { return in_out; }
    
    public void set(Pacote p) { in_out = p; }
    
    public void setOut () {
        if (p1.size() > 0) {
            in_out = p1.get(0);
            p1.remove(0);
        }
        
         else if (p2.size() > 0) {
            in_out = p2.get(0);
            p2.remove(0);
        }
        
        else if (p3.size() > 0) {
            in_out = p3.get(0);
            p3.remove(0);
        }
        
        else if (p4.size() > 0) {
            in_out = p4.get(0);
            p4.remove(0);
        }
    }
        
    @Override
    public int roteamento() {
        int saida = -1;
        if (p1.size() > 0) saida = (int) p1.get(0).getDestino().charAt(10);
        else if (p2.size() > 0) saida = (int) p2.get(0).getDestino().charAt(10);
        else if (p3.size() > 0) saida = (int) p3.get(0).getDestino().charAt(10);
        else if (p4.size() > 0) saida = (int) p4.get(0).getDestino().charAt(10);
        return saida;
    }
}
