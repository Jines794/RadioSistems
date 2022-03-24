/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NOYAU;

import library.interfaces.ClientHL7;
import library.interfaces.ServeurHL7;
import library.interfaces.Patient;


/**
 *
 * @author Seuad
 */
public class Interoperabilite {

    private ServeurHL7 serveur = new ServeurHL7();
    private ClientHL7 c = new ClientHL7();
   
   
    

    public Interoperabilite() {

    }

    public void fermerServeur() {
        serveur.fermer();
    }

    public Patient recevoirMessage(int port) {

        serveur.ecoute(); //ecoute du serveur
        String messageHL7 = serveur.protocole(); //la méthode protocole(): traiter le message reçu. Le message HL7 est analysé et convertie en objets Java :- Patient- Message
        System.out.println("message "+messageHL7);//La méthode retourne le message HL7 reçu sous forme de chaîne de caractères.
        Patient pat;
        pat = serveur.getPatient(); 
        
            
        
        System.out.println("Id:" +pat.getID());
        System.out.println("Nom: " +pat.getFamillyName());
        System.out.println("Prénom: " +pat.getFirstName());
        System.out.println("Sexe: " +pat.getCharSex());
        System.out.println("Date de naissance " +pat.getBirth());
        System.out.println("Service : " +pat.getPatClass());
        
       //inserer dans la bd
  
        
        return pat;
        
        
    }

    public void envoyerPatient(Patient p, String host, int port) {
        
       c.connexion(host, port); 
       c.admit(p); //admission d'un patient
       

    }

    public void connection(int port) {
        serveur = new ServeurHL7();
        serveur.connection(port);
        System.out.println("Connection HL7");
    }
}
