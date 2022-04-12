package bilet.client.gui;

import bilet.services.BiletException;
import bilet.services.IBiletObserver;
import bilet.services.IBiletServices;
import model.Bilet;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BiletClientCtrl implements IBiletObserver {
    private User user;
    private IBiletServices server;
    //private BileteListModel bileteModel;

    public BiletClientCtrl(IBiletServices server) {

        this.server = server;
       // bileteModel = new BileteListModel();

    }

    public void login(String username, String pass) throws BiletException {
        User userL = new User(username, pass);
        server.login(userL, this);
        user = userL;

    }

//    public List<Bilet> getAllBilete() throws BiletException{
//        return server.getAllBilete();
//
//    }

//    public ListModel getBileteModel() {
//        return bileteModel;
//    }

    public void logout() {
        try {
            server.logout(user, this);
        } catch (BiletException e) {
            System.out.println("Logout error " + e);
        }
    }
}
