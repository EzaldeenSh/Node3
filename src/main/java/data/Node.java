package data;
import communication.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Node implements Observer, Subject {

    private List<Integer> otherNodesPorts;
    private final String nodeID;
    private int portNumber;
    private int numberOfConnectedUsers;

    private static Node instance;

    private Node() {
        nodeID = "node3";
        this.portNumber = 8083;
        this.numberOfConnectedUsers = 0;
        registerObservers();
    }
    public static Node getInstance(){
        if (instance == null){
            instance = new Node();
        }
        return instance;
    }
    @Override
    public void registerObservers() {
        this.otherNodesPorts = new ArrayList<>();
        otherNodesPorts.add(8081);
        otherNodesPorts.add(8082);
        otherNodesPorts.add(8084);
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public void setNumberOfConnectedUsers(int numberOfConnectedClients) {
        this.numberOfConnectedUsers = numberOfConnectedClients;
    }
    public String getNodeID() {
        return nodeID;
    }

    public int getPortNumber() {
        return portNumber;
    }
    public int getNumberOfConnectedUsers() {
        return numberOfConnectedUsers;
    }
    @Override
    public void update(Message message) {
    new MessageExecutor().executeMessage(message);
    }

    @Override
    public void unregister() {
    this.otherNodesPorts = new ArrayList<>();
    }

    @Override
    public void notifyObservers(Message message) {
        for(Integer portNumber : otherNodesPorts){
            try {
                this.broadcastMessage(portNumber,message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void broadcastMessage(int portNumber,Message message) throws IOException {
        new MessageBroadcaster().broadcastMessage(portNumber , message);
    }

    @Override
    public String toString() {
        return "Node{" +

                "otherNodesPorts=" + otherNodesPorts +
                ", portNumber=" + portNumber +
                ", nodeID=" + nodeID +
                ", numberOfConnectedUsers=" + numberOfConnectedUsers +
                '}';
    }
}
