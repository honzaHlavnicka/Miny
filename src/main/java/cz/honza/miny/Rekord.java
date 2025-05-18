/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.honza.miny;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author jan.hlavnicka.s
 */
public class Rekord {
    public static void main(String[] args) {
        zkontrolujRekord(1, 1, 0, 58);
    }
    public static void zkontrolujRekord(int vyska, int sirka, int pocetMin, int sekundy) {
        File soubor = new File("miny-rekord-" + sirka + "x" + vyska + "-" + pocetMin + ".txt");
        int staryRekord = Integer.MAX_VALUE;//maximum, aby v případě, že žádný rekord není, tak aby vždy byla aktualní hra ta nejlepší

        //Pokus o načtení starého rekordu
        if (soubor.exists() && soubor.canRead()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(soubor))) {
                String radek = reader.readLine();//přečte první řádek, ostatní by měli být prázdné
                if (radek != null) {
                    //pokusí se ho zpracovat. Trim by tam být nemusel, ale kdyby e někdo vrtal v souborua nechal tam mezeru, takl to porad bude fungovat.
                    staryRekord = Integer.parseInt(radek.trim());
                }
            } catch (IOException | NumberFormatException ex) {
                chyba("Chyba se souborem na zápis rekordu.");
            }
        }

        //rekord překonán
        if (sekundy < staryRekord) {
            
            // Zápis nového rekordu
            try (BufferedWriter vystup = new BufferedWriter(new FileWriter(soubor))) {
                vystup.write(String.valueOf(sekundy));
                if(staryRekord != Integer.MAX_VALUE){
                    prekonanyRekord(sekundy, staryRekord);
                }else{
                    JOptionPane.showMessageDialog(null, "Tuto hru hraješ poprvé!\nTvůj čas byl " + sekundy + " sekund, což je tvůj první rekord.", "překonaný rekord", JOptionPane.PLAIN_MESSAGE);

                }
                
            } catch (IOException ex) {
                chyba("Překonal/a jsi rekord! Bohužel nebyl zaznamenám kvůli chybě souboru.");
            }
        }
    }

    private static void chyba(String s){
        JOptionPane.showMessageDialog(null, s, "chyba", JOptionPane.ERROR_MESSAGE);
    }
    private static void prekonanyRekord(int sekundy, int staryRekord){
        JOptionPane.showMessageDialog(null, "Úspěšně jsi překonal/a aktuální rekord!\nTvůj čas byl " + sekundy + " sekund. Nejlepší před tebou byl "+staryRekord + " sekund.", "překonaný rekord", JOptionPane.PLAIN_MESSAGE);
    }
    
}
