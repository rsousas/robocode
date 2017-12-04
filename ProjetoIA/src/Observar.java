
import robocode.control.events.*;

class Observar extends BattleAdaptor {

    int score = 0;
    String nome;

    public Observar(String nome) {
        this.nome = nome;
    }

    @Override
    public void onBattleCompleted(BattleCompletedEvent e) {
        score = 0;
        for (robocode.BattleResults resultado : e.getSortedResults()) {
            if (resultado.getTeamLeaderName().equals(nome)) {
                score = resultado.getScore();
            }
        }
    }

    @Override
    public void onBattleMessage(BattleMessageEvent e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void onBattleError(BattleErrorEvent e) {
        System.err.println(e.getError());
    }

    public int getScore() {
        return score;
    }
}
