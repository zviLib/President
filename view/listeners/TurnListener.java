package view.listeners;

import model.player.Player;
/**
 * Gets notification when events that regards the turn cycle happens
 * @author Zvi
 *
 */
public interface TurnListener {
	public void notifyTurnChanged(Player newTurn);
	public void notifyPlayerFinished(Player p);
}
