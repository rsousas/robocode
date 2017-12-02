/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robo06;

import robocode.control.events.*;

/**
 *
 * @author Renato Sousa
 */
public class battleObserver extends BattleAdaptor {

    // Called when the battle is completed successfully with battle results
    public void onBattleCompleted(BattleCompletedEvent e) {

        int robotScore = 0,
                enemyScore = 0;

        Robo06 robot = new Robo06();

        System.out.println("-- Battle has completed --");

        //Print out the sorted results with the robot names
        System.out.println("Battle results:");

        for (robocode.BattleResults result : e.getSortedResults()) {

            if (robot.battleResults(result.getTeamLeaderName(), result.getScore())) {

                robotScore = result.getScore();

            } else {

                enemyScore = result.getScore();

            }

        }

        robot.sortScore(robotScore, enemyScore); // sort score - func in robocodeGA.java

    }

    // Called when the game sends out an information message during the battle
    public void onBattleMessage(BattleMessageEvent e) {

        System.out.println("Msg> " + e.getMessage());

    }

    // Called when the game sends out an error message during the battle
    public void onBattleError(BattleErrorEvent e) {

        System.err.println("Err> " + e.getError());

    }
}
