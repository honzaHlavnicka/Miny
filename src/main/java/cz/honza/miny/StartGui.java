/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template

Toto je domácí verze souborů z programování.
 */
package cz.honza.miny;

/**
 *
 * @author honza
 */

import javax.swing.*;
import java.awt.*;

public class StartGui extends JFrame {
    public StartGui() {
        setTitle("Miny");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel nadpis = new JLabel("💣Miny", SwingConstants.CENTER);
        nadpis.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(nadpis, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(5, 1, 5, 5));

        JButton lehka = new JButton("Lehká (9x9, 10 min)");
        
        JButton stredni = new JButton("Střední (16x16, 40 min)");
        JButton tezka = new JButton("Těžká (30x16, 99 min)");
        
       
        lehka.setBackground(Barva.barvaCisla(1));
        stredni.setBackground(Barva.barvaCisla(2));
        tezka.setBackground(Barva.barvaCisla(3));
        
        JPanel customPanel = new JPanel(new FlowLayout());
        JTextField sirkaField = new JTextField("10", 4);
        JTextField vyskaField = new JTextField("10", 4);
        JTextField minyField = new JTextField("10", 4);
        JButton vlastni = new JButton("Vlastní");
        
        vlastni.setBackground(Barva.barvaCisla(4));
        
        customPanel.add(new JLabel("Šířka:"));
        customPanel.add(sirkaField);
        customPanel.add(new JLabel("Výška:"));
        customPanel.add(vyskaField);
        customPanel.add(new JLabel("Miny:"));
        customPanel.add(minyField);
        customPanel.add(vlastni);

        center.add(lehka);
        center.add(stredni);
        center.add(tezka);
        center.add(new JLabel("Vlastní nastavení:", SwingConstants.CENTER));
        center.add(customPanel);

        add(center, BorderLayout.CENTER);

        // Akce tlačítek
        lehka.addActionListener(e -> Miny.spustHru(9, 9, 10));       //zjednodušeny zapis, abych nemel vsude kod jenom kvuli udalostem
        stredni.addActionListener(e -> Miny.spustHru(16, 16, 40));
        tezka.addActionListener(e -> Miny.spustHru(30, 16, 99));
        vlastni.addActionListener(e -> {
            try {
                
                //přeparsuje custom udaje do intů
                int sirka = Integer.parseInt(sirkaField.getText());
                int vyska = Integer.parseInt(vyskaField.getText());
                int miny = Integer.parseInt(minyField.getText());
                
                
                if((vyska*sirka) - 8 > miny || miny == 0){//musí mít dostatek políček co nejsou miny (>=9) aby první klk byl na nulu, jinak by se generování min zacyklilo
                    Miny.spustHru(sirka, vyska, miny);
                }else{
                    JOptionPane.showMessageDialog(this, "Počet min musí být o 9 menší než počet políček. (Zmenči počet min pod "+ Integer.toString(sirka*vyska - 8) +")", "Chyba", JOptionPane.ERROR_MESSAGE);
                }
                
                
            } catch (NumberFormatException ex) {//Stringy nejsou čísla
                
                JOptionPane.showMessageDialog(this, "Zadej platná čísla!", "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

}
