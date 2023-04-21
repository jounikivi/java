import java.util.HashMap;
import java.util.Scanner;

public class MolkkyPistelaskuriV1 {
    public static void main(String[] args) {
        HashMap<String, Integer> pelaajat = new HashMap<String, Integer>();
        try (Scanner lukija = new Scanner(System.in)) {
            System.out.print("Anna pelaajien lukumäärä: ");
            int pelaajienLkm = lukija.nextInt();
            lukija.nextLine();

            for (int i = 1; i <= pelaajienLkm; i++) {
                System.out.print("Anna pelaajan " + i + " nimi: ");
                String nimi = lukija.nextLine();
                pelaajat.put(nimi, 0);
            }

            String[] pelaajaNimet = pelaajat.keySet().toArray(new String[0]);
            int nykyinenPelaajaIndex = 0; // Alustetaan ensimmäinen pelaaja vuoroon

            while (true) {
                String nykyinenPelaaja = pelaajaNimet[nykyinenPelaajaIndex];
                System.out.println(nykyinenPelaaja + " heittää seuraavaksi.");
            
                int pisteet = 0;
                while (true) {
                    System.out.print("Kuinka monta pistettä lisätään? (1-12): ");
                    pisteet = lukija.nextInt();
                    lukija.nextLine();
                    if (pisteet >= 0 && pisteet <= 12) {
                        break;
                    }
                    System.out.println("Syötä luku väliltä 0-12.");
                }
            
                int vanhatPisteet = pelaajat.get(nykyinenPelaaja);
                int uudetPisteet = vanhatPisteet + pisteet;
            
                if (uudetPisteet > 50) {
                    System.out.println("Liikaa pisteitä! " + nykyinenPelaaja + " tippuu takaisin 25 pisteeseen.");
                    pelaajat.put(nykyinenPelaaja, 25);
                } else {
                    pelaajat.put(nykyinenPelaaja, uudetPisteet);
                }
            
                nykyinenPelaajaIndex = (nykyinenPelaajaIndex + 1) % pelaajienLkm;
            
                for (String pelaaja : pelaajat.keySet()) {
                    System.out.println(pelaaja + ": " + pelaajat.get(pelaaja));
                }
            
                if (pelaajat.containsValue(50)) {
                    System.out.println("Peli päättyi! " + nykyinenPelaaja + " voitti.");
                    break;
                }
            }
            
        }
    }
}
