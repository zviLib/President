package controller.matchTrackers;

import java.util.ArrayList;
import java.util.List;

import model.hand.Set;
import view.listeners.CenterListener;

/**
 * used for observation on the center set.
 * @author Zvi
 *
 */
public class CenterTracker {

	private List<CenterListener> listeners;

	public CenterTracker() {
		listeners = new ArrayList<>();
	}

	public void addListener(CenterListener listener) {
		listeners.add(listener);
	}

	public void removeListener(CenterListener listener) {
		listeners.add(listener);
	}

	/**
	 * notify listeners that the center set has changed.
	 * @param newSet - the new center set
	 */
	public void notifySetChanged(Set newSet) {
		for (CenterListener cl : listeners)
			cl.notifyChange(newSet);
	}

	/**
	 * notify listeners that the center set hasn't changed during the turn
	 */
	public void notifyPass() {
		for (CenterListener cl : listeners)
			cl.notifyPass();
	}
}
