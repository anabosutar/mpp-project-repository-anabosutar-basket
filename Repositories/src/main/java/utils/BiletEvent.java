package utils;

import model.Bilet;
import model.StatusType;

public class BiletEvent implements Event{
    private StatusType statusType;
    private Bilet bilet, oldBilet;

    public BiletEvent(StatusType statusType, Bilet bilet) {
        this.statusType = statusType;
        this.bilet = bilet;
    }

    public BiletEvent(StatusType statusType, Bilet bilet, Bilet oldBilet) {
        this.statusType = statusType;
        this.bilet = bilet;
        this.oldBilet = oldBilet;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public Bilet getBilet() {
        return bilet;
    }

    public void setBilet(Bilet bilet) {
        this.bilet = bilet;
    }

    public Bilet getOldBilet() {
        return oldBilet;
    }

    public void setOldBilet(Bilet oldBilet) {
        this.oldBilet = oldBilet;
    }
}
