/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template

Toto je domácí verze souborů z programování.
 */
package cz.honza.miny;

import static cz.honza.miny.Znacka.NIC;
import static cz.honza.miny.Znacka.ODHALENE;
import static cz.honza.miny.Znacka.URCITE;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;


/**
 *
 * @author honza
 */

public class Gui extends JFrame {
    public final int VYSKA;
    public final int SIRKA;
    private CasovacGui casovac;
    private Pole pole;

    JButton[][] tlacitka;
    public Gui(int vyska,int sirka,Pole pole){
        super("miny"); 
        this.casovac = new CasovacGui();
        this.pole = pole;
        this.SIRKA = sirka;
        this.VYSKA = vyska;
        this.tlacitka = new JButton[sirka][vyska];
        pole.setCasovac(casovac);
    }
    
    public void zobraz(){
        Gui okno = this;
        okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//nezruší celou aplikaci vypnutím
        okno.setSize(SIRKA * 70, VYSKA * 70);
        okno.setLocationRelativeTo(null);
        okno.setLayout(new GridLayout(VYSKA, SIRKA));
       
        //zaplní tlačítky
        for (int y = 0; y < VYSKA; y++) {//      pro každý sloupec
             for (int x = 0; x < SIRKA; x++) {// a každý řádek
                JButton t = vytvorTlacitko();//  vytvoří tlačítko se všemi poslouchači barvami atd.
                Policko policko = pole.getPolicko(x, y);
                
                //spojení tlačítka a políčka které reprezentuje
                policko.setTlacitko(t);
                t.putClientProperty("policko", policko);
                okno.add(t);
                tlacitka[x][y] = t;
            }
        }

        okno.setVisible(true);//vše je připravené, okno se zobrazí.
        
        
        //zavření časovače při zavření okna.:
        //listener na zavření okna
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                casovac.dispose();  // zavře okno s časem
            }
        });

    }
    
    private JButton vytvorTlacitko(){
        JButton t = new JButton("");
        
        t.setBackground(Barva.POZADI);
        
        //přiřadí poslouchače událostí
        t.addActionListener(new PoslouchacLevy());
        t.addMouseListener(new PoslouchacPravy());
 
        return t;
    }
    
    
    
    
    //poslouchače událostí tlačítek:
    
    private class PoslouchacPravy extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {//pouze pro pravý klik
                JButton tlacitko = (JButton) e.getSource();
                Policko policko = (Policko) tlacitko.getClientProperty("policko");
                if (policko != null) {//pokud není políčko přiřazené, tak se nic nedělá, protože není důvod => nemelo by nastat

                    //po zmáčknutí pravého tlačítka myši se má změnit značka políčka, to ovšem záleží na tom co to políčko zrovna je. (jakou má značku)
                    switch (policko.getZnacka()) {
                        case NIC:
                            policko.setZnacka(Znacka.URCITE);
                            tlacitko.setText("P");
                            tlacitko.setBackground(Barva.VLAJKA);
                            break;
                        case URCITE:
                            policko.setZnacka(Znacka.MOZNA);
                            tlacitko.setText("?");
                            tlacitko.setBackground(Barva.VLAJKA);
                            break;
                        case ODHALENE:
                            break;//nedělá nic, nemá cenu označovat odhalené políčko
                        default:
                            policko.setZnacka(Znacka.NIC);
                            tlacitko.setText("");
                            tlacitko.setBackground(Barva.POZADI);
                            break;
                    }
                }
            }
        }
    }
    
    
    private class PoslouchacLevy implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton stisknuto = (JButton) e.getSource();
            Policko p = (Policko) stisknuto.getClientProperty("policko");
            
            if (p != null) {//pokud by policko neexistovalo, tak to nic neudela
                if(!pole.jsouVygenerovaneMiny()){//první klik musí nastavit pozice min v závislosti na jeho pozici, aby první klk byl vždy na nulu.
                    pole.nastavMiny(p);
                    casovac.spust();//časovač se spustí poté, co hráč už poprvé klikne.
                }
                if(p.getZnacka() == Znacka.ODHALENE){//odhalené políčko se nemá odkrývat, ale má odrkýt sousedy
                    p.odkryjZnameSousedy();
                }else{
                    p.odkryjSe();
                }
                
            }else{
                System.out.println("Policko neexistuje");
           }
        }
    }
    



}

