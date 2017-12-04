
import javax.tools.*;
import org.jgap.IChromosome;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class GerarRobo {

    private static String arquivo;
    private static String modelo;
    private static Double[] genesDefault;

    public GerarRobo(String origem_bot, String diretorio, String pacote, String nome) {
        GerarRobo.arquivo = diretorio + "/" + pacote + "/" + nome + ".java";

        try {
            FileReader fstream = new FileReader(origem_bot);
            BufferedReader in = new BufferedReader(fstream);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                sb.append(line).append("\n");
            }

            String modelo = sb.toString().replaceAll("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/", "");
            modelo = modelo.replaceAll("package ([^;]+)", "package " + pacote);
            modelo = modelo.replaceAll("class ([^ ]+)", "class " + nome);
            modelo = modelo.replaceAll("int", "double");

            List<Double> ls = new ArrayList<>();
            Pattern padrao = Pattern.compile("(?>-?\\d+(?:[\\./]\\d+)?)");
            Matcher match = padrao.matcher(modelo);

            while (match.find()) {
                ls.add(Double.parseDouble(match.group()));
            }

            genesDefault = ls.toArray(new Double[ls.size()]);

            GerarRobo.modelo = modelo.replaceAll("(?>-?\\d+(?:[\\./]\\d+)?)", "%.0f");

        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public static Double[] getGenes() {
        return genesDefault;
    }

    public static String getModelo() {
        return modelo;
    }

    public static void createRobo(IChromosome cromossomo) {
        createCodigo(cromossomo);
        compile();
    }

    private static void compile() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, arquivo);
    }

    private static void createCodigo(IChromosome cromossomo) {
        try {

            FileWriter fstream = new FileWriter(arquivo);
            BufferedWriter saida = new BufferedWriter(fstream);
            Double[] genes = new Double[cromossomo.getGenes().length];

            for (int i = 0; i < genes.length; i++) {
                genes[i] = (Double) cromossomo.getGene(i).getAllele();
            }

            saida.append(String.format(modelo, genes));
            saida.close();
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
