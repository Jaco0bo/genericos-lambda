package main.java.org.escuelaing.edu.co;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Minimal doubly-linked list implementing the required AbstractList methods.
 */
public class MyLinkedList<E> extends AbstractList<E> implements Iterable<E> {

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
        Node(E item, Node<E> prev, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private Node<E> head; // first
    private Node<E> tail; // last
    private int size = 0;

    public MyLinkedList() {}

    @Override
    public E get(int index) {
        checkIndex(index); // Check value
        return node(index).item;
    }

    // length of the linked list
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> n = node(index);
        E val = n.item;
        unlink(n);
        return val;
    }

    private void linkLast(E e) {
        final Node<E> l = tail;
        final Node<E> newNode = new Node<>(e, l, null);
        tail = newNode;
        if (l == null) head = newNode;
        else l.next = newNode;
        size++;
        modCount++;
    }

    private void unlink(Node<E> x) {
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) head = next;
        else prev.next = next;

        if (next == null) tail = prev;
        else next.prev = prev;

        x.item = null;
        x.next = null;
        x.prev = null;
        size--;
        modCount++;
    }

    private Node<E> node(int index) {
        if (index < (size >> 1)) { // size >> 1 == size/2
            Node<E> x = head;
            for (int i = 0; i < index; i++) x = x.next;
            return x;
        } else {
            Node<E> x = tail;
            for (int i = size - 1; i > index; i--) x = x.prev;
            return x;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> curr = head;
            @Override
            public boolean hasNext() { return curr != null; }
            @Override
            public E next() {
                if (curr == null) throw new NoSuchElementException();
                E it = curr.item;
                curr = curr.next;
                return it;
            }
        };
    }
}
