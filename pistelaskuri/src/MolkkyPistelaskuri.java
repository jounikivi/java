import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * MolkkyPistelaskuri on Java-ohjelma, joka toimii pistelaskurina mölkky-pelissä.
 * Ohjelma kysyy pelaajien lukumäärän ja nimet käyttäjältä, ja näyttää käyttöliittymässä
 * nykyisen pelaajan nimen ja pistetilanteet taulukossa. Käyttäjä voi lisätä pisteitä
 * nykyiselle pelaajalle syöttämällä halutun pistemäärän tekstikenttään ja painamalla
 * "Lisää pisteet" -painiketta. Ohjelma tarkistaa, että syöte on kelvollinen kokonaisluku,
 * lisää pisteet pelaajalle ja päivittää pistetilanteet taulukkoon. Jos pelaaja saa yli
 * 50 pistettä, hän tippuu takaisin 25 pisteeseen. Jos jokin pelaaja saa 50 pistettä,
 * peli päättyy ja ilmoitetaan voittaja.
 */

public class MolkkyPistelaskuri extends JFrame {
    // Pelaajat tallennetaan HashMap-tietorakenteeseen, jossa avaimena on pelaajan nimi ja
    // arvona pelaajan pisteet.
    private HashMap<String, Integer> pelaajat = new HashMap<String, Integer>();
    private String[] pelaajaNimet;
    private int nykyinenPelaajaIndex = 0;

    // Käyttöliittymäkomponentit
    private JLabel pelaajaLabel;
    private JLabel pisteetLabel;
    private JTextField pisteetTextField;
    private JButton lisaaPisteetButton;
    private JTable taulukko;

     /**
     * Luo uuden MolkkyPistelaskuri-ikkunan. Kysyy pelaajien lukumäärän ja nimet käyttäjältä,
     * luo käyttöliittymäkomponentit ja asettaa ne ikkunaan.
     */

    public MolkkyPistelaskuri() {
        super("Mölkky Pistelaskuri");

        // Kysy pelaajien lukumäärä ja nimet
        int pelaajienLkm = Integer.parseInt(JOptionPane.showInputDialog("Anna pelaajien lukumäärä:"));
        for (int i = 1; i <= pelaajienLkm; i++) {
            String nimi = JOptionPane.showInputDialog("Anna pelaajan " + i + " nimi:");
            pelaajat.put(nimi, 0);
        }
        pelaajaNimet = pelaajat.keySet().toArray(new String[0]);

        // Luodaan käyttöliittymäkomponentit
        pelaajaLabel = new JLabel(pelaajaNimet[0]);
        pisteetLabel = new JLabel("Kuinka monta pistettä lisätään? (1-12):");
        pisteetTextField = new JTextField();
        lisaaPisteetButton = new JButton("Lisää pisteet");
        lisaaPisteetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lisaaPisteet();
            }
        });

        // Luodaan taulukko pelaajien pistetilanteille
        String[] otsikot = {"Pelaaja", "Pisteet"};
        Object[][] data = new Object[pelaajat.size()][2];
        int i = 0;
        for (String pelaaja : pelaajat.keySet()) {
            data[i][0] = pelaaja;
            data[i][1] = pelaajat.get(pelaaja);
            i++;
        }
        taulukko = new JTable(data, otsikot);

        // Muokataan pisteetTextFieldin kokoa
        pisteetTextField.setPreferredSize(new Dimension(20, 20));

        // Asetetaan käyttöliittymäkomponentit ikkunaan
          
                setLayout(new BorderLayout());
                JPanel ylaPaneeli = new JPanel(new FlowLayout());
                ylaPaneeli.add(pelaajaLabel);
                ylaPaneeli.add(pisteetLabel);
                ylaPaneeli.add(pisteetTextField);
                ylaPaneeli.add(lisaaPisteetButton);
                add(ylaPaneeli, BorderLayout.NORTH);
                JScrollPane taulukkoPaneeli = new JScrollPane(taulukko);
                add(taulukkoPaneeli, BorderLayout.CENTER);
        
                // Asetetaan ikkunan koko ja näkyvyys
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(400, 300);
                setLocationRelativeTo(null);
                setVisible(true);
            }

            /**
             * Metodi lisää pelaajalle annetun pistemäärän ja päivittää taulukon näytön.
             */
            private void lisaaPisteet() {
                try {
                    int pisteet = Integer.parseInt(pisteetTextField.getText());
            
                    // Tarkista että pisteet ovat sallitulla alueella 0-12
                    if (pisteet < 0 || pisteet > 12) {
                        JOptionPane.showMessageDialog(this, "Syötä luku väliltä 0-12.");
                        return; // Palaa metodiin ja älä lisää pisteitä
                    }
            
                    String nykyinenPelaaja = pelaajaNimet[nykyinenPelaajaIndex];
                    int vanhatPisteet = pelaajat.get(nykyinenPelaaja);
                    int uudetPisteet = vanhatPisteet + pisteet;
            
                    if (uudetPisteet > 50) {
                        JOptionPane.showMessageDialog(this, "Liikaa pisteitä! " + nykyinenPelaaja + " tippuu takaisin 25 pisteeseen.");
                        pelaajat.put(nykyinenPelaaja, 25);
                    } else {
                        pelaajat.put(nykyinenPelaaja, uudetPisteet);
                    }
            
                    nykyinenPelaajaIndex = (nykyinenPelaajaIndex + 1) % pelaajat.size();
                    pelaajaLabel.setText(pelaajaNimet[nykyinenPelaajaIndex]);
                    for (int i = 0; i < pelaajat.size(); i++) {
                        taulukko.setValueAt(pelaajat.get(pelaajaNimet[i]), i, 1);
                    }
            
                    if (pelaajat.containsValue(50)) {
                        JOptionPane.showMessageDialog(this, "Peli päättyi! " + nykyinenPelaaja + " voitti.");
                        System.exit(0);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Syöte ei ole kelvollinen kokonaisluku!");
                }
            }
            
        
            public static void main(String[] args) {
                new MolkkyPistelaskuri();
            }
        }
        
       
