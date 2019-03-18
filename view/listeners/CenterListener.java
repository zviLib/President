package view.listeners;

import model.hand.Set;

/**
 * Gets notification when events that regards the center set happens
 * @author Zvi
 *
 */
public interface CenterListener {
	public void notifyChange(Set newCenter);
	public void notifyPass();
}
