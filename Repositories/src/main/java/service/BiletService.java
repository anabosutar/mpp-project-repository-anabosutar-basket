package service;

import model.Bilet;
import org.springframework.stereotype.Component;
import repositories.IRepository;

import java.util.List;

@Component
public class BiletService {
    private IRepository<Integer, Bilet> biletrepo;

    public BiletService(IRepository<Integer, Bilet> biletrepo) {
        this.biletrepo = biletrepo;
    }

    public void add(Bilet bilet) {
        biletrepo.add(bilet);
    }

    public void delete(Integer id) {
        biletrepo.delete(id);
    }

    public List<Bilet> getAll() {
        return biletrepo.getAll();
    }

    public Bilet findById(Integer id) {
        return biletrepo.findById(id);
    }

    public void update(Bilet bilet) {
        biletrepo.update(bilet);
    }


}
