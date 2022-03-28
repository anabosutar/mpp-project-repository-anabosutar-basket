package model;

public class Echipa {
    int id;
    String nume;
    String echipa_adversa;

    public Echipa(int id, String nume, String echipa_adversa) {
        this.id = id;
        this.nume = nume;
        this.echipa_adversa = echipa_adversa;
    }

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

    public String getEchipa_adversa() {
        return echipa_adversa;
    }

    public void setEchipa_adversa(String echipa_adversa) {
        this.echipa_adversa = echipa_adversa;
    }
}
