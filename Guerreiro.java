import java.util.LinkedList;

/**
 * Guerreiro
 */
public class Guerreiro {

    private String nome;
    private int terraConquistada;
    private int terraHerdada;
    private int totalTerras;
    private int geracao;
    private boolean maiorDonoTerrasUltimaGeracao;
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
        this.geracao = 0;
        this.maiorDonoTerrasUltimaGeracao = false;
        this.pai = null;
        this.Filhos = new LinkedList<>();
    }

    public Guerreiro(Guerreiro pai, String nome, int terraConquistada) {
        this.pai = pai;
        this.nome = nome;
        this.totalTerras = 0;
        this.terraHerdada = 0;
        this.terraConquistada = terraConquistada;
        this.geracao = pai.geracao + 1;
        this.maiorDonoTerrasUltimaGeracao = false;
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

    public String getNome() {
        return this.nome;
    }

    public int getTerras() {
        return this.totalTerras;
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

    /**
     * Retorna uma lista com todos os elementos da árvore.
     * @return lista com os elementos da arvore.
     */
    public LinkedList<Guerreiro> asLinkedList() {
        LinkedList<Guerreiro> list = new LinkedList<>();
        this.appendOnLinkedList(list);
        return list;
    }

    private LinkedList<Guerreiro> appendOnLinkedList(LinkedList<Guerreiro> list) {
        list.add(this);
        if (this.temFilhos()) {
            for (Guerreiro guerreiro : this.Filhos) {
                guerreiro.appendOnLinkedList(list);
            }
        }
        return list;
    }

    /**
     * @return String com os dados do Guerreiro
     */
    public String toString() {
        String res = this.nome;
        if (pai != null) {
            res = res + " Filho de " + pai.nome;
        }
        res = res + ", conquistou " + this.terraConquistada;
        return res;
    }
    
    /**
     * @return String com os dados do Guerreiro e filhos
     */
    public String toString(boolean incluirFilhos) {
        String res = this.toString();
        if (incluirFilhos && this.temFilhos()) {
            for (Guerreiro guerreiro : this.Filhos) {
                res = res + "\n" + guerreiro.toString(incluirFilhos);
            }
        }
        return res;
    }

    private String dotNodo() {
        String atributos = "[label=\"" +
            this.nome + "|" + this.totalTerras + "\"";
        if (this.maiorDonoTerrasUltimaGeracao) {
            atributos = atributos + " color=red style=bold";
        }
        atributos = atributos + "]";
        return "  " + this.nome + " " + atributos + "\n";
    }

    public String geraDotNodos() {
        String res = this.dotNodo();
        if (this.temFilhos()) {
            for (Guerreiro guerreiro : this.Filhos) {
                res = res + guerreiro.geraDotNodos();
            }
        }
        return res;
    }

    public String geraDotConexoes() {
        String res = "";
        if (this.temFilhos()) {
            for (Guerreiro guerreiro : this.Filhos) {
                res = res + "  " + this.nome + " -> " + guerreiro.nome + ";\n";
            }
            for (Guerreiro guerreiro : this.Filhos) {
                res = res + guerreiro.geraDotConexoes();
            }
        }
        return res;
    }

    public void identifiqueMaiorDonoTerrasUltimaGeracao() {
        Guerreiro guerreiro = this.qualMaiorDonoTerrasUltimaGeracao(null);
        guerreiro.maiorDonoTerrasUltimaGeracao = true;
    }
    
    private Guerreiro qualMaiorDonoTerrasUltimaGeracao(Guerreiro maiorDono) {
        if (this.temFilhos()) {
            for (Guerreiro guerreiro : this.Filhos) {
                maiorDono = guerreiro.qualMaiorDonoTerrasUltimaGeracao(maiorDono);
            }
            return maiorDono;
        }
        if (maiorDono == null) {
            return this;
        }
    
        if (maiorDono.geracao > this.geracao) {
            return maiorDono;
        }
        if (maiorDono.geracao < this.geracao) {
            return this;
        }
        if (maiorDono.totalTerras > this.totalTerras) {
            return maiorDono;
        } else {
            return this;
        }
    }

    public int nroGuerreiros() {
        return this.nroGuerreiros(0);
    }

    private int nroGuerreiros(int acc) {
        acc = acc + 1;
        if (this.temFilhos()) {
            for (Guerreiro guerreiro : this.Filhos) {
                acc = guerreiro.nroGuerreiros(acc);
            }
        }
        return acc;
    }

    public Guerreiro maiorDonoTerras() {
        if (this.maiorDonoTerrasUltimaGeracao) {
            return this;
        }

        if (this.temFilhos()) {
            Guerreiro aux = null;
            for (Guerreiro guerreiro : this.Filhos) {
                aux = guerreiro.maiorDonoTerras();
                if (aux != null) {
                    return aux;
                }
            }
        }

        return null;
    }
}