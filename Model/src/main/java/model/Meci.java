package model;

public class Meci {
    private int id;
    private int locuri_disponibile;
    private String echipa1;
    private String echipa2;
    private String etapa;

    public Meci(int id, int locuri_disponibile, String echipa1, String echipa2, String etapa)
    {
        this.id = id;
        this.locuri_disponibile = locuri_disponibile;
        this.echipa1 = echipa1;
        this.echipa2 = echipa2;
        this.etapa = etapa;
    }

    public Meci(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocuri_disponibile() {
        return locuri_disponibile;
    }

    public void setLocuri_disponibile(int locuri_disponibile) {
        this.locuri_disponibile = locuri_disponibile;
    }

    public String getEchipa1() {
        return echipa1;
    }

    public void setEchipa1(String echipa1) {
        this.echipa1 = echipa1;
    }

    public String getEchipa2() {
        return echipa2;
    }

    public void setEchipa2(String echipa2) {
        this.echipa2 = echipa2;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    @Override
    public String toString() {
        return "Meci{" +
                "id=" + id +
                ", locuri_disponibile=" + locuri_disponibile +
                ", echipa1='" + echipa1 + '\'' +
                ", echipa2='" + echipa2 + '\'' +
                ", etapa='" + etapa + '\'' +
                '}';
    }
}
