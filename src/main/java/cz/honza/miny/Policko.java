/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template

Toto je domácí verze souborů z programování.
 */
package cz.honza.miny;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author honza
 */
public class Policko {
    private boolean jeMina;
    private Znacka znacka;
    private final int x;
    private final int y;
    private final Pole POLE;
    private JButton tlacitko;

    public Policko(int x, int y, Pole pole,boolean jeMina) {
        this.jeMina = jeMina;
        this.x = x;
        this.y = y;
        this.POLE = pole;
        this.znacka = Znacka.NIC; 
    }
    public Policko(int x, int y, Pole pole) {
        this.jeMina = false;//výchozí hodnota
        this.x = x;
        this.y = y;
        this.POLE = pole;
        this.znacka = Znacka.NIC;
    }
    
    /*
    vrátí počet sousedních min, včetně miny na daném políčku (pokud je)
    */
    public int pocetSousednichMin(){
        int pocetMin = 0;
        //projede mřížku 3x3 se středem tohoto políčka
        for(int kontrolovaneX = this.x - 1; kontrolovaneX <= this.x + 1; kontrolovaneX++){//      řádky
            for (int kontrolovaneY = this.y - 1; kontrolovaneY <= this.y + 1; kontrolovaneY++) {//sloupce
                
                if(kontrolovaneX < 0 || kontrolovaneY < 0 || kontrolovaneX > POLE.sirka - 1 || kontrolovaneY > POLE.vyska -1){//mimo rozsah pole
                    //neexistující políčko, nic nedělá
                }else if(POLE.getPolicko(kontrolovaneX, kontrolovaneY).jeMina()){
                    pocetMin ++;
                }
            }
        }
        return pocetMin;//počet min v sousedství
        
            
    }

    public JButton getTlacitko() {
        return tlacitko;
    }

    
    public boolean odkryjSe(){
        if (znacka == Znacka.ODHALENE)//odhalene policko se nebude odhalovat znovu
            return false;
        
        znacka = Znacka.ODHALENE;
        
        if (tlacitko != null) { //pokud tlačítko neexistuje, tak na něj není nic napsáno, ale ostatní logika bude stále fungovat
            if (jeMina) {
                tlacitko.setBackground(Color.RED);
                //ukončí hru: časovač, dialog, promenou
                POLE.casovac.zastav();
                JOptionPane.showMessageDialog(null, "Hra skončila, muzes zavrit okno, nebo pokracovat", "Vybouchl/a jsi.", JOptionPane.ERROR_MESSAGE);
                POLE.prohral = true;

            } else {
                //zvisi pocet odkrytych, nakresli na sebe správnou barvu a text
                
                tlacitko.setText(Integer.toString(pocetSousednichMin()));
                tlacitko.setBackground(Barva.barvaCisla(pocetSousednichMin()));
                POLE.zvysPocetOdkrytych();
            }
        }
        
        //rekurzivní odkrývání:
        //pokud se klikne na nulu, tak se má odkrýt celý strůvek nul a jejich okrajů
        if (!jeMina && pocetSousednichMin() == 0) {
            // rekurzivně odhal sousedy
            //projede sousedy po
            for (int dx = -1; dx <= 1; dx++) {    //řádku a
                for (int dy = -1; dy <= 1; dy++) {//slupci
                    if (dx == 0 && dy == 0) {//kontrola zda to není toto políčko.
                        continue; //přeskočí jedo opakování cyklu, tzn. že kod níže se neprovede (toto políčko odkrývat nebude
                    }
                    
                    //pozice políčka co se má odkrývat
                    int novyX = x + dx;
                    int novyY = y + dy;
                    
                    if (novyX >= 0 && novyX < POLE.sirka && novyY >= 0 && novyY < POLE.vyska) {//provede pouze na existujících políčkách (v rozsahu pole.)
                        Policko soused = POLE.getPolicko(novyX, novyY);
                        if (soused.getZnacka() == Znacka.NIC) {
                            soused.odkryjSe(); //nechá odkrýt každého svého neodkrytého souseda.
                        }
                    }
                }
            }
        }
        
        return jeMina;
    }
    
    public void odkryjZnameSousedy(){
        //zkontrolování, jestli se má funkce provést.
        //provést se mé pouze pokud bude označený správný počet okolních políček a zároveň pokud bude toto políčko odhalené
        if(znacka != Znacka.ODHALENE){
            return;
        }
        
        //spočítá jestli je označený správný počet okolních políček.
        int pocetZnacek = 0;
        //všechny sousedy to projede a koukne se jestli nejsou označené
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if(dx+x < 0 || dx+x >= POLE.sirka || dy+y < 0 || dy+y >= POLE.sirka){
                    continue;//políčko neexistuje a nepujde zkontrolovat.
                }

                if (POLE.getPolicko(dx + x, dy + y).getZnacka() == Znacka.URCITE){
                    pocetZnacek++;
                }
            }
        }
        
        if(pocetZnacek != pocetSousednichMin()){
            return;//funkce má proběhnout pouze pokud je označen správný počet okolních políček.
        }
        
        //odkryje okolní políčka
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx + x < 0 || dx + x >= POLE.sirka || dy + y < 0 || dy + y >= POLE.sirka) {
                    continue;//políčko neexistuje a nepujde zkontrolovat.
                }
                
                //odkryje všechna neodkrytá políčka
                if (POLE.getPolicko(dx + x, dy + y).getZnacka() == Znacka.NIC){
                    POLE.getPolicko(dx + x, dy + y).odkryjSe();
                }
            }
        }  
        
    }

    public void setJeMina(boolean jeMina) {
        this.jeMina = jeMina;
    }
    public void setTlacitko(JButton tlacitko) {
        this.tlacitko = tlacitko;
    }
    public boolean jeMina() {
        return jeMina;
    }

    public Znacka getZnacka() {
        return znacka;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setZnacka(Znacka znacka) {
        this.znacka = znacka;
    }
    
    //vrátí jako string písmeno (zakomentované je emoji, ale to nefunguje v mem pc) které oznacuje zda je policko mina nebo ne. Pomaha pri testovani, viz funkce toString  v Pole.java
    @Override
    public String toString() {
        if(jeMina){
            //return  "\uD83D\uDCA5";
            return "M"  ;
        }else{
            //return "\uD83D\uDFE9";
            return "o";
        }
    }
    
    
    
    
}
