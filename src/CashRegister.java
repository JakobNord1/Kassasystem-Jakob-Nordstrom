import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CashRegister implements ActionListener {

    
    JFrame frame;
    JTextArea receipt;
    JButton kaffeButton;
    JButton nalleButton;
    JButton muggButton;
    JButton chipsButton;
    JButton yoghurtButton;
    JButton daimButton;
    JTextArea inputProductName;
    JTextArea inputCount;
    JButton addToReceiptButton;
    JButton payButton;
    Produkt valdProdukt;
    Kvitto mittKvitto = new Kvitto();
    int kvittoNummer = 1;


    public CashRegister(){
        frame = new JFrame("IOT26 POS");
        createReceiptArea();
        createQuickButtonsArea();
        createAddArea();

        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);

    }

    private void createAddArea(){
        inputProductName = new JTextArea();
        inputProductName.setBounds(20,650,300,30);
        inputProductName.setFont(new Font("Arial Black", Font.BOLD, 18));
        frame.add(inputProductName);

        JLabel label = new JLabel("Antal");
        label.setBounds(340,625,300,30);
        label.setForeground(Color.WHITE);
        frame.add(label);

        inputCount = new JTextArea();
        inputCount.setBounds(330,650,50,30);
        inputCount.setFont(new Font("Arial Black", Font.BOLD, 18));
        frame.add(inputCount);

        addToReceiptButton = new JButton("Add");
        addToReceiptButton.setBounds(400,640,70,50);
        addToReceiptButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
        addToReceiptButton.addActionListener(this);
        frame.add(addToReceiptButton);

        payButton = new JButton("Pay");
        payButton.setBounds(480,640,70,50);
        payButton.setFont(new Font("Arial Black", Font.PLAIN, 14));
        payButton.addActionListener(this);
        frame.add(payButton);


    }

    private void createQuickButtonsArea() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setBackground(Color.green);
        panel.setPreferredSize(new Dimension(600, 600));

        Produkt kaffe = new Produkt("Kaffe", 80.5, 0.12);
        kaffeButton = new JButton("Kaffe");
        kaffeButton.putClientProperty("produkt", kaffe);
        kaffeButton.addActionListener(this);
        panel.add(kaffeButton);

        Produkt nalle = new Produkt("Nalle", 179.5, 0.25);
        nalleButton = new JButton("Nalle");
        nalleButton.putClientProperty("produkt", nalle);
        nalleButton.addActionListener(this);
        panel.add(nalleButton);

        Produkt mugg = new Produkt("Mugg", 20.0, 0.12);
        muggButton = new JButton("Mugg");
        muggButton.putClientProperty("produkt", mugg);
        muggButton.addActionListener(this);
        panel.add(muggButton);

        Produkt chips = new Produkt("Chips", 30.0, 0.25);
        chipsButton = new JButton("Chips");
        chipsButton.putClientProperty("produkt", chips);
        chipsButton.addActionListener(this);
        panel.add(chipsButton);

        Produkt yoghurt = new Produkt("Yoghurt", 30.0, 0.12);
        yoghurtButton = new JButton("Yoghurt");
        yoghurtButton.putClientProperty("produkt", yoghurt);
        yoghurtButton.addActionListener(this);
        panel.add(yoghurtButton);
        
        Produkt daim = new Produkt("Daim", 15.0, 0.25);
        daimButton = new JButton("Daim");
        daimButton.putClientProperty("produkt", daim);
        daimButton.addActionListener(this);
        panel.add(daimButton);




        panel.setBounds(0, 0, 600, 600);

        frame.add(panel);
    }

    private void createReceiptArea() {
        receipt = new JTextArea();
        receipt.setSize(400,400); 
        receipt.setLineWrap(true);
        receipt.setEditable(false);
        receipt.setVisible(true);
        receipt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane (receipt);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(600, 0, 400, 1000);

        frame.add(scroll);    
    }

    public void SkrivKvitto(boolean VisaTotalSumma){
        LocalDateTime nu = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String tid = nu.format(format);
        receipt.setText("");
        receipt.append("              Jakobs ännu bättre shop\n");
        receipt.append("----------------------------------------------------\n");
        receipt.append("\n");
        receipt.append("Kvittonummer: " + String.format("%-3s", kvittoNummer) + "        Datum: " + tid + "\n");
        receipt.append("----------------------------------------------------\n");
        for (KvittoRad vara : mittKvitto.getVaror()) {
            Produkt prod = vara.getProdukt();
            int antal = vara.getAntal();
            double totaltPris = prod.getPris() * antal;
            receipt.append(String.format("%-21s", prod.getNamn()) + String.format("%4s", antal) + " *" + String.format("%11s",prod.getPrisTvaDecimaler()) + "  = " + String.format("%9s",vara.getTotaltPrisTvaDecimaler())  + "  \n");
        }
        receipt.append("                                             ------\n");
        receipt.append("Total" + String.format("%46s", mittKvitto.getTotaltPrisTvaDecimaler())+ "\n");

        if (VisaTotalSumma) {
            receipt.append("\n");
            Kvitto kvitto12 = mittKvitto.getMoms12();
            receipt.append("Moms%      Moms        Netto                 Brutto\n");
            if (kvitto12.varor.size() > 0) {
                receipt.append(String.format("12,00%10s", kvitto12.getTotaltMomsTvaDecimaler()) + String.format("%13s", kvitto12.getTotaltPrisExMomsTvaDecimaler()) + String.format("%23s",kvitto12.getTotaltPrisInkMomsTvaDecimaler()) + "\n");
            }
            Kvitto kvitto25 = mittKvitto.getMoms25();
            if (kvitto25.varor.size() > 0) {
                receipt.append(String.format("25,00%10s", kvitto25.getTotaltMomsTvaDecimaler()) + String.format("%13s", kvitto25.getTotaltPrisExMomsTvaDecimaler()) + String.format("%23s",kvitto25.getTotaltPrisInkMomsTvaDecimaler()) + "\n");
            }
            receipt.append("\n");
            receipt.append("TACK FÖR DITT KÖP\n");
            mittKvitto.clear();
            kvittoNummer++;
        }

    }            

    //Händelser för knapptryckningar
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addToReceiptButton) {
            if(valdProdukt == null) {
                JOptionPane.showMessageDialog(frame, "En produkt måste väljas!");
            }
            else {
                try {
                    int antal = Integer.parseInt(inputCount.getText());
                    mittKvitto.addProdukt(valdProdukt, antal);
                    SkrivKvitto(false);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Antal måste vara en siffra!");
                }
            }
        }
        else if (e.getSource() == payButton) {
            if (mittKvitto.varor.size() == 0) {
                JOptionPane.showMessageDialog(frame, "Lägg till minst en produkt!");
            }
            else {
                SkrivKvitto(true);
            }


        }
        //Produktknapparna
        else {
            JButton button = (JButton) e.getSource();
            valdProdukt = (Produkt) button.getClientProperty("produkt");
            inputProductName.setText(valdProdukt.getNamn());
        }
    }
}
