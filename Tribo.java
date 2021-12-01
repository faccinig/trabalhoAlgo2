import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tribo {
    public Guerreiro root;
    public String pergaminho;

    public Tribo(String filePath) {        
        Path path1 = Paths.get(filePath);
        String fileName = path1.getFileName().toString();
        this.pergaminho = fileName;

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String aux[];
            String line = reader.readLine();
            // Lê primeira linha com a quantidade de terras do pai de todos
            int terraOriginal = Integer.parseInt(line);
            
            /*
                Lê o primeiro filho e cria o pai de todos
                Estou partindo da suposição de que o pai do primeiro filho é
                o pai de todos. Necessário confirmar com a profesoura.
             */
            line = reader.readLine();
            aux = line.split(" ");
            
            root = new Guerreiro(aux[0], terraOriginal);

            while (line != null) {
                aux = line.split(" ");
                root.addGuerreiro(aux[0], aux[1], Integer.parseInt(aux[2]));
                line = reader.readLine();
            }

            root.transfereTerras();
            root.identifiqueMaiorDonoTerrasUltimaGeracao();

        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }  
    }

    /**
     * @return String com os dados da Tribo
     */
    public String toString() {
        return root.toString(true);
    }

    public String geraDot() {
        String dot = "digraph g { \n" +
            "graph [rankdir=LR]\n" +
            "node [shape=record fillcolor=lightgrey style=filled]\n" +
            root.geraDotNodos() +
            root.geraDotConexoes() +
            "}";
        return dot;
    }

    public String writeDot() {
        String filePath = "dot/" + this.pergaminho.split("\\.")[0] + ".Dot";
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(this.geraDot());
            myWriter.close();
            return filePath;
        } catch (IOException e) {
            System.out.println("Não foi possível escrever o arquivo:\n \"" + filePath + "\"");
            e.printStackTrace();
            return "";
        }
    }

    public int nroGuerreiros() {
        return this.root.nroGuerreiros();
    }

    public String resumo() {
        String res = this.pergaminho;
        // res = "nroGuerreiros, maiorDonoTerrasUltimaGeracao, terrasMaiorDono
        res = res + "," + this.nroGuerreiros();
        Guerreiro maiorDonoTerrasUltimaGeracao = this.root.maiorDonoTerras();
        res = res + "," + maiorDonoTerrasUltimaGeracao.getNome();
        res = res + "," + maiorDonoTerrasUltimaGeracao.getTerras();
        res = res + "\n";

        // res = res + "," + this.pergaminho.split("\\.")[0];

        return res;
    }

}
