package org.example.BusinessLogic;

import org.example.Model.QueueClass;
import org.example.Model.Client;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addClient(List<QueueClass> queues, Client c) {
        QueueClass selectedQueue = null;
        int minWaitingPeriod = Integer.MAX_VALUE;

        for (QueueClass queue : queues) {
            int serverWaitingPeriod = queue.getWaitingPeriod() + c.getServiceTime();

            if (serverWaitingPeriod < minWaitingPeriod) {
                selectedQueue = queue;
                minWaitingPeriod = serverWaitingPeriod;
            }
        }

        if (selectedQueue != null) {
            selectedQueue.addClient(c);
        }
    }

}
