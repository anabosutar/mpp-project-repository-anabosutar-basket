package basket.network.objectprotocol;

import basket.network.dto.DTOUtils;
import basket.network.dto.UserDTO;
import bilet.services.BiletException;
import bilet.services.IBiletObserver;
import bilet.services.IBiletServices;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BasketClientObjectWorker implements Runnable, IBiletObserver {
    private IBiletServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public BasketClientObjectWorker(IBiletServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Object response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse((Response) response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }



    private Response handleRequest(Request request){
        Response response=null;
        if (request instanceof LoginRequest){
            System.out.println("Login request ...");
            LoginRequest logReq=(LoginRequest)request;
            UserDTO udto=logReq.getUser();
            User user= DTOUtils.getFromDTO(udto);
            try {
                server.login(user, this);
                return new OkResponse();
            } catch (BiletException e) {
                connected=false;
                return new ErrorResponse(e.getMessage());
            }
        }
//        if (request instanceof LogoutRequest){
//            System.out.println("Logout request");
//            LogoutRequest logReq=(LogoutRequest)request;
//            UserDTO udto=logReq.getUser();
//            User user= DTOUtils.getFromDTO(udto);
//            try {
//                server.logout(user, this);
//                connected=false;
//                return new OkResponse();
//
//            } catch (ChatException e) {
//                return new ErrorResponse(e.getMessage());
//            }
 //
 //       }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

}
