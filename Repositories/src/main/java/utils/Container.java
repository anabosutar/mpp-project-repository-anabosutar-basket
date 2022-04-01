package utils;

import model.Bilet;

public interface Container {
    Bilet remove();
    void add(Bilet task);
    int size();
    boolean isEmpty();
    Iterable<Bilet> getAll();
}
