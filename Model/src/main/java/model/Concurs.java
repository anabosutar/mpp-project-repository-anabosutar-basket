package model;

public class Concurs {
    private int id;
    private int locuri_disponibile;
    private String echipa1;
    private String echipa2;
    private EtapaConcurs etapa;

    public Concurs(int id, int locuri_disponibile, String echipa1, String echipa2, EtapaConcurs etapa)
    {
        this.id = id;
        this.locuri_disponibile = locuri_disponibile;
        this.echipa1 = echipa1;
        this.echipa2 = echipa2;
    }

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

    public EtapaConcurs getEtapa() {
        return etapa;
    }

    public void setEtapa(EtapaConcurs etapa) {
        this.etapa = etapa;
    }




}
