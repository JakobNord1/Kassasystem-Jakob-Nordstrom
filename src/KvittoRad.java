public class KvittoRad {
    Produkt produkt;
    int antal;

    public KvittoRad(Produkt produkt, int antal) {
        this.produkt = produkt;
        this.antal = antal;
    }

    public boolean addProdukt(Produkt produkt, int antal) {
        if (produkt == this.produkt){
            this.antal = this.antal + antal;
            return true;
        }
        else {
            return false;
        }
    }
    public Produkt getProdukt() {
        return produkt;
    }
    public int getAntal() {
        return antal;
    }
    public Double getTotaltPrisExMoms() {
        return produkt.getPris() * this.antal;
    }
    public Double getTotaltPrisInkMoms() {
        return produkt.getPris() * (1 + produkt.getMomsSats()) * this.antal;
    }
    public Double getTotaltMomsPris() {
        return produkt.getPris() * produkt.getMomsSats() * this.antal;
    }
    public String getTotaltPrisTvaDecimaler() {
        return String.format("%.2f", getTotaltPrisExMoms());
    }
}
