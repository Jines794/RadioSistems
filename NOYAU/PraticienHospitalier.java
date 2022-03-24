package NOYAU;

import BD.PersonnelBD;

import java.sql.SQLException;
import BD.IdPro;


public class PraticienHospitalier extends Personnel {
    private static final String FONCTION ="1";
    public PraticienHospitalier(IdPro id, String mdp,String nom, String prenom) throws SQLException {
        super(id,mdp,nom,prenom);
        PersonnelBD personnel= new PersonnelBD();
        personnel.insererUnPerso(id, mdp, nom, prenom, FONCTION);
    
    }
    public void ecrireCompteRendu(){
        
    }
}
