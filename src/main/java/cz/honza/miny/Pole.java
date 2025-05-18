/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template

Toto je domácí verze souborů z programování.
 */
package cz.honza.miny;

import java.util.Random;
import javax.swing.JOptionPane;



/**
 *
 * @author honza
 */
public class Pole {
    public CasovacGui casovac;
    public boolean prohral = false;

    private int pocetOdkrytych = 0;
    //mohou být public, protože jsou final

    public final int vyska;
    public final int sirka;
    private final Policko[][] policka;
    private boolean JSOU_VYGENEROVANE_MINY = false;
    private final int POCET_MIN;

    public Pole(int vyska, int sirka, int pocetMin) {
        this.vyska = vyska;
        this.sirka = sirka;
        this.policka = new Policko[sirka][vyska];
        //this.gui = new Gui(vyska,sirka);
        this.POCET_MIN = pocetMin;
        zapln();//vygeneruje výchozí políčka do pole
        
    }
    
    
    
    //V nové verzi se již nepoužívá, ale zůstalo to tu, mohlo by se hodit při vylepšování
    private int[][] vygenerujPoziceMin(){
        Random random = new Random();
        //pole pozic [x,y]
        int[][] poziceMin = new int[POCET_MIN][2];
        for (int i = 0; i < POCET_MIN; i++) {
            int[] poziceJedneMiny = new int[2];
            poziceJedneMiny[0] = random.nextInt(vyska); // osa y
            poziceJedneMiny[1] = random.nextInt(sirka); // osa x
            poziceMin[i] = poziceJedneMiny;
        }
        
        return poziceMin;
    }
    
    public void nastavMiny(Policko polickoPrvnihoKliku){
        Random random = new Random();
        int kolikUmisteno = 0;

        int klikX = polickoPrvnihoKliku.getX();
        int klikY = polickoPrvnihoKliku.getY();
        
        //umisťuje dokud neumístí všechny co má
        while (kolikUmisteno < POCET_MIN) {
            //vygeneruje si pozici
            int x = random.nextInt(sirka);
            int y = random.nextInt(vyska);

            //policko na te pozici
            Policko p = policka[x][y];
            
            //je v sousedství políčka prvního kliku, popřípadě přímo pozioce prvního kliku?
            //tam nesmí být, protože jinak by první klk nebyl nula
            //zkontroluje zda není od prvního kliku o jedno vedle
            boolean jeVOkoli = x >= klikX - 1 && x <= klikX + 1 && y >= klikY - 1 && y <= klikY + 1;
            
            //umístí se a započte jako umístěné pouze v případě, že není splněná horní podmínka a že už není minou (to by se totiž lišil počet min od POCET_MIN)
            if (!p.jeMina() && !jeVOkoli) {
                p.setJeMina(true);
                kolikUmisteno++;
            }
            
            //pro případné testování
            //System.out.println("Mina na: " + x + "," + y);
            

        }
        //System.out.println(this); //vypíše správné řešení.
        JSOU_VYGENEROVANE_MINY = true;
    }
    
    //zaplní oběkty políček
    private void zapln() {
        for (int x = 0; x < sirka; x++) {
            for (int y = 0; y <  vyska; y++ ) {
 
                policka[x][y] = new Policko(x, y, this);
                
            }
        }
    }
    
    public void vyhra(){
        if(!prohral){//pokud už prohrál, tak ho hra nechá hrát dál, ale díky tomuto už pak nemůže vyhrát.
            casovac.zastav();
            JOptionPane.showMessageDialog(null, "Vyhrál jsi.", "Dobrá práce.", JOptionPane.OK_OPTION);
            Rekord.zkontrolujRekord(vyska, sirka, POCET_MIN, casovac.getCas());
        }
    }
    
    public boolean jsouVygenerovaneMiny(){
        return JSOU_VYGENEROVANE_MINY;
    }
    public Policko getPolicko(int x,int y){
        return policka[x][y];
    }
    public void setCasovac(CasovacGui casovac) {
        this.casovac = casovac;
    }
    public void zvysPocetOdkrytych(){
        pocetOdkrytych++;
        
        //pokud už jsou všechny odkrytelná políčka odkryta, tak ukončí hru
        if(pocetOdkrytych + POCET_MIN >= vyska * sirka){
            vyhra();
        }
    }


    



    //pro testovací účely
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Policko[] radek: policka) {
            for (Policko policko : radek) {
                sb.append(policko);
            }
            sb.append('\n');
        }   
        return sb.toString();
    }
}
