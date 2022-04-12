package bilet.services;

import model.Bilet;
import model.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public interface IBiletServices {
    void login(User user, IBiletObserver client) throws BiletException;
    //void getAllBilete(Bilet bilete) throws BiletException;
   // Bilet[] getAllBilete() throws BiletException;
    void logout(User user, IBiletObserver client) throws BiletException;

}
