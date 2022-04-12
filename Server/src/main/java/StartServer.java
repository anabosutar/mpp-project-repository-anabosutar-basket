import basket.network.AbstractServer;
import basket.network.utils.BasketObjectConcurrentServer;
import bilet.server.BileteServiceImpl;
import bilet.services.IBiletServices;
import jdbc.BiletDBRepository;
import jdbc.UserDBRepository;
import repositories.BiletRepository;
import repositories.UserRepository;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find chatserver.properties "+e);
            return;
        }
        BiletRepository biletRepo=new BiletDBRepository(serverProps);
        UserRepository userRepo=new UserDBRepository(serverProps);
        IBiletServices chatServerImpl=new BileteServiceImpl(userRepo, biletRepo);
        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new BasketObjectConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
