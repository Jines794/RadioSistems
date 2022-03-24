/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NOYAU;

import BD.ExamenBD;
import BD.PatientsBD;
import BD.PersonnelBD;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author sosso
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException{

        ExamenBD exam = new ExamenBD();
        exam.updateCR("dsddv000000000000", "0000000000");
        /*Date date;
        date = new Date("16/09/76");
        Genre g = Genre.M;
        Patient p1 = new Patient("Fortun","Laurent","45, boulevard de la Liberation 13012 MARSEILLE",g,date);
        */
        /*BdConnection bd = new BdConnection();
        Connection con = bd.getConnexion();
        
       IdPro idpro = new IdPro(con);
       ManipulateurRadio p = new ManipulateurRadio(idpro,"vwdfztrjj","Artan","Léa");*/
        
        //PersonnelBD pbd = new PersonnelBD();
        //pbd.insererUnPerso2("mdp123" ,"Delfino" ,"Mike" , "4");
        //System.out.println(pbd.recupPrenomNom("0000000000"));
 
        //String a = pbd.getMdp("0000000000");
        //System.out.println("lala "+a);*/
        
        /*PatientsBD pbd = new PatientsBD();
        System.out.println(pbd.recupSexe("1111111111"));
        
        PersonnelBD p = new PersonnelBD();
        System.out.println(p.recupNom("0000000001"));*/
        
     
        System.out.println(exam.recupContenuCR1("0000000000"));
    }
    
}
