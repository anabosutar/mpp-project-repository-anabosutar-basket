package model;

public class Echipa {
    int id;
    String nume;
    int nr_membrii;

    public Echipa(int id, String nume, int nr_membrii) {
        this.id = id;
        this.nume = nume;
        this.nr_membrii = nr_membrii;

    }

    public Echipa(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getNr_membrii() {
        return nr_membrii;
    }

    public void setNr_membrii(int nr_membrii) {
        this.nr_membrii = nr_membrii;
    }

    @Override
    public String toString() {
        return "Echipa{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", nr_membrii=" + nr_membrii +
                '}';
    }
}
