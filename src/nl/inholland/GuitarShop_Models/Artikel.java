package nl.inholland.GuitarShop_Models;

public class Artikel {
    private Integer aantal;
    private int id;
    private String merk;
    private String model;
    private Boolean akoestisch;
    private TypeGitaar type;
    private double prijs;

    public Artikel(Integer aantal, int id, String merk, String model, Boolean akoestisch, TypeGitaar type, double prijs) {
        this.aantal = aantal;
        this.id = id;
        this.merk = merk;
        this.model = model;
        this.akoestisch = akoestisch;
        this.type = type;
        this.prijs = prijs;
    }

    public Integer verkrijgAantal() {
        return aantal;
    }
    public void setAantal(Integer aantal) {
        this.aantal = aantal;
    }

    public int verkrijgId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String verkrijgModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public Boolean verkrijgAkoestisch() {
        return akoestisch;
    }
    public void setAkoestisch(Boolean akoestisch) {
        this.akoestisch = akoestisch;
    }

    public TypeGitaar verkrijgType() {
        return type;
    }
    public void setType(TypeGitaar type) {
        this.type = type;
    }

    public double verkrijgPrijs() {
        return prijs;
    }
    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public String verkrijgMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }
}
