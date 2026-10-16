import java.util.ArrayList;

public class Kvitto {
    ArrayList<KvittoRad> varor = new ArrayList<>();

    public void addProdukt(Produkt nyProdukt, int antal) {
        for (KvittoRad vara : varor) {
            if (vara.addProdukt(nyProdukt, antal)) {
                //Nytt antal på samma kvittorad (addProdukt metod)
                return;
            }
        }
        //Ny kvittorad på kvittot
        KvittoRad vara = new KvittoRad(nyProdukt, antal);
        varor.add(vara);
    }
    public void clear() {
        varor.clear();
    }

    public ArrayList<KvittoRad> getVaror() {
        return varor;
    }

    //Getters för beräkningar av totalpris (Summerar värdet för alla kvittorader)
    public Double getTotaltPrisExMoms() {
        Double totalt = 0.0;

        for (KvittoRad vara : varor) {
            totalt = totalt + vara.getTotaltPrisExMoms();
        }
        return totalt;
    }
    public String getTotaltPrisExMomsTvaDecimaler() {
        return String.format("%.2f", getTotaltPrisExMoms());
    }

    public Double getTotaltPrisInkMoms() {
        Double totalt = 0.0;

        for (KvittoRad vara : varor) {
            totalt = totalt + vara.getTotaltPrisInkMoms();
        }
        return totalt;
    }
    public String getTotaltPrisInkMomsTvaDecimaler() {
        return String.format("%.2f", getTotaltPrisInkMoms());
    }
    public Double getTotaltMomsPris() {
        Double totalt = 0.0;

        for (KvittoRad vara : varor) {
            totalt = totalt + vara.getTotaltMomsPris();
        }
        return totalt;
    }
    public String getTotaltMomsTvaDecimaler() {
        return String.format("%.2f", getTotaltMomsPris());
    }
    public String getTotaltPrisTvaDecimaler() {
        return String.format("%.2f", getTotaltPrisExMoms());
    }

    //Nya kvittoobjekt
    public Kvitto getMoms12() {
        Kvitto kvitto12 = new Kvitto();

        for (KvittoRad row : varor) {
            if (row.getProdukt().getMomsSats() == 0.12) {
                kvitto12.varor.add(row);
            }
        }
        return kvitto12;
    }
    public Kvitto getMoms25() {
        Kvitto kvitto25 = new Kvitto();

        for (KvittoRad row : varor) {
            if (row.getProdukt().getMomsSats() == 0.25) {
                kvitto25.varor.add(row);
            }
        }
        return kvitto25;
    }

}
