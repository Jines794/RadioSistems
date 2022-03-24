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

import java.util.Date;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author François 
 */
//patientId char(10) primary key,lastNameP varchar(150) not null, firstNameP varchar(150) not null, adress varchar(300) not null,
//gender char(1) check(gender='M' OR gender='F') not null, birthDate date not null

public class PatientsBD {

    private String patientId;
    private String lastNameP;
    private String firstNameP;
    private String birthDate;
    private String gender;
    private String adress;

    private int j;

    private Connection con;

    /**
     * Permet de récuper la connection
     *
     * @throws SQLException
     */
    public PatientsBD() throws SQLException {
        this.con = BdConnection.getConnexion();
    }

    public void listpatients() throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT * FROM PATIENT";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String patientId = rs.getString(1);
                String firstNameP = rs.getString(3);

                String lastNameP = rs.getString(2);

//                System.out.println("patientId : "+ rs.getInt("patientId "));
//                System.out.println("prénom: "+ rs.getInt("firstNameP "));
//                System.out.println("nom: "+ rs.getInt("nom"));
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
     * Recupere une chaine de caractere concernant le patient
     *
     * @param patientId
     * @return
     * @throws SQLException
     */
    public String recupIdPat(String patientId) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT * FROM PATIENT WHERE patientId  = '" + patientId + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                patientId = rs.getString(1);
                firstNameP = rs.getString(2);
                lastNameP = rs.getString(3);
                System.out.println("Patient trouvé :");
                System.out.println("id :" + patientId);
            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        if (patientId == null) {
            return "";

        } else {

            return patientId + " " + firstNameP + " " + lastNameP;
        }
    }

    /**récupère l'adresse selon l'ID 
     *
     * @param patientId
     * @return
     * @throws SQLException
     */
    public String recupAdresse(String patientId) throws SQLException {
        Statement st = null;
        String adresseP = "";
        try {

            st = con.createStatement();
            String query = "SELECT adress FROM PATIENT WHERE patientId  = '" + patientId + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                adresseP = rs.getString(1);

            }
        } finally {

            if (st != null) {
                st.close();

            }
        }
        if (patientId == null) {
            return "";

        } else {

            return adresseP;
        }
    }

    /**
     * Recupere un patient en focntion de son nom,prenom
     *
     * @param firstNameP Prenom du patient
     * @param lastNameP Nom du patient
     * @return
     * @throws SQLException
     */
    public String recupPatients(String firstNameP, String lastNameP) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT * FROM PATIENT WHERE lastNameP  = '" + lastNameP + "' AND firstNameP  = '" + firstNameP + "'";

            //st.executeUpdate("INSERT INTO PatientsBD (firstNameP , nom) VALUES ('Thomas','Martin'), ('Samuel','santos')");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                patientId = rs.getString(1);
                firstNameP = rs.getString(3);
                lastNameP = rs.getString(2);

            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        if (patientId == null) {
            return "";

        } else {

            return patientId + " " + firstNameP + " " + lastNameP;
        }

    }

    /**
     * Recupere l'id du patient avec son nom prenom
     *
     * @param firstNameP Prenom du patient
     * @param lastNameP Nom du patient
     * @return String identifiant du patient
     * @throws SQLException
     */
    public String recupIdPatients(String firstNameP, String lastNameP) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT patientId  FROM PATIENT WHERE lastNameP  = '" + lastNameP + "' AND firstNameP  = '" + firstNameP + "'";

            //st.executeUpdate("INSERT INTO PatientsBD (firstNameP , lastNameP ) VALUES ('Thomas','Martin'), ('Samuel','santos')");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                patientId = rs.getString(1);

            }
        } finally {

            if (st != null) {
                st.close();

            }
            if (con != null) {
                //   con.close();

            }
        }
        if (patientId == null) {
            return "";

        } else {

            return patientId;
        }

    }

    /**
     * Ajoute un patient a la base de données
     *
     * @param firstNameP
     * @param lastNameP
     * @param birthDate
     * @param gender
     * @param adress
     * @throws SQLException
     */
    public void insererUnPatient(String lastNameP, String firstNameP, String adress, String gender, String birthDate) throws SQLException {
        Statement st = null;
        IdPatient idp = new IdPatient(con);
        this.patientId = idp.getIdPatient();
        try {

            String query = "INSERT INTO PATIENT VALUES (('" + patientId + "'),('" + lastNameP + "'),('" + firstNameP + "'),('" + adress + "'),('" + gender + "'),('" + birthDate + "'))";
            System.out.println("Requete :" + query);
            st = con.createStatement();
            st.executeUpdate(query);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Recherche si un patient existe
     *
     * @param patientId id patient
     * @param nom
     * @param prenom
     * @return Boolean
     * @throws SQLException
     */
    public Boolean rechIdPatients(String patientId, String nom, String prenom) throws SQLException {
        Statement st = null;

        st = con.createStatement();
        String query = "SELECT count(*) FROM PATIENT WHERE patientId  = '" + patientId + "' and lastnameP = '" + nom + "' and firstnamep  = '" + prenom + "'";

        ResultSet rs = st.executeQuery(query);

        rs.next();
        if (rs.getInt(1) == 0) {
            return false;
        } else {
            return true;
        }

    }
    
    /**vérifie si le patient existe (V2)
     *
     * @param patientId
     * @return
     * @throws SQLException
     */
    public Boolean rechIdPatients2(String patientId) throws SQLException {
        Statement st = null;

        st = con.createStatement();
        String query = "SELECT count(*) FROM PATIENT WHERE patientId  = '" + patientId + "'";

        ResultSet rs = st.executeQuery(query);

        rs.next();
        if (rs.getInt(1) == 0) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Recupere la liste de tout les patients
     *
     * @param model
     * @return DefaultTableModel
     * @throws SQLException
     */
    public DefaultTableModel listePatientModel(DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT patientId , lastNameP , firstNameP, gender, birthDate  FROM PATIENT ";

            //st.executeUpdate("INSERT INTO PatientsBD (firstNameP , lastNameP ) VALUES ('Thomas','Martin'), ('Samuel','santos')");
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String patientId = rs.getString(1);
                String lastNameP = rs.getString(2);
                String firstNameP = rs.getString(3);
                String gender = rs.getString(4);
                Date birthDate = rs.getDate(5);

                Vector row = new Vector(4);// Vector Qui fait la ligne
                row.add(patientId);
                row.add(lastNameP);
                row.add(firstNameP);
                row.add(gender);
                row.add(birthDate);

                System.out.println(row.toString());
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
     * recupere les patients en fonction du prenom, du nom et de la date de
     * naissance
     *
     * @param firstNameP
     * @param lastNameP
     * @param birthDate date de Naissance
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel rechPatPrenomNomDate(String firstNameP, String lastNameP, String birthDate, DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT patientId , lastNameP , firstNameP, gender , birthDate  FROM PATIENT WHERE firstNameP   = '" + firstNameP + "'AND lastNameP ='" + lastNameP + "'AND birthDate ='" + birthDate + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                String patientId = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                String dateDeNaissancePa = rs.getString(5);

                Vector row = new Vector(4);// Vector Qui fait la ligne
                row.add(patientId);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
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
     * recupere les patients en fonction du nom et du prenom
     *
     * @param firstNameP
     * @param lastNameP
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel rechPatNomPrenom(String firstNameP, String lastNameP, DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT patientId , lastNameP , firstNameP, gender , birthDate FROM PATIENT WHERE firstNameP   = '" + firstNameP + "'AND lastNameP ='" + lastNameP + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String patientId = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                String dateDeNaissancePa = rs.getString(5);

                Vector row = new Vector(4);// Vector Qui fait la ligne
                row.add(patientId);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
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
     * recupere les patients en fonction du prenom, du nom et de la date de
     * naissance et ID
     *
     * @param firstNameP
     * @param lastNameP
     * @param birthDate date de Naissance
     * @param patientId
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel rechPatPrenomNomDateID(String firstNameP, String lastNameP, String birthDate, String patientId, DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT patientId , lastNameP , firstNameP, gender , birthDate  FROM PATIENT WHERE patientId   = '" + patientId + "'AND firstNameP ='" + firstNameP + "'AND lastNameP ='" + lastNameP + "'AND birthDate ='" + birthDate + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                String patientId1 = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                String dateDeNaissancePa = rs.getString(5);

                Vector row = new Vector(4);// Vector Qui fait la ligne
                row.add(patientId);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
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
     * recupere les patients en fonction ID
     *
     * @param patientId
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel rechPatID(String patientId, DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT patientId , lastNameP , firstNameP, gender , birthDate FROM PATIENT WHERE patientId   = '" + patientId + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                String patientId1 = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                String dateDeNaissancePa = rs.getString(5);

                Vector row = new Vector(4);// Vector Qui fait la ligne
                row.add(patientId);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
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

    /**récupère la date de naissance selon un Id  
     *
     * @param patientId
     * @return
     * @throws SQLException
     */
    public Date recupDateN(String patientId) throws SQLException {
        Statement st = null;
        Date date = null;
        try {

            st = con.createStatement();
            String query = "SELECT birthdate FROM PATIENT WHERE patientId  = '" + patientId + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                date = rs.getDate(1);

            }
        } finally {

            if (st != null) {
                st.close();

            }
        }
        return date;
    }

    /**récupère le sexe du patient selon l'ID
     *
     * @param patientId
     * @return
     * @throws SQLException
     */
    public String recupSexe(String patientId) throws SQLException {
        Statement st = null;
        String sexe = "";
        try {

            st = con.createStatement();
            String query = "SELECT gender FROM PATIENT WHERE patientId  = '" + patientId + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                sexe = rs.getString(1);

            }
        } finally {

            if (st != null) {
                st.close();

            }
        }
        return sexe;
    }
}
