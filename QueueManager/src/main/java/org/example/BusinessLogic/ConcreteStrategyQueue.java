package org.example.BusinessLogic;

import org.example.Model.QueueClass;
import org.example.Model.Client;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addClient(List<QueueClass> queues, Client client) {
        QueueClass shortestQueue = queues.get(0);
        for (QueueClass queue : queues) {
            if (queue.getQueueSize() < shortestQueue.getQueueSize()) {
                shortestQueue = queue;
            }
        }
        shortestQueue.addClient(client);
    }
}