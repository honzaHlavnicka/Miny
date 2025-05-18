/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template

Toto je domácí verze souborů z programování.
 */
package cz.honza.miny;

import java.awt.Color;

/**
 *
 * @author honza
 */
public class Barva {
    
    //stará verse barev
    /*private static final Color[] BARVY_CISEL = {
        new Color(156, 255, 122),//0
        new Color(30, 171, 214),//1
        new Color(30, 214, 208),//2
        new Color(193, 214, 30),//3
        new Color(181, 162, 20),//4
        new Color(235, 169, 16),//5
        new Color(235, 129, 16),//6
        new Color(230, 110, 41),//7
        new Color(128, 56, 5),//8
    };*/
    
    
    //barvy odpovídajícím políčkům s číslem (=index pole)
    private static final Color[] BARVY_CISEL = {
        new Color(237, 232, 232),
        new Color(212, 245, 174),
        new Color(244, 245, 174),
        new Color(247, 222, 171),
        new Color(230, 190, 145),
        new Color(255, 195, 189),
        new Color(255, 195, 189),
        new Color(230, 142, 133),
        new Color(196, 109, 100),
    };

    public static final Color POZADI = new Color(148, 148, 148);
    //public static final Color VLAJKA = new Color(132, 150, 150);
    public static final Color VLAJKA = new Color(55, 95, 122);
    public static final Color ZACATEK_POZADI = Color.white;
    
    
    
    static Color barvaCisla(int cislo){
        if (cislo < 0 || cislo > BARVY_CISEL.length) return Color.BLACK;//pro neexistující číslo - nemělo by nastat
        return BARVY_CISEL[cislo];
    }
    
}
