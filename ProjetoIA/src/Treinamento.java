
import org.jgap.*;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

public class Treinamento extends FitnessFunction {

    int qtd_rounds;
    String nome;
    Boolean usa_interface;
    String[] inimigos;

    public Treinamento(int qtd_rounds, String nome, Boolean usa_interface, String[] inimigos) {
        this.qtd_rounds = qtd_rounds;
        this.nome = nome;
        this.usa_interface = usa_interface;
        this.inimigos = inimigos;
    }

    @Override
    protected double evaluate(IChromosome cromossomo) {
        GerarRobo.createRobo(cromossomo);

        RobocodeEngine engine = new RobocodeEngine(new java.io.File(""));
        Observar observador = new Observar(nome);
        BattlefieldSpecification arena = new BattlefieldSpecification(800, 600);
        int treinamento = 0;

        engine.addBattleListener(observador);
        engine.setVisible(usa_interface);

        for (String inimigo : inimigos) {
            RobotSpecification[] robosSelecionados = engine.getLocalRepository(inimigo + "," + nome);
            BattleSpecification batalhaEspec = new BattleSpecification(qtd_rounds, arena, robosSelecionados);
            engine.runBattle(batalhaEspec, true);
            treinamento += observador.getScore();
        }

        engine.close();

        return treinamento > 0 ? treinamento : 0;
    }
}
