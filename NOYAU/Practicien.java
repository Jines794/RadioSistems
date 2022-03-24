/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NOYAU;

import BD.IdPro;
import NOYAU.Personnel;
import BD.PersonnelBD;
import java.sql.SQLException;

/**
 *
 * @author Sonya Ndomkeu
 */
public class Practicien extends Personnel {
    private static final String FONCTION ="2";
    public Practicien(IdPro id, String mdp,String nom, String prenom) throws SQLException {
        super(id,mdp,nom,prenom);
        PersonnelBD personnel= new PersonnelBD();
        personnel.insererUnPerso(id, mdp, nom, prenom, FONCTION);
    
    }
    
}
