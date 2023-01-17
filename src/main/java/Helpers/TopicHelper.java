package Helpers;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ServiceException;
import jade.core.messaging.TopicManagementHelper;

public class TopicHelper {
    public static AID createTopic(Agent agent, String topicName) {
        try {
            TopicManagementHelper topicHelper = (TopicManagementHelper)
                    agent.getHelper(TopicManagementHelper.SERVICE_NAME);
            AID jadeTopic = topicHelper.createTopic(topicName);
            topicHelper.register(jadeTopic);
            return jadeTopic;
        } catch (ServiceException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot create topic with");
        }
    }
}