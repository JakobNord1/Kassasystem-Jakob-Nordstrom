

public class Produkt {
    private String namn;
    private Double pris;
    private Double momsSats;

    public Produkt(String Namn, Double Pris, Double MomsSats) {
        this.namn = Namn;
        this.pris = Pris;
        this.momsSats = MomsSats;
    }

    public String getNamn() {
        return namn;
    }
    public Double getPris() {
        return pris;
    }
    public Double getMoms() {
        return pris * momsSats;
    }
    public String getPrisTvaDecimaler() {
        return String.format("%.2f", pris);
    }
    public Double getMomsSats() {
        return momsSats;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }
    public void setPris(Double pris) {
        this.pris = pris;
    }
    public void setMomsSats(Double momsSats) {
        this.momsSats = momsSats;
    }
}
