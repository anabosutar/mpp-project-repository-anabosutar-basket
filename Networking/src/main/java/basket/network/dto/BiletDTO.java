package basket.network.dto;

import java.io.Serializable;

public class BiletDTO implements Serializable {
    private int id, pret, nr_rand, nr_loc;

    public BiletDTO(int id, int pret, int nr_rand, int nr_loc) {
        this.id = id;
        this.pret = pret;
        this.nr_rand = nr_rand;
        this.nr_loc = nr_loc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public int getNr_rand() {
        return nr_rand;
    }

    public void setNr_rand(int nr_rand) {
        this.nr_rand = nr_rand;
    }

    public int getNr_loc() {
        return nr_loc;
    }

    public void setNr_loc(int nr_loc) {
        this.nr_loc = nr_loc;
    }
}
