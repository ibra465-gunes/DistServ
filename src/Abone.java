import java.io.Serializable;

public class Abone implements Serializable {
    private static final long serialVersionUID = 1L;
    private int Id;
    private long lastUpdatedEpochMiliSeconds;
    private Boolean abone;
    private Boolean giris;

    public Abone() {
        Id = 1;
        lastUpdatedEpochMiliSeconds = 0;
        abone = false;
        giris = false;
    }

    public long getLastUpdatedEpochMiliSeconds() {
        return lastUpdatedEpochMiliSeconds;
    }

    public void setLastUpdatedEpochMiliSeconds(long lastUpdatedEpochMiliSeconds) {
        this.lastUpdatedEpochMiliSeconds = lastUpdatedEpochMiliSeconds;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Boolean getAbone() {
        return abone;
    }

    public void setAbone(Boolean abone) {
        this.abone = abone;
    }

    public Boolean getGiris() {
        return giris;
    }

    public void setGiris(Boolean giris) {
        this.giris = giris;
    }
}

