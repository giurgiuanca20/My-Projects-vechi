package org.example.BusinessLogic;

import org.example.Model.QueueClass;
import org.example.Model.Client;
import java.util.List;

public interface Strategy {
     void addClient(List<QueueClass> queues, Client c);
}