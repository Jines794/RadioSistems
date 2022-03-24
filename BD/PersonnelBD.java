/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

/*import bd.crypto.SHA256;
import bd.crypto.aes.AESGCM;*/
import BD.crypto.SHA256;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author François
 */
public class PersonnelBD {
    
    private String proId ;
    private String password ;
    private String lastName ;
    private String firstName ;
    private String function ;
    
    private Connection con;
    //private AESGCM aes;
    
    public PersonnelBD() throws SQLException {
        this.con = BdConnection.getConnexion();
        //this.aes = new AESGCM(128, true);
    }
    
    //Permet de récupérer la liste du personnel 
    public void listperso() throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT * FROM LOGIN";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String proId  = rs.getString(1);
                String firstName  = rs.getString(4);
                String lastName  = rs.getString(3);
                
//                System.out.println("id: "+ rs.getInt("proId "));
//                System.out.println("prénom: "+ rs.getInt("firstName "));
//                System.out.println("nom: "+ rs.getInt("lastName "));
            }
        } finally {

            if (st != null) {
                st.close();
            }
            if (con != null) {
                // con.close();
            }
        }

    }
    
    /**
   * Recupere une chaine de caractere concernant le membre du personnel
   * @param proId 
   * @return String 
   * @throws SQLException 
   */
    public String recupIdPerso(String proId ) throws SQLException {
        Statement st = null;
        
           try{
            st = con.createStatement();
            String query = "SELECT proId FROM LOGIN WHERE proId = '" + proId  + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                proId  = rs.getString(1);
                
                System.out.println("Membre trouvé :");
                System.out.println("id :" + proId );
            }}
         finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        /*if (proId  == null) {
            return "";

        } else {*/

            return proId ;
        //}
    }
    
    /**
 * Recupere un membre en focntion de son nom,prenom 
 * @param firstName  Prenom du membre
 * @param lastName  Nom du membre
 * @return
 * @throws SQLException 
 */
    public String recupPerso(String firstName , String lastName ) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT * FROM LOGIN WHERE lastName  = '" + lastName  + "' AND firstName  = '" + firstName  + "'";

            //st.executeUpdate("INSERT INTO PatientsBD (firstName , lastName ) VALUES ('Thomas','Martin'), ('Samuel','santos')");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                proId  = rs.getString(1);
                firstName  = rs.getString(4);
                lastName  = rs.getString(3);
             

            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        if (proId  == null) {
            return "";

        } else {

            return proId  + " " + firstName  + " " + lastName ;
        }

    }
    
    /**récupère le nom et prénom du membre selon l'ID
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public String recupPrenomNom(String id) throws SQLException {
        Statement st = null;
        String nom="";
        String prenom="";
        try {

            st = con.createStatement();
            String query = "SELECT firstname, lastname FROM LOGIN WHERE proId  = '" + id  + "'";

            //st.executeUpdate("INSERT INTO PatientsBD (firstName , lastName ) VALUES ('Thomas','Martin'), ('Samuel','santos')");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                prenom  = rs.getString(1);
                nom  = rs.getString(2);
             

            }
        } finally {

            if (st != null) {
                st.close();
            }

 

            return prenom  + " " + nom ;
        }

    }
    
  /**Recupere l'id du membre avec son nom prenom 
 * 
 * @param firstName  Prenom du membre
 * @param lastName  Nom du membre
 * @return String identifiant du membre  
 * @throws SQLException 
 */
    public String recupIdPerso(String firstName , String lastName ) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT proId FROM LOGIN WHERE lastName  = '" + lastName  + "' AND firstName  = '" + firstName  + "'";

            
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                proId  = rs.getString(1);

            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        if (proId  == null) {
            return "";

        } else {

            return proId ;
        }

    }
    /**
 * Ajoute un membre a la base de données NON VISIBLE SUR L'INTERFACE, A DESTINATION DU SI
     * @param id
 * @param password 
 * @param firstName 
 * @param lastName 
 * @param function 
 
 * @throws SQLException 
 */
    public void insererUnPerso(IdPro id, String password , String lastName , String firstName , String function ) throws SQLException {
        Statement st = null;
        
        try {
 
            
            //FAIRE UN CHECK DE LA FONCTION ENTRE 1 ET 5

            //String query = "INSERT INTO LOGIN VALUES (('" + proId  + "'),('" + password  + "'),('" + lastName  + "'),('" + firstName  + "'),('"+ function  + "'))";
            String query = "INSERT INTO LOGIN VALUES (('" + proId  + "'),('" + SHA256.hash(password)  + "'),('" + lastName  + "'),('" + firstName  + "'),('" + function  + "'))";
            System.out.println("Requete :" + query);
            st = con.createStatement();
            st.executeUpdate(query);

        

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    /**Ajoute un membre a la base de données NON VISIBLE SUR L'INTERFACE, A DESTINATION DU SI
     *
     * @param password
     * @param lastName
     * @param firstName
     * @param function
     * @throws SQLException
     */
    public void insererUnPerso2(String password , String lastName , String firstName , String function ) throws SQLException {
        Statement st = null;
        IdPro idp= new IdPro(con);
        this.proId= idp.getIdPro();
        try {
 
            
            //FAIRE UN CHECK DE LA FONCTION ENTRE 1 ET 5

            //String query = "INSERT INTO LOGIN VALUES (('" + proId  + "'),('" + password  + "'),('" + lastName  + "'),('" + firstName  + "'),('"+ function  + "'))";
            String query = "INSERT INTO LOGIN VALUES (('" + proId  + "'),('" + SHA256.hash(password)  + "'),('" + lastName  + "'),('" + firstName  + "'),('" + function  + "'))";
            System.out.println("Requete :" + query);
            st = con.createStatement();
            st.executeUpdate(query);

        

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    /**
 * Recherche si un membre existe 
 * @param proId  id pro 
 * @return Boolean 
 * @throws SQLException 
 */
    public Boolean rechIdPerso(String proId ) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT proId FROM LOGIN WHERE proId  = '" + proId  + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                proId  = rs.getString(1);

            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        if (proId  == null) {
            return false;

        } else {

            return true;
        }

    }
    
    
    /**
 * Recupere la liste de tout les membre 
 * @param model
 * @return DefaultTableModel
 * @throws SQLException 
 */
    public DefaultTableModel listePersoModel(DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT proId, lastName , firstName , function  FROM LOGIN ";
           

           
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String proId  = rs.getString(1);
                String lastName= rs.getString(3);
                String firstName  = rs.getString(4);
                String function  = rs.getString(5);

                Vector row = new Vector(4);// Vector Qui fait la ligne
                row.add(proId );
                row.add(lastName );
                row.add(firstName );
                row.add(function );

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  

            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        return model;

    }
    /**
      *  recupere les membres en function  du nom et du prenom
      * @param firstName 
      * @param lastName 
      * @param model
      * @return
      * @throws SQLException 
      */
     public DefaultTableModel  rechPatNomPrenom(String firstName ,String lastName ,DefaultTableModel model) throws SQLException {
       
         Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT proId, lastName , firstName , function   FROM LOGIN WHERE firstName   = '"+firstName +"'AND lastName ='"+lastName +"'";
            

            
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                
                String proId  = rs.getString(1);
                String lastName1  = rs.getString(2);
                String firstName1 = rs.getString(3);
                String dateDeNaissancePa = rs.getString(6);

                Vector row = new Vector(4);// Vector Qui fait la ligne
                row.add(proId );
                row.add(lastName1 );
                row.add(firstName1);
                row.add(dateDeNaissancePa);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  

            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        return model;
     }
     
         /**
 * Recherche la function  d'un membre via son id
 * @param proId  id pro 
 * @return Boolean 
 * @throws SQLException 
 */
    public String rechFuncId(String proId ) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT function  FROM LOGIN WHERE proId = '" + proId  + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                function  = rs.getString(1);

            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        if (proId  == null) {
            return "Erreur";

        } else {

            return function ;
        }

    }
    
     /**
   * Recupere une chaine de caractere concernant le membre du personnel
   * @param proId 
     * @param passwordEntree 
   * @return String 
   * @throws SQLException 
   */
    public Boolean identification(String proId , String passwordEntree ) throws SQLException {
        Statement st = null;
        Boolean test = false;
        
        try {
            System.out.println("hello");
           
            st = con.createStatement();
            String query = "SELECT * FROM LOGIN WHERE proId = '" + proId  + "'" /*AND password  = '"+ SHA256.hash(passwordEntree) + "'"*/;

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                proId  = rs.getString(1);
                password  = rs.getString(2);
                System.out.println("mdp dans la bd hash : "+password);
                System.out.println("mdp qu'on a entré hash : "+SHA256.hash(passwordEntree));
                
                System.out.println("Membre trouvé :");
                System.out.println("bienvenue"+ proId  + " !" );
            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        if (proId  == null && password  == null) {
            test = false;
            return test;

        } else {
            if (SHA256.hash(passwordEntree).equals(password)){
                test=true;
            }
            else{
                test=false;
            }
            //test=SHA256.checkHash(password,passwordEntree);
            //test = true;
            return test;
        }
    }
    
    /**récupètre le nom du membre 
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public String recupNom(String id) throws SQLException {
        Statement st = null;
        String nom="";
        
        try {

            st = con.createStatement();
            String query = "SELECT lastname FROM LOGIN WHERE proId  = '" + id  + "'";

            //st.executeUpdate("INSERT INTO PatientsBD (firstName , lastName ) VALUES ('Thomas','Martin'), ('Samuel','santos')");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                
                nom  = rs.getString(1);
             

            }
        } finally {

            if (st != null) {
                st.close();
            }

 

            return  nom ;
        }

    }
    
        public String recupPrenom(String id) throws SQLException {
        Statement st = null;
        String prenom="";
        
        try {

            st = con.createStatement();
            String query = "SELECT firstname FROM LOGIN WHERE proId  = '" + id  + "'";

            //st.executeUpdate("INSERT INTO PatientsBD (firstName , lastName ) VALUES ('Thomas','Martin'), ('Samuel','santos')");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                
                prenom  = rs.getString(1);
             

            }
        } finally {

            if (st != null) {
                st.close();
            }

 

            return  prenom ;
        }

    }
         /**
     *
     * @param proId
     * @return
     * @throws SQLException
     */
        public String recupNomPerso(String proId ) throws SQLException {
        Statement st = null;
        String lastname ="";
        try {
           
            st = con.createStatement();
            String query = "SELECT lastName  FROM LOGIN WHERE proId = '" + proId  + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
               
              
                lastName = rs.getString(1);
                
            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        if (proId  == null) {
            return "";

        } else {

            return firstName  + " " + lastName;
        }
    }
    
    
   
    
}