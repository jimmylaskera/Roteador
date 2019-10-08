package br.com.lp2.roteador;

public class Pacote {
    private int cabecalho = 0;
    private int terminador = 0;
    private String dados;
    private String destino;

    public Pacote() {
    }

    public Pacote(String dados, String destino) {
        this.dados = dados;
        this.destino = destino;
    }

    public String getDados() { return dados; }

    public void setDados(String dados) { this.dados = dados; }
    
    public String getDestino() { return destino; }
    
    public void setDestino(String destino) { this.destino = destino; }
    
    public boolean isStart() { return cabecalho == 1; }
    
    public boolean isEnd() { return terminador == 1; }
    
    public void setStart() { this.cabecalho = 1; }
    
    public void setEnd() { this.terminador = 1; }
    
    public void reset() {
        cabecalho = 0;
        terminador = 0;
    }
}
