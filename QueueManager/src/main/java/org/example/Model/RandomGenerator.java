package org.example.Model;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RandomGenerator {
   private int numberOfClients;
    private int maxServiceTime;
    private int minServiceTime;
    private int maxArrivalTime;
    private int minArrivalTime;
    public RandomGenerator(int numberOfClients,int maxServiceTime, int minServiceTime, int maxArrivalTime, int minArrivalTime) {
        this.numberOfClients =numberOfClients;
        this.maxServiceTime=maxServiceTime;
        this.minServiceTime=minServiceTime;
        this.maxArrivalTime=maxArrivalTime;
        this.minArrivalTime=minArrivalTime;
    }
    public BlockingQueue<Client> generaza(){
        BlockingQueue<Client> tasks = new LinkedBlockingQueue<>();
        Random rand = new Random();
        int taskCounter = 0;
        int prevArrivalTime=minArrivalTime;
        for (int i = 0; i < numberOfClients; i++) {
            int processingTime = rand.nextInt(maxServiceTime - minServiceTime) + minServiceTime;
            int arrivalTime = rand.nextInt(maxArrivalTime - prevArrivalTime) + prevArrivalTime;
            prevArrivalTime=arrivalTime;
            Client task = new Client(taskCounter++, arrivalTime, processingTime);
            tasks.add(task);
        }
        return tasks;
    }

}
