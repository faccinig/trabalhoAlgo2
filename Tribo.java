import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Tribo {
    public Guerreiro root;

    public Tribo() {
        Path path1 = Paths.get("exemplo.txt");

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
            "node [shape=record]\n" +
            root.geraDotNodos() +
            root.geraDotConexoes() +
            "}";
        return dot;
    }
}
