package bilet.server;

import bilet.services.BiletException;
import bilet.services.IBiletObserver;
import bilet.services.IBiletServices;
import model.Bilet;
import model.User;
import repositories.BiletRepository;
import repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BileteServiceImpl implements IBiletServices {

    private UserRepository userRepo;
    private BiletRepository biletrepo;
    private Map<String,IBiletObserver> loggedClients;

    public BileteServiceImpl(UserRepository userRepo, BiletRepository biletrepo) {
        this.userRepo = userRepo;
        this.biletrepo = biletrepo;
        loggedClients=new ConcurrentHashMap<>();

    }

    @Override
    public void login(User user, IBiletObserver client) throws BiletException {
        User userR=userRepo.findBy(user.getUsername(),user.getPassword());
        if (userR!=null){
            if(loggedClients.get(user.getUsername())!=null)
                throw new BiletException("User already logged in.");
            loggedClients.put(user.getUsername(), client);
            //notifyFriendsLoggedIn(user);
        }else
            throw new BiletException("Authentication failed.");

    }


    public List<Bilet> getAllBilete() throws BiletException {
        List<Bilet> bilete = biletrepo.getAll();
        return bilete;
    }

    public synchronized void logout(User user, IBiletObserver client) throws BiletException {
        IBiletObserver localClient=loggedClients.remove(user.getId());

}}
