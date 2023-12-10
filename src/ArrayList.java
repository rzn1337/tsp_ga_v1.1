import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class ArrayList<T extends Comparable<T>> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] elements;
    private int size;

    public ArrayList(ArrayList<T> list) {
        elements = (T[]) new Comparable[list.elements.length];
        size = list.size;
        for (int i = 0; i < size; i++) {
            elements[i] = list.elements[i];
        }
    }

    public ArrayList() {
        elements = (T[]) new Comparable[DEFAULT_CAPACITY];
        size = 0;
    }

    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    public void addAll(ArrayList<T> otherList) {
        for (int i = 0; i < otherList.size(); i++) {
            add(otherList.get(i));
        }
    }

    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        size = i;
    }

    private class ArrayIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements[currentIndex++];
        }
    }

    public ArrayList<T> asList() {
        ArrayList<T> list = new ArrayList<T>();
        for (int i = 0; i < size; i++) {
            list.add(elements[i]);
        }
        return list;
    }

    public void shuffle() {
        Random rand = new Random();
        for (int i = size - 1; i > 0; i--) {
            int randIndex = rand.nextInt(i + 1);
            T temp = elements[i];
            elements[i] = elements[randIndex];
            elements[randIndex] = temp;
        }
    }

    public void swap(int i, int j) {
        T temp = elements[i];
        elements[i] = elements[j];
        elements[j] = temp;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            resizeArray();
        }
    }

    private void resizeArray() {
        int newCapacity = elements.length * 2;
        T[] newArray = (T[]) new Comparable[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = elements[i];
        }
        elements = newArray;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for ArrayList");
        }
        return elements[index];
    }
}


