import java.util.LinkedList;

/**
 * Guerreiro
 */
public class Guerreiro {

    private String nome;
    private int terraConquistada;
    private int terraHerdada;
    private int totalTerras;
    private Guerreiro pai;
    private LinkedList<Guerreiro> Filhos;

    public Guerreiro(String nome, int terraConquistada) {
        this.nome = nome;
        /* 
            Usado para a criação do root(pai de todos)
         */
        this.totalTerras = 0;
        this.terraHerdada = 0;
        this.terraConquistada = terraConquistada;
        this.pai = null;
        this.Filhos = new LinkedList<>();
    }

    public Guerreiro(Guerreiro pai, String nome, int terraConquistada) {
        this.pai = pai;
        this.nome = nome;
        this.totalTerras = 0;
        this.terraHerdada = 0;
        this.terraConquistada = terraConquistada;
        this.Filhos = new LinkedList<>();
    }

    public boolean addGuerreiro(String nomePai, String nome, int terraConquistada) {
        Guerreiro pai = this.findGuerreiro(nomePai);
        if (pai == null) {
            // Se não tiver pai não for encontrado, não faça nada.
            return false;
        }
        Guerreiro novoGuerreiro = new Guerreiro(pai, nome, terraConquistada);
        pai.addFilho(novoGuerreiro);
        return true;
    }

    public void addFilho(Guerreiro filho) {
        this.Filhos.add(filho);
    }
    public boolean temFilhos() {
        return !this.Filhos.isEmpty();
    }
    public Guerreiro findGuerreiro(String nomeProcurado) {
        if (this.nome.equals(nomeProcurado))
            return this;
        Guerreiro res = null;
        if (this.temFilhos()) {
            for (Guerreiro guerreiro : this.Filhos) {
                res = guerreiro.findGuerreiro(nomeProcurado);
                if (res != null)
                    return res;
            }
        }
        return res;
    }

    public void transfereTerras() {
        int totalTerras = this.terraConquistada + this.terraHerdada;
        this.totalTerras = totalTerras;

        if (this.temFilhos()) {
            int heranca = this.totalTerras / this.Filhos.size();
            for (Guerreiro guerreiro : this.Filhos) {
                guerreiro.transfereTerras(heranca);
            }
        }
    }

    public void transfereTerras(int heranca) {
        this.terraHerdada = heranca;
        this.transfereTerras();
    }
}