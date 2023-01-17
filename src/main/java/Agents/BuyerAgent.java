package Agents;

import Behaviours.BuyerBehaviour;
import Helpers.DFHelper;
import Helpers.TopicHelper;
import jade.core.AID;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuyerAgent extends Agent {
    private AID auctionTopic;

    @Override
    protected void setup() {
        DFHelper.registerAgent(this, "Buyer");
        auctionTopic = TopicHelper.createTopic(this, "Auction");
        addBehaviour(new BuyerBehaviour(auctionTopic));
    }
}
