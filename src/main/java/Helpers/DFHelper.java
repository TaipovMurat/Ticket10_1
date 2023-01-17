package Helpers;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DFHelper {
    /**
     * Register agents in DF and define their service
     * @param a - agent name
     * @param serviceName - agent's service
     * @return
     */
    public static boolean registerAgent(Agent a, String serviceName){
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(a.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceName);
        sd.setName(a.getLocalName());
        dfd.addServices(sd);

        try {
            DFService.register(a, dfd);
            return true;
        } catch (FIPAException e) {
            System.out.println("I'm not here");
            e.printStackTrace();
            return false;
        }
    }
    public static List<AID> findAgents(Agent a, String serviceName){
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceName);
        dfd.addServices(sd);
        try {
            DFAgentDescription[] search = DFService.search(a, dfd);
            return Arrays.stream(search)
                    .map(DFAgentDescription::getName)
                    .collect(Collectors.toList());

        } catch (FIPAException e) {
            e.printStackTrace();

        }
        return null;
    }


}


