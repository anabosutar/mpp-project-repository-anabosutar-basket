package basket.network.objectprotocol;

import basket.network.dto.BiletDTO;
import basket.network.dto.DTOUtils;
import basket.network.dto.UserDTO;
import bilet.services.BiletException;
import bilet.services.IBiletObserver;
import bilet.services.IBiletServices;
import model.Bilet;
import model.User;
import jdbc.JdbcUtils;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BasketServicesObjectProxy implements IBiletServices {
    private String host;
    private int port;

    private IBiletObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private JdbcUtils dbUtils;

    //private List<Response> responses;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public BasketServicesObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        //responses=new ArrayList<Response>();
        qresponses=new LinkedBlockingQueue<Response>();
    }

    public void login(User user, IBiletObserver client) throws BiletException {
        initializeConnection();
        UserDTO udto= DTOUtils.getDTO(user);
        sendRequest(new LoginRequest(udto));
        Response response=readResponse();
        if (response instanceof OkResponse){
            this.client=client;
            return;
        }
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new BiletException(err.getMessage());
        }
    }

//    public Bilet[] getAllBilete() throws BiletException {
//
//        Response response=readResponse();
//        if (response instanceof ErrorResponse){
//            ErrorResponse err=(ErrorResponse)response;
//            throw new ChatException(err.getMessage());
//        }
//        GetLoggedFriendsResponse resp=(GetLoggedFriendsResponse)response;
//        UserDTO[] frDTO=resp.getFriends();
//        User[] friends= DTOUtils.getFromDTO(frDTO);
//        return friends;
//    }
//    public List<Bilet> getAllBilete() throws BiletException {
//
//
        //log.traceEntry(" Get all");
//        ResultSet resultSet = null;
//        Connection connection = dbUtils.getConnection();
//        List<Bilet> bilete = new ArrayList<>();
//        try {
//            PreparedStatement statement =
//                    connection.prepareStatement("Select * from BILETE");
//            resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                Bilet bilet = new Bilet();
//                bilet.setId(resultSet.getInt(1));
//                bilet.setNr_loc(resultSet.getInt(2));
//                bilet.setNr_rand(resultSet.getInt(3));
//                bilet.setPret(resultSet.getInt(4));
//                bilete.add(bilet);
//            }
//            if (resultSet != null) {
//                resultSet.close();
//                //log.traceExit();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return bilete;
//    }


    public void logout(User user, IBiletObserver client) throws BiletException {
        UserDTO udto= DTOUtils.getDTO(user);
        sendRequest(new LogoutRequest(udto));
        Response response=readResponse();
        closeConnection();
        if (response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            throw new BiletException(err.getMessage());
        }
    }

//    public void logout(User user, IChatObserver client) throws BiletException {
//        UserDTO udto= DTOUtils.getDTO(user);
//        sendRequest(new LogoutRequest(udto));
//        Response response=readResponse();
//        closeConnection();
//        if (response instanceof ErrorResponse){
//            ErrorResponse err=(ErrorResponse)response;
//            throw new BiletException(err.getMessage());
//        }
//    }




    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws BiletException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new BiletException("Error sending object "+e);
        }

    }

    private Response readResponse() throws BiletException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws BiletException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


//    private void handleUpdate(UpdateResponse update){
//        if (update instanceof FriendLoggedInResponse){
//
//            FriendLoggedInResponse frUpd=(FriendLoggedInResponse)update;
//            User friend= DTOUtils.getFromDTO(frUpd.getFriend());
//            System.out.println("Friend logged in "+friend);
//            try {
//                client.friendLoggedIn(friend);
//            } catch (BiletException e) {
//                e.printStackTrace();
//            }
//        }
//        if (update instanceof FriendLoggedOutResponse){
//            FriendLoggedOutResponse frOutRes=(FriendLoggedOutResponse)update;
//            User friend= DTOUtils.getFromDTO(frOutRes.getFriend());
//            System.out.println("Friend logged out "+friend);
//            try {
//                client.friendLoggedOut(friend);
//            } catch (BiletException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (update instanceof NewMessageResponse){
//            NewMessageResponse msgRes=(NewMessageResponse)update;
//            Message message= DTOUtils.getFromDTO(msgRes.getMessage());
//            try {
//                client.messageReceived(message);
//            } catch (BiletException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
//                    if (response instanceof UpdateResponse){
//                        handleUpdate((UpdateResponse)response);
//                    }else{
                        /*responses.add((Response)response);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        synchronized (responses){
                            responses.notify();
                        }*/
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
