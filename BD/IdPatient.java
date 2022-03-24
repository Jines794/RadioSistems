package BD;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author François
 */
public class IdPatient {
    String idPatient;
    /**
     * Générer un identifiant pour un patient
     *
     * @param conn connexion � la base de donn�es
     * @throws SQLException en cas d'erreur d'acc�s � la base de donn�es
     */
    public IdPatient(Connection conn) throws SQLException{
        String maxId;
        int intId;
        String newId = "";
        // Get a statement from the connection
        Statement stmt = conn.createStatement();
        
        //Tester si la table est vide
        ResultSet rs = stmt.executeQuery("select count(*) from patient");
        rs.next();
        if (rs.getInt(1)==0){
            this.idPatient="0000000000";
        }
        else{
        // Execute the query
        ResultSet rs2 = stmt.executeQuery("SELECT MAX(patientId) FROM Patient");
        System.out.println("voilaaa");

        while (rs2.next()) {

            maxId = rs2.getString(1);
            System.out.println(maxId);

            intId = Integer.parseInt(maxId);//On convertit l'identifiant max en int
            intId = intId + 1;//On lui ajoute 1

            String intS = Integer.toString(intId);//On convertit en String à nouveau
            int longueur = intS.length();//On prend le nombre de chiffre différent de zéro

            for (int i = 0; i < (10 - longueur); i++) { //on construit un identifiant de longueur 10 en mettant des zéros pour combler
                newId = newId + "0";
                System.out.println(newId);
            }
            newId = newId + Integer.toString(intId);
            this.idPatient = newId;

        }
        }
        // Close the result set, statement and theconnection 
        rs.close();
        stmt.close();

     
    }
      

    public String getIdPatient() {
        return idPatient;
    }
    



}
