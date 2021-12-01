import java.io.FileWriter;
import java.io.IOException;
public class App {
   public static void main(String[] args) {
        String[] casos = {
            "./casos_teste/caso04a.txt",
            "./casos_teste/caso04B.txt",
            "./casos_teste/caso04c.txt",
            "./casos_teste/caso05a.txt",
            "./casos_teste/caso05b.txt",
            "./casos_teste/caso05c.txt",
            "./casos_teste/caso06a.txt",
            "./casos_teste/caso06B.txt",
            "./casos_teste/caso06c.txt",
            "./casos_teste/caso07a.txt",
            "./casos_teste/caso07b.txt",
            "./casos_teste/caso07c.txt",
            "./casos_teste/caso08a.txt",
            "./casos_teste/caso08b.txt",
            "./casos_teste/caso08c.txt",
            "./casos_teste/caso09a.txt",
            "./casos_teste/caso09b.txt",
            "./casos_teste/caso09c.txt",
            "./casos_teste/caso10a.txt",
            "./casos_teste/caso10b.txt",
            "./casos_teste/caso10c.txt"};
        try {
            FileWriter myWriter = new FileWriter("./resumo.csv");
            myWriter.write("pergaminho,nro_guerreiros,maior_dono_terras,terras\n");
            for (String filePath : casos) {
                Tribo t = new Tribo(filePath);
                t.writeDot();
                myWriter.write(t.resumo());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Não foi possível escrever o resumo!");
            e.printStackTrace();
        }
    }
}
