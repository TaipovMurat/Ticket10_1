package Behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class SellerBehaviour extends Behaviour {
    private AID topic;
    private Random r = new Random();
    private int price;
    private int high = 400;
    private int low = 200;
    private boolean theEndOfWork;
    private int currentBet;
    private int auctionStep = 20;

    public SellerBehaviour(AID auctionTopic) {
        this.topic = auctionTopic;
    }

    @Override
    public void onStart() {
        price = r.nextInt(high-low)+low;
        currentBet = price*2;
        log.info(getAgent().getLocalName()+" got price "+price);
        ACLMessage betMessage =  new ACLMessage(ACLMessage.REQUEST);
        betMessage.setContent(String.valueOf(currentBet));
        betMessage.addReceiver(topic);
        getAgent().send(betMessage);
    }

    @Override
    public void action() {
        ACLMessage bet = getAgent().receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        if (bet != null){
            if(!bet.getSender().equals(getAgent().getAID())){
                int receivedBet = Integer.parseInt(bet.getContent());
                if((receivedBet <= currentBet)){
                    currentBet = receivedBet - auctionStep;
                    if (currentBet < price && price < receivedBet){
                        currentBet = price;
                    }
                    if(currentBet >= price){
                        log.info(getAgent().getLocalName()+" got new bet " +currentBet);
                        ACLMessage betMessage =  new ACLMessage(ACLMessage.REQUEST);
                        betMessage.setContent(String.valueOf(currentBet));
                        betMessage.addReceiver(topic);
                        getAgent().send(betMessage);
                    } else {
                        log.info(getAgent().getLocalName()+" can't bet anymore");
                        ACLMessage deny =  new ACLMessage(ACLMessage.FAILURE);
                        deny.setContent("0");
                        deny.addReceiver(topic);
                        getAgent().send(deny);
                        theEndOfWork = true;
                    }
                }
            }

        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.AGREE);
        ACLMessage receiveReply = getAgent().receive(mt);
        if (receiveReply != null){
            log.info("Agent {} got confirm from {}", getAgent().getLocalName(),
                    receiveReply.getSender().getLocalName());
            theEndOfWork = true;
        }
        return theEndOfWork;
    }
}
