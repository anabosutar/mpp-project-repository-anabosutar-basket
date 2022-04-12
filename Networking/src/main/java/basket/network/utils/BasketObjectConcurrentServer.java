package basket.network.utils;

import basket.network.objectprotocol.BasketClientObjectWorker;
import bilet.services.IBiletServices;

import java.net.Socket;

public class BasketObjectConcurrentServer extends AbsConcurrentServer {
    private IBiletServices chatServer;
    public BasketObjectConcurrentServer(int port, IBiletServices chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatObjectConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        BasketClientObjectWorker worker=new BasketClientObjectWorker(chatServer, client);
        Thread tw=new Thread(worker);
        return tw;
    }


}
