package App.Entities;

public abstract class Vehicle {
    private int capacity;
    private int sideNumber;


    public Vehicle(int capacity, int num){
        this.capacity = capacity;
        sideNumber = num;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSideNumber() {
        return sideNumber;
    }
}