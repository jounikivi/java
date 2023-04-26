/**
 * PeliOhjelma on graafinen käyttöliittymä pelille, jossa pelaajat saavat pisteitä 
 * vuorollaan ja voittaja on se, jolla on ensimmäisenä 50 pistettä.
 */
package uusiversio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


 /**
 * Tämä luokka sisältää pelin päälogiikan ja käyttöliittymän.
 */
public class PeliOhjelma extends JFrame implements ActionListener {

  
    private JLabel pelaajaNimi;
    private JTextField pistemaaraKentta;
    private JTable pistetaulukko;
    private DefaultTableModel taulukkoMalli;

    private String[] pelaajat;
    private int[] pisteet;

    private int nykyinenPelaaja;
    private boolean peliPaattynyt;

     /**
     * Konstruktori, joka luo käyttöliittymän ja alustaa pelin muuttujat.
     */
    public PeliOhjelma() {
        super("Peli");

        pelaajienLukumaaraJaNimet();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        pelaajaNimi = new JLabel("");
        pelaajaNimi.setHorizontalAlignment(JLabel.CENTER);
        add(pelaajaNimi, BorderLayout.NORTH);

        taulukkoMalli = new DefaultTableModel();
        taulukkoMalli.addColumn("Pelaaja");
        taulukkoMalli.addColumn("Pisteet");

        pistetaulukko = new JTable(taulukkoMalli);
        add(new JScrollPane(pistetaulukko), BorderLayout.CENTER);

        JPanel alapaneeli = new JPanel(new FlowLayout());

        pistemaaraKentta = new JTextField(10);
        alapaneeli.add(pistemaaraKentta);

        JButton lisaaPisteetNappi = new JButton("Lisää pisteet");
        lisaaPisteetNappi.addActionListener(this);
        alapaneeli.add(lisaaPisteetNappi);

        add(alapaneeli, BorderLayout.SOUTH);

        nykyinenPelaaja = 0;
        peliPaattynyt = false;

        pelaajanTiedot();

        setVisible(true);
    }

     /**
     * Metodi kysyy käyttäjältä pelaajien lukumäärän ja nimet, ja tallentaa ne taulukkoihin.
     */
    private void pelaajienLukumaaraJaNimet() {
        int lukumaara = Integer.parseInt(JOptionPane.showInputDialog(this, "Syötä pelaajien lukumäärä:"));
        pelaajat = new String[lukumaara];
        pisteet = new int[lukumaara];
        for (int i = 0; i < lukumaara; i++) {
            pelaajat[i] = JOptionPane.showInputDialog(this, "Syötä pelaajan " + (i+1) + " nimi:");
        }
    }

     /**
     * Metodi päivittää käyttöliittymässä näkyvän pelaajan nimen ja pistetaulukon.
     */
    private void pelaajanTiedot() {
        pelaajaNimi.setText(pelaajat[nykyinenPelaaja]);
        taulukkoMalli.setRowCount(0);
        for (int i = 0; i < pelaajat.length; i++) {
            taulukkoMalli.addRow(new Object[]{pelaajat[i], pisteet[i]});
        }
    }

  
    private void lisaaPisteet() {
      if (peliPaattynyt) {
          return;
      }
      String syote = pistemaaraKentta.getText();
      int lisattavatPisteet;
      try {
          lisattavatPisteet = Integer.parseInt(syote);
          if (lisattavatPisteet < 0 || lisattavatPisteet > 12) {
              JOptionPane.showMessageDialog(this, "Syötä kelvollinen pistemäärä väliltä 0-12.");
              return;
          }
      } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(this, "Syötä kelvollinen kokonaisluku.");
          return;
      }
      pisteet[nykyinenPelaaja] += lisattavatPisteet;
      if (pisteet[nykyinenPelaaja] > 50) {
          pisteet[nykyinenPelaaja] = 25;
      }
      pelaajanTiedot();
      if (pisteet[nykyinenPelaaja] == 50) {
          peliPaattynyt = true;
          JOptionPane.showMessageDialog(this, pelaajat[nykyinenPelaaja] + " voitti pelin!");
      } else {
          nykyinenPelaaja++;
          if (nykyinenPelaaja == pelaajat.length) {
              nykyinenPelaaja = 0;
          }
          pelaajanTiedot();
      }
      pistemaaraKentta.setText("");
  }
  
          public static void main(String[] args) {
            new PeliOhjelma();
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            lisaaPisteet();
        }
      }        


