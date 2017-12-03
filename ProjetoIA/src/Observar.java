
import robocode.control.events.*;

/* 
 * Robocode Battle Observar
 */
class Observar extends BattleAdaptor {

    int score = 0;
    String nome; // robot nome

    public Observar(String nome) {
        this.nome = nome;
    }

    // battle completed successfully
    @Override
    public void onBattleCompleted(BattleCompletedEvent e) {
        score = 0;
        for (robocode.BattleResults resultado : e.getSortedResults()) {
            if (resultado.getTeamLeaderName().equals(nome)) {
                score = resultado.getScore();
            }
        }
    }

    // information message during the battle
    @Override
    public void onBattleMessage(BattleMessageEvent e) {
        System.out.println(e.getMessage());
    }

    //  error message during the battle
    @Override
    public void onBattleError(BattleErrorEvent e) {
        System.err.println(e.getError());
    }

    public int getScore() {
        return score;
    }
}
