package org.example.Model;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class QueueClass implements Runnable {
    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;
    private boolean isRunning;
    private int prevtime=0;
    boolean primulClientDinQ=true;
    public QueueClass() {
        clients=new LinkedBlockingQueue<>();
        waitingPeriod = new AtomicInteger(0);
        isRunning = true;
    }

    public void addClient(Client newClient) {
        clients.add(newClient);
        waitingPeriod.addAndGet(newClient.getServiceTime());
        if (primulClientDinQ==true){
            prevtime=clients.element().getArrivalTime();
            primulClientDinQ=false;
        }

    }

    public void stop() {
        isRunning = false;
    }

    public void run() {
        while (isRunning) {
            try {
                if (clients.isEmpty()) {
                    Thread.sleep(100);
                    continue;
                }
                Client client =clients.element();
                Thread.sleep(client.getServiceTime());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
public boolean getEmpty(){
        if(clients.isEmpty())
            return true;
        else
            return false;
}
public Client getPrimul(){
        return clients.element();
}
public void remove(){
        clients.remove();
}
public void setPrevtime(int time){
        prevtime=time;
}
    public int getPrevtime(){
        return prevtime;
    }
    public List<Client> getClients() {
        return new ArrayList<>(clients);
    }
    public void setWaitingPeriod() {
        waitingPeriod.set(waitingPeriod.get()-1);
    }
    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }
    public int getQueueSize() {
        return clients.size();
    }
}