package br.com.lp2.roteador;

public class Pacote {
    private int cabecalho;
    private int terminador;
    private String dados;
    private String destino;

    public Pacote() {
    }

    public Pacote(String dados, String destino) {
        this.dados = dados;
        this.destino = destino;
    }


    public String getDados() {
        return dados;
    }

    public void setDados(String dados) {
        this.dados = dados;
    }
}
