package Agents;

import Behaviours.SellerBehaviour;
import Helpers.DFHelper;
import Helpers.TopicHelper;
import jade.core.AID;
import jade.core.Agent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SellerAgent extends Agent {

    private AID auctionTopic;

    @Override
    protected void setup() {
        DFHelper.registerAgent(this, "Seller");
        auctionTopic = TopicHelper.createTopic(this, "Auction");
        addBehaviour(new SellerBehaviour(auctionTopic));
    }
}
