package App.Queue;

import App.Events.Event;

public class GlobalEventQueue {
    private static GlobalEventQueue instance = null;
    private EventHeapPriorityQueue heapPriorityQueue;

    private GlobalEventQueue() {
        heapPriorityQueue = new EventHeapPriorityQueue();
    }

    public static GlobalEventQueue getInstance() {
        if (instance == null) {
            instance = new GlobalEventQueue();
        }
        return instance;
    }

    public EventHeapPriorityQueue getQueue() {
        return heapPriorityQueue;
    }
}