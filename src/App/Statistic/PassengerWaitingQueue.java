package App.Statistic;

import App.Events.Event;

import java.util.Arrays;

public class PassengerWaitingQueue {
    private PassengerWaitingRecord[] records;
    private static final int DEFAULT_CAPACITY = 16;
    private int size;
    PassengerWaitingQueue() {
        records = new PassengerWaitingRecord[DEFAULT_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size == records.length) {
            records = Arrays.copyOf(records, size * 2);
        }
    }

    public void add(PassengerWaitingRecord record) {
        ensureCapacity();
        records[size] = record;
        size++;
    }

    public PassengerWaitingRecord remove() {
        if (size == 0) return null;
        return records[--size];
    }
}
