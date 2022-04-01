package utils;

import model.Bilet;

public interface IBiletRunner {
    void executeOneBilet();
    void executeAll();
    void addBilet(Bilet t);
}
