/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NOYAU;

/**
 *
 * @author Seuad
 */
import java.util.Date;
import library.interfaces.Patient;




public class TestInteropp {
    private final static Interoperabilite inter = new Interoperabilite();

    
   

    public static void main(String[] args) {

        //inter.connection(8882);
        Date dateN = new Date();

        char service = 'E';   // E Emergency/I Inpatient/ O Outpatient /P Preadmit /R Recurring patient /B Obstetrics /C Commercial Account/N Not Applicable
        char sexe = 'F';
        Patient p = new Patient(101, "FREDERIC", service); //envoi d'un patient (id,nom, classP)
        p.setFirstName("Solane");
        p.setBirth(dateN);
        p.setDeath(false);
        p.setSex(sexe);

        System.out.println("Id:" + p.getID());
        System.out.println("Prénom :" + p.getFirstName());
        System.out.println("Nom :" + p.getFamillyName());
        System.out.println("Sexe : " + p.getCharSex());
        System.out.println("Service : " + p.getCharPatClass());
        System.out.println("Date de naissance :" + p.getBirth());

        inter.envoyerPatient(p, "localhost", 8882); //interop envoi d'un patient en local(localhost), sur le port 8882
       
        /*System.out.println("RECEVOIR");
        Patient pat = inter.recevoirMessage(88); //Renvoi un message 
       
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
              SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yy");
              String dateString = formater.format(sDate);
             //String dateString= sDate.toString();
             System.out.println("yo "+dateString);
             
             PatientsBD pbd=null;
           try {
               pbd=new PatientsBD();
           } catch (SQLException ex) {
               Logger.getLogger(SecMedicale.class.getName()).log(Level.SEVERE, null, ex);
           }
             
           try {
               pbd.insererUnPatient(nom, prenom,"non renseigné", genre,dateString);
           } catch (SQLException ex) {
               Logger.getLogger(SecMedicale.class.getName()).log(Level.SEVERE, null, ex);
           }*/

}}
