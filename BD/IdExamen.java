/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;


import java.sql.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author François
 */
public class IdExamen {
    String idExamen;
    /**
     * Générer un identifiant pour un examen
     *
     * @param conn connexion � la base de donn�es
     * @throws SQLException en cas d'erreur d'acc�s � la base de donn�es
     */
    public IdExamen(Connection conn) throws SQLException{
   
        String maxId;
        int intId;
        String newId = "";
        // Get a statement from the connection
        Statement stmt = conn.createStatement();
        //Tester si la table est vide
        ResultSet rs = stmt.executeQuery("select count(*) from exam");
        rs.next();
        if (rs.getInt(1)==0){
            this.idExamen="0000000000";
        }
        else{
        // Execute the query
        ResultSet rs2 = stmt.executeQuery("SELECT MAX(ExamId) FROM EXAM");

        while (rs2.next()) {

            maxId = rs2.getString(1);
            System.out.println("maxiD :"+maxId);

            intId = Integer.parseInt(maxId);//On convertit l'identifiant max en int
            System.out.println("le max en int : "+intId);
            intId = intId + 1;//On lui ajoute 1

            String intS = Integer.toString(intId);//On convertit en String à nouveau
            int longueur = intS.length();//On prend le nombre de chiffre différent de zéro
            System.out.println("nombre de chiffres différents de 0 "+longueur);

            for (int i = 0; i < (10 - longueur); i++) { //on construit un identifiant de longueur 10 en mettant des zéros pour combler
                newId = newId + "0";
                System.out.println(newId);
            }
            newId = newId + Integer.toString(intId);
            idExamen = newId;

        }}
        // Close the result set, statement and theconnection 
        rs.close();
        stmt.close();

        
    }
    

    public String getIdExamen() {
        return idExamen;
    }
}
