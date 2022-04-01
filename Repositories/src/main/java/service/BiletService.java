package service;

import model.Bilet;
import model.StatusType;
import org.springframework.stereotype.Component;
import repositories.IRepository;
import utils.*;

import java.util.ArrayList;
import java.util.List;


@Component
public class BiletService implements Observable<BiletEvent> {
    private IRepository<Integer, Bilet> biletrepo;
    private ObservableBiletRunner runner;

    public BiletService(IRepository<Integer, Bilet> biletrepo) {
        this.biletrepo = biletrepo;
    }
    public BiletService(IRepository<Integer, Bilet> biletrepo, ObservableBiletRunner runner){

        this.biletrepo=biletrepo;
        this.runner=runner;
    }
    public void add(Bilet bilet) {
        biletrepo.add(bilet);
        notifyObservers(new BiletEvent(StatusType.ADD,bilet));
    }

    public void delete(Bilet bilet) {
        biletrepo.delete(bilet.getId());
        notifyObservers(new BiletEvent(StatusType.DELETE,bilet ));
    }

    public void update(Bilet oldbilet,Bilet newBilet) {
        biletrepo.update(newBilet);
        notifyObservers(new BiletEvent(StatusType.UPDATE,newBilet,oldbilet));
    }

    public List<Bilet> getAll() {
        return biletrepo.getAll();
    }

    public Bilet findById(Integer id) {
        return biletrepo.findById(id);
    }


    private List<Observer<BiletEvent>> observers=new ArrayList<>();
    @Override
    public void addObserver(Observer<BiletEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<BiletEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(BiletEvent t) {
        observers.stream().forEach(x->x.update(t));
    }


}
