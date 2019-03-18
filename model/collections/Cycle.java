package model.collections;

import java.util.Collection;
import java.util.LinkedList;

/**
 * used for keeping a collection in a cyclic order
 * 
 * @author Zvi
 */
public class Cycle<T> {

	private LinkedList<T> objects; // list of the cycled objects
	private T head; // the current object we are considering as the head of the cycle

	public Cycle() {
		objects = new LinkedList<>();
	}

	public Cycle(Collection<T> objects) {
		this.objects = new LinkedList<>(objects);
		head = this.objects.getFirst();
	}

	/**
	 * adds an object after the original end of the cycle
	 * @param t - an new object to add to the cycle
	 */
	public void add(T t) {
		objects.add(t);
	}

	/**
	 * removes an object from the cycle
	 * @param t
	 */
	public void remove(T t) {
		if (t == head)
			forwardHead();

		objects.remove(t);
	}

	/**
	 * forward the head to the following object
	 */
	public void forwardHead() {
		head = getNext();
	}

	/**
	 * set an object as the head of the cycle
	 * @param t
	 */
	public void setHead(T t) {
		if (objects.contains(t))
			head = t;
	}

	/**
	 * @return the current head of the cycle
	 */
	public T getHead() {
		return head;
	}

	/**
	 * @return the object that follows the current head 
	 */
	public T getNext() {
		if (!(head == objects.getLast()))
			return objects.get(objects.indexOf(head) + 1);
		else
			return objects.getFirst();
	}

	/**
	 * @return the number of objects in the cycle 
	 */
	public int size() {
		return objects.size();
	}
}
