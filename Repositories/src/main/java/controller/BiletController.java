package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Bilet;
import service.BiletService;
import utils.BiletEvent;
import utils.Observer;

import java.util.List;

public class BiletController implements Observer<BiletEvent> {
    private BiletService biletservice;
    private ObservableList<Bilet> biletModel;


    public BiletController(BiletService biletsevice) {
        this.biletservice = biletsevice;
        biletsevice.addObserver(this);
        biletModel= FXCollections.observableArrayList();
        populateList();
    }

    private void populateList(){
        Iterable<Bilet> bilete=biletservice.getAll();
        bilete.forEach(x->biletModel.add(x));
    }
    public void add (Bilet bilet){
        biletservice.add(bilet);
    }

    public void delete(Bilet bilet)
    {
        biletservice.delete(bilet);
    }

    public Bilet findById(Integer id)
    {
        return biletservice.findById(id);

    }

    public List<Bilet> getAll()
    {
        return biletservice.getAll();
    }


    public ObservableList<Bilet> getBiletModel(){

        return biletModel;
    }
    @Override
    public void update(BiletEvent bilet) {
        switch (bilet.getStatusType()){
            case ADD:{ biletModel.add(bilet.getBilet()); break;}
            case DELETE:{biletModel.remove(bilet.getBilet()); break;}
            case UPDATE:{biletModel.remove(bilet.getOldBilet());
                biletModel.add(bilet.getBilet()); break;}
        }
    }
}
