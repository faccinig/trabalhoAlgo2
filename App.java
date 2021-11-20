import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
   public static void main(String[] args) {
       
        Path path1 = Paths.get("exemplo.txt");

        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String aux[];
            String s = reader.readLine();
            if (s != null)
                System.out.println(Integer.parseInt(s));
            String line = null;
            while ((line = reader.readLine()) != null) {
                aux = line.split(" ");
                System.out.println(aux[0] + " " + aux[1] + " " + Integer.parseInt(aux[2]));
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }  
       
    }
     
}
