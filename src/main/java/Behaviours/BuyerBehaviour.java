package Behaviours;

import Helpers.DFHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BuyerBehaviour extends Behaviour {
    private AID topic;
    private List<AID> agents;

    public BuyerBehaviour(AID auctionTopic) {
        this.topic = auctionTopic;
    }

    @Override
    public void onStart() {
        agents = DFHelper.findAgents(getAgent(), "Seller");
        log.info("Agents count {}", agents.size());
    }

    @SneakyThrows
    @Override
    public void action() {
        ACLMessage deny = getAgent().receive(MessageTemplate.MatchPerformative(ACLMessage.FAILURE));
        if (deny != null){
            agents.remove(deny.getSender());
        } else {
            block();
        }
        if (agents.size() == 1){
            Thread.sleep(1000);
            log.info("Auction winner: "+agents.get(0).getLocalName());
            ACLMessage agree =  new ACLMessage(ACLMessage.AGREE);
            agree.setContent("0");
            agree.addReceiver(topic);
            getAgent().send(agree);
        }
    }

    @Override
    public boolean done() {

        return agents.size() == 1;
    }
}
