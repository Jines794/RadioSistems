package NOYAU;

import BD.PatientsBD;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Patient { 
    private String nom; 
    private String prenom;   
    private String idPatient;  
    private Date dateNaissance;
    private Genre genre;  
    private String adresse;
    

    public Patient(String nom, String prenom, String adresse, Genre genre,Date dateNaissance) throws SQLException {
        this.nom=nom;
        this.prenom=prenom;
        this.dateNaissance=dateNaissance;
        this.adresse=adresse;
        this.genre=genre;
      
        
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy");
        
        PatientsBD pbd = new PatientsBD();
        pbd.insererUnPatient(nom,prenom,adresse,genre.toString(),formater.format(dateNaissance));
        
        //creerDMR(idPatient);//création du DMR vide du patient
    }

  //Obtenir des informations à propos du patient
    
    Date getDateNaissance() {
        return this.dateNaissance;
    }

    Genre getGenre() {
        return this.genre;
    }
   
    String getNom() {
        return this.nom;
    }

    
    void setNom(String value) {
        this.nom = value;
    }

    
    String getPrenom() {
        return this.prenom;
    }

    
    String getIdPatient() {
        return this.idPatient;
    }

    
    String getAdresse(){
        return this.adresse;
    }
  
    
    //Renvoir une chaine de caractère avec les coordonnées du patient
    public String toStringPatient() {
        String titre = "";
        if (this.genre.equals("F")){
            titre = "monsieur";
        }
        else{
            titre="madame";
        }
        return titre + " " + this.nom + this.prenom + "\n" + "Adresse : " + this.adresse + "\n" + "Date de naissance : " + this.dateNaissance;
    }
    
   
    public void creerDMR(String idPatient) {
        DossierMedicalRadiologique DMR = new DossierMedicalRadiologique(this.idPatient); //ouvre un DMR vide pour le patient
    }
    
    

}
