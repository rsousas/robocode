
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
        GerarRobo.createRobo(cromossomo); // build robot

        RobocodeEngine engine = new RobocodeEngine(new java.io.File("")); // create robocode engine
        Observar observador = new Observar(nome);
        BattlefieldSpecification arena = new BattlefieldSpecification(800, 600); // battlefield size
        int treinamento = 0;

        engine.addBattleListener(observador); // add battle listener to engine
        engine.setVisible(usa_interface); // show GUI

        for (String inimigo : inimigos) {
            RobotSpecification[] robosSelecionados = engine.getLocalRepository(inimigo + "," + nome); // robots in battle
            BattleSpecification batalhaEspec = new BattleSpecification(qtd_rounds, arena, robosSelecionados);
            engine.runBattle(batalhaEspec, true); // run battle
            treinamento += observador.getScore(); // set treinamento score
        }

        engine.close(); // clean up engine

        return treinamento > 0 ? treinamento : 0; // return treinamento score if it's over 0
    }
}
