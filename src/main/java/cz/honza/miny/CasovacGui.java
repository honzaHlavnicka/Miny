/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.honza.miny;

/**
 *
 * @author jan.hlavnicka.s
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CasovacGui extends JFrame {
    private Timer timer;
    private int sekundy = 0;
    private JLabel casLabel;

    public CasovacGui() {
        super("Časovač");
        casLabel = new JLabel("Čas: 0:00", SwingConstants.CENTER);
        casLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(casLabel);

        setSize(150, 100);
        setAlwaysOnTop(true);  // zůstane nahoře, aby se nezakryl hlavním oknem
        setLocation(100, 100); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // nezavře hlavní okno
        setVisible(true);
        
        //bude se spouštět každou sekundu:
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sekundy++;
                if(sekundy % 60 <10){ //počet sekund je jednočíslicový => musí se pře něj vložit 0
                    casLabel.setText("Čas: " + sekundy / 60 + ":0" + sekundy % 60 );
                }else{
                    casLabel.setText("Čas: "+ sekundy/60 +":" + sekundy % 60 );
                }
                
            }
        });
    }

    public void spust() {
        sekundy = 0;
        timer.start();
    }

    public void zastav() {
        timer.stop();
    }

    public int getCas() {
        return sekundy;
    }

}
