/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author François
 */
public class IdPacs {
    private int idPacs;
    /**
     * Générer un identifiant pour une image 
     *
     * @param conn connexion � la base de donn�es
     * @throws SQLException en cas d'erreur d'acc�s � la base de donn�es
     */
 public IdPacs(Connection conn) throws SQLException{
    
        int maxId=0;
        String stringId;
        
        // Get a statement from the connection
        Statement stmt = conn.createStatement();
        
        //Tester si la table est vide
        ResultSet rs = stmt.executeQuery("select count(*) from pacs");
        rs.next();
        if (rs.getInt(1)==0){
            this.idPacs=1000000000;
        }
        
        else{
        // Execute the query
        ResultSet rs2 = stmt.executeQuery("SELECT MAX(pacsId) FROM PACS");

        while (rs2.next()) {

            maxId = rs2.getInt(1);//on récupère l'id max
            maxId+=1;//On lui ajoute 1
            
            stringId = Integer.toString(maxId);//On le convertit en String
            

            String maxIdS = Integer.toString(maxId); 
            int longueur = maxIdS.length();//On prend le nombre de chiffre différent de zéro

            for (int i = 0; i < (10 - longueur); i++) { //on construit un identifiant de longueur 10 en mettant des zéros pour combler
                maxIdS = maxIdS + "0";
                
            }
            idPacs = Integer.parseInt(maxIdS);

        }}
        // Close the result set, statement and theconnection 
        rs.close();
        stmt.close();


    }
      

    public int getIdPacs() {
       return idPacs;
      
    }
    



}