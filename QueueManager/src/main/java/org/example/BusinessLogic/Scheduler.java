package org.example.BusinessLogic;

import org.example.Model.QueueClass;
import org.example.Model.Client;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<QueueClass> queues;
    private int maxNrQueues;
    private Strategy strategy;

    public Scheduler(int maxNoQueues) {
        queues = new ArrayList<>();
        this.maxNrQueues = maxNoQueues;
        for (int i = 0; i < maxNoQueues; i++) {
            QueueClass queue = new QueueClass();
            queues.add(queue);
            Thread t = new Thread(queue);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
        if (policy == SelectionPolicy.SHORTEST_TIME)
            strategy = new ConcreteStrategyTime();
    }

    public void dispatchClient(Client c) {
        strategy.addClient(queues, c);
    }

    public List<QueueClass> getQueues() {
        return queues;
    }
}
