package org.example.BusinessLogic;

import org.example.GUI.FileWriting;
import org.example.GUI.SimulationFrame;
import org.example.Model.Client;
import org.example.Model.QueueClass;
import org.example.Model.RandomGenerator;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class SimulationManager implements Runnable {
    private int timeLimit, maxArrivalTime, minArrivalTime, maxServiceTime, minServiceTime, numberOfQueues, numberOfClients;
    private RandomGenerator randomClientGenerator;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private BlockingQueue<Client> waitingClients;

    private StringBuilder output = new StringBuilder();
    private String s, aux = " ";
    private int averageWTPerQueue[] = {0};
    private int personsWTPerQueue[] = {0};
    private int totalWT = 0, totalService = 0;
    private int peakHour[] = {0};
    private SimulationFrame frame;

    public SimulationManager(int timeLimit, int maxServiceTime, int minServiceTime, int maxArrivalTime, int minArrivalTime, int numberOfQueues, int numberOfClients) {

        this.timeLimit = timeLimit;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.maxServiceTime = maxServiceTime;
        this.minServiceTime = minServiceTime;
        this.numberOfQueues = numberOfQueues;
        this.numberOfClients = numberOfClients;

        randomClientGenerator = new RandomGenerator(numberOfClients, maxServiceTime, minServiceTime, maxArrivalTime, minArrivalTime);
        scheduler = new Scheduler(numberOfQueues);
        scheduler.changeStrategy(selectionPolicy);

        for (int i = 0; i < numberOfQueues; i++) {
            Thread queueThread = new Thread(scheduler.getQueues().get(i));
            queueThread.start();
        }

        waitingClients = randomClientGenerator.generaza();
    }


    public void run() {
        frame = new SimulationFrame("Simulation", numberOfQueues);
        averageWTPerQueue = new int[numberOfQueues];
        personsWTPerQueue = new int[numberOfQueues];
        peakHour = new int[timeLimit + 1];

        int currentTime = 0;

        while (currentTime < timeLimit) {
            s = "Time: " + (currentTime + 1);
            frame.setTime(s);

            for (Iterator<Client> iterator = waitingClients.iterator(); iterator.hasNext(); ) {
                Client client = iterator.next();
                if (client.getArrivalTime() == currentTime + 1) {
                    totalService = totalService + client.getServiceTime();
                    scheduler.dispatchClient(client);
                    iterator.remove();
                    waitingClients.remove(client);
                }
            }

            int k = 0;

            for (QueueClass gueue : scheduler.getQueues()) {
                if (!gueue.getEmpty()) {
                    Client client = gueue.getPrimul();
                    if (gueue.getPrevtime() + client.getServiceTime() == currentTime + 1) {
                        averageWTPerQueue[k] = averageWTPerQueue[k] + (currentTime + 1 - client.getArrivalTime());
                        personsWTPerQueue[k]++;
                        if (gueue.getQueueSize() > 1)
                            gueue.setPrevtime(currentTime + 1);
                        else if (!waitingClients.isEmpty())
                            gueue.setPrevtime(waitingClients.element().getArrivalTime());
                        gueue.remove();
                    }
                }
                k++;
            }

            for (QueueClass gueue2 : scheduler.getQueues()) {
                if (!gueue2.getEmpty())
                    gueue2.setWaitingPeriod();
            }

            int time = currentTime + 1;

            output.append("Time ").append(time).append("\nWaiting clients: ");
            aux = "";

            for (Client client : waitingClients) {
                aux = aux + "(" + (client.getId() + 1) + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ";
                output.append("(").append(client.getId() + 1).append(",").append(client.getArrivalTime()).append(",").append(client.getServiceTime()).append("); ");
            }

            s = "                Waiting clients:" + aux + "                 ";
            frame.setWaiting(s);
            output.append("\n");

            int i = 0;

            for (QueueClass gueue1 : scheduler.getQueues()) {
                i++;
                output.append("Queue ").append(i).append(": ");
                if (gueue1.getEmpty()) {
                    output.append("closed\n");
                    aux = "closed";
                } else {
                    aux = "";
                    for (Client client : gueue1.getClients()) {
                        peakHour[currentTime + 1]++;
                        output.append("(").append(client.getId() + 1).append(",").append(client.getArrivalTime()).append(",").append(client.getServiceTime()).append("); ");
                        aux = aux + "(" + (client.getId() + 1) + "," + client.getArrivalTime() + "," + client.getServiceTime() + "); ";
                    }
                    output.append("\n");
                }
                s = "Queue " + i + ": " + aux + "                     ";
                frame.setText(s, i);
            }

            currentTime++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < numberOfQueues; i++) {
            if (personsWTPerQueue[i] == 0)
                personsWTPerQueue[i] = 1;
            totalWT = totalWT + averageWTPerQueue[i] / personsWTPerQueue[i];
        }
        totalWT = totalWT / numberOfQueues;
        output.append("Average waiting time: ").append(totalWT);
        s = "                              Average waiting time: " + totalWT;
        frame.setAverageWaiting(s);
        output.append("\n");

        totalService = totalService / numberOfClients;
        output.append("Average service time: ").append(totalService);
        s = "                              Average service time: " + totalService;
        frame.setAverageService(s);
        output.append("\n");

        int maxPeakHour = 0;
        for (int i = 0; i < timeLimit; i++) {
            if (peakHour[i] > peakHour[maxPeakHour])
                maxPeakHour = i;
        }
        output.append("Peak Hour: ").append(maxPeakHour);
        s = "                              Peak Hour: " + maxPeakHour;
        frame.setPeakHour(s);
        output.append("\n");

        FileWriting fw = new FileWriting(output);
    }
}