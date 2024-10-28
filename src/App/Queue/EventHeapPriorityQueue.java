// Stworzono za pomocą:
// https://www.youtube.com/watch?v=0wPlzMU-k00&ab_channel=MichaelSambol
// https://www.youtube.com/watch?v=pLIajuc31qk&ab_channel=Insidecode
// ChatGPT: https://chat.openai.com/share/96335c1e-a8d7-4b10-95d6-7fdc960fd730
//
package App.Queue;

import App.Events.Event;

import java.util.Arrays;

public class EventHeapPriorityQueue implements HeapPriorityQueue{
    private Event[] heap;
    private int size;
    private static final int DEFAULT_CAPACITY = 16;

    public EventHeapPriorityQueue() {
        heap = new Event[DEFAULT_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            heap = java.util.Arrays.copyOf(heap, size * 2);
        }
    }

    private int getParentIndex(int index) { return (index - 1) / 2; }
    private int getLeftChildIndex(int index) { return 2 * index + 1; }
    private int getRightChildIndex(int index) { return 2 * index + 2; }

    private boolean hasParent(int index) { return getParentIndex(index) >= 0; }
    private boolean hasLeftChild(int index) { return getLeftChildIndex(index) < size; }
    private boolean hasRightChild(int index) { return getRightChildIndex(index) < size; }

    private void swap(int indexOne, int indexTwo) {
        Event temp = heap[indexOne];
        heap[indexOne] = heap[indexTwo];
        heap[indexTwo] = temp;
    }

    @Override
    public void add(Event event) {
        ensureCapacity();
        heap[size] = event;
        size++;
        heapifyUp();
    }

    private void heapifyUp() {
        int index = size - 1;
        while (hasParent(index) && heap[getParentIndex(index)].getTime() > heap[index].getTime()) {
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }

    @Override
    public Event poll() {
        if (size == 0) return null;
        Event item = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        return item;
    }

    private void heapifyDown() {
        int index = 0;
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && heap[getRightChildIndex(index)].getTime() < heap[smallerChildIndex].getTime()) {
                smallerChildIndex = getRightChildIndex(index);
            }
            if (heap[index].getTime() < heap[smallerChildIndex].getTime()) {
                break;
            } else {
                swap(index, smallerChildIndex);
            }
            index = smallerChildIndex;
        }
    }

    @Override
    public Event peek() {
        if (size == 0) return null;
        return heap[0];
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}

