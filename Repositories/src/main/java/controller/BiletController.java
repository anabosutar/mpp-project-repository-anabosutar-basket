package controller;

import model.Bilet;
import service.BiletService;

import java.util.List;

public class BiletController {
    private BiletService biletservice;

    public BiletController(BiletService biletsevice) {
        this.biletservice = biletsevice;
    }

    public void add (Bilet bilet){
        biletservice.add(bilet);
    }

    public void delete(Integer id)
    {
        biletservice.delete(id);
    }

    public Bilet findById(Integer id)
    {
        return biletservice.findById(id);

    }

    public List<Bilet> getAll()
    {
        return biletservice.getAll();
    }

    public void update(Bilet bilet)
    {
        biletservice.update(bilet);
    }
}
