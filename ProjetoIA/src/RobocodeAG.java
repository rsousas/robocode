
import java.io.BufferedWriter;
import java.io.FileWriter;
import org.jgap.*;
import org.jgap.impl.*;

@SuppressWarnings("serial")
public class RobocodeAG {

    public static final int MAX_GERACOES = 100; 
    public static final int TAM_POPULACAO = 20; 
    public static final int QTD_ROUNDS = 1; 
    public static final String NOME_BOT = "Destroyer"; 
    public static final String PACKAGE = "ufv"; 
    public static final String ROBO_INI = "robots/sample/Fire.java"; 
    public static final String DIRETORIO_DEST = "robots"; 
    public static final String LOG = "robots/" + PACKAGE + "/" + NOME_BOT + "_treinamento.txt"; 
    public static final Boolean USAR_GUI = true; 
    public static final String[] INIMIGOS = new String[]{"sample.RamFire", "sample.Walls", "sample.SpinBot", "sample.Fire"};

    public void run() throws Exception {

        new GerarRobo(ROBO_INI, DIRETORIO_DEST, PACKAGE, NOME_BOT);

        Configuration conf = new DefaultConfiguration(); 
        
        conf.addGeneticOperator(new MutationOperator(conf, 10)); 
        conf.addGeneticOperator(new AveragingCrossoverOperator(conf)); 
        conf.addNaturalSelector(new TournamentSelector(conf, 2, 0.1), false); 
        conf.setPreservFittestIndividual(true); 

        Treinamento treino = new Treinamento(QTD_ROUNDS, PACKAGE + "." + NOME_BOT + "*", USAR_GUI, INIMIGOS);
        conf.setFitnessFunction(treino); 

        Gene[] genesModelo = new Gene[GerarRobo.getGenes().length];

        for (int i = 0; i < genesModelo.length; i++) {
            genesModelo[i] = new DoubleGene(conf, -10000, 10000);
        }
        IChromosome cromossomoModelo = new Chromosome(conf, genesModelo); // create chromosome template
        conf.setSampleChromosome(cromossomoModelo); // set chromosome template

        // set random populacao
        conf.setPopulationSize(TAM_POPULACAO); // create a populacao
        Genotype populacao = Genotype.randomInitialGenotype(conf);
        IChromosome maisApto = null;

        try {
            // logging fitness
            FileWriter file_stream = new FileWriter(LOG);
            BufferedWriter saida = new BufferedWriter(file_stream);

            // evolve populacao
            for (int geracao = 0; geracao < MAX_GERACOES; geracao++) {
                populacao.evolve(); // evolve populacao
                double fitness = 0;
                for (IChromosome crom : populacao.getChromosomes()) {
                    fitness += crom.getFitnessValue();

                }
                maisApto = populacao.getFittestChromosome(); // best chromosome
                System.out.println(geracao + " " + maisApto.getFitnessValue() + " " + fitness / populacao.getChromosomes().length);
                saida.append(geracao + " " + maisApto.getFitnessValue() + " " + fitness / populacao.getChromosomes().length + "\n");
                //System.saida.printf("\nafter %d generations the best solution is %s \n", gen + 1, melhorSolucao);
            }
            saida.close();
            GerarRobo.createRobo(maisApto); // pass best solution to build
            System.exit(0); // clean exit
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        new RobocodeAG().run(); // run main
    }
}
