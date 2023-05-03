package org.example.Model;

public class Client {
    private int id;
    private int arrivalTime;
    private int serviceTime;

    private boolean primulClientDinQueue;
    public Client(int id, int arrivalTime, int serviceTime) {
        this.id=id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.primulClientDinQueue = false;
    }

    public boolean getPrimulClientDinQueue() {
        return primulClientDinQueue;
    }

    public void setPrimulClientDinQueue(boolean primulClientDinQueue) {
        this.primulClientDinQueue = primulClientDinQueue;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getServiceTime() {
        return serviceTime;
    }
}