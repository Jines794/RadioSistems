/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NOYAU;

import BD.PatientsBD;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.interfaces.ClientHL7;
import library.interfaces.ServeurHL7;
import library.interfaces.Patient;

/**
 *
 * @author Seuad
 */
public class ReceptionHL7 extends Thread{
     private final static Interoperabilite inter = new Interoperabilite();
     private  PatientsBD patients = new PatientsBD();
     

    public ReceptionHL7(Connection con) throws SQLException {
        
        
    }

    
     public  void run() {
   
         try {
             
             inter.connection(8882);
             Patient pat = inter.recevoirMessage(8882);
             int id =pat.getID();
             String prenom = pat.getFirstName();
             String nom = pat.getFamillyName();
             Date dateNaissance = pat.getBirth();
             String sexeP = pat.getSex();
             String genre;
             if (sexeP.equals("Male")){
                 genre = "M";
                 
             }else{
                  genre ="F";
             }
             String idPat = String.valueOf(id);
              java.sql.Date sDate = new java.sql.Date(dateNaissance.getTime());
             String dateString= sDate.toString();
             
             patients.insererUnPatient(prenom, nom, dateString, genre,"adress");
            
        } catch (SQLException ex) {
             Logger.getLogger(ReceptionHL7.class.getName()).log(Level.SEVERE, null, ex);
         }
         
             
             
         
 
         
         
         
        
     
}}

 

