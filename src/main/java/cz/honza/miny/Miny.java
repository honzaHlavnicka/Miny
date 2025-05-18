/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license

Toto je domácí verze souborů z programování.
 */

package cz.honza.miny;


/**
 *
 * @author honza
 */


public class Miny {

    public static void main(String[] args) {
        new StartGui();
    }
    
    public static void spustHru(int sirka, int vyska, int miny) {
        Pole pole = new Pole(vyska, sirka, miny);
        Gui gui = new Gui(vyska, sirka, pole);
        gui.zobraz();
    }
    
    
}
