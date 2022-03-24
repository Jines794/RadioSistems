package BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

// Colonnes de la BDD examId char(10) primary key, patientId char(10),proId char(10),type varchar(90) not null,report long varchar,
//d TIMESTAMP not null, status integer check (status = 0 or status = 1) not null, foreign key (proId)  references LOGIN(proId), foreign key (patientId) references PATIENT(patientId)

public class ExamenBD {

    /* private String examId;
     private String patientId;
     private String proId;
     private String type;
     private String report;
     private String d;
     private String status;*/
    //private ArrayList<String> patAtt = new ArrayList<>();
    protected Connection con;

    /**
     * Permet de récuper la connection
     *
     * @throws SQLException
     */
    public ExamenBD() throws SQLException {
        this.con = BdConnection.getConnexion();
    }

    /**
     * Génère un nouvel identifiant d'examen
     *
     * @return unidentifiant d'examen
     * @throws SQLException
     */
    public String GenererIdEXam() throws SQLException {
        IdExamen idExamen = new IdExamen(con);
        String id = idExamen.getIdExamen();
        return id;
    }

    /**
     * Permet d'obtenir la liste des examens
     *
     * @throws SQLException
     */
    public void listexamen() throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT  * FROM EXAM";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String patientId = rs.getString(2);//patientId
                String proId = rs.getString(3);//proId
                String report = rs.getString(5); //compte rendu
                String status = rs.getString(7);//statut de l'examen

                System.out.printf("%-20s | %-20s| %-20s\n", patientId, proId, report, status);
                System.out.println(status);
                System.out.println('\n');

            }
        } finally {

            if (st != null) {
                st.close();
            }

        }

    }

    /**
     * Rechercher un examen grâce à un id Patient
     *
     * @param id Id Patient
     * @return chaine de caractere contenant les informations de l'ExamenBD
     * @throws SQLException
     */
    public String rechercheExamen(String id) throws SQLException {
        Statement st = null;
        String patientId = "";
        String proId = "";
        String type = "";
        String report = "";
        Timestamp d = null;
        String status = "";
        try {

            st = con.createStatement();
            String query = "SELECT * FROM EXAM WHERE patientId = '" + id + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                patientId = rs.getString(2);
                proId = rs.getString(3);
                type = rs.getString(4);
                report = rs.getString(5);
                d = rs.getTimestamp(6);
                status = rs.getString(7);

            }
        } finally {

            if (st != null) {
                st.close();

            }

        }
        if (patientId == null) {
            return "";

        } else {

            return patientId + " " + proId + " " + report + " " + type + " " + d + " " + status + " ";
        }
    }

    /**
     * recupere les colonnes de la base de données
     *
     * @return
     */
    public ArrayList getColumnTablesExamen() {
        ArrayList listCol = new ArrayList();
        try {

            Statement stmt = con.createStatement();

            // Créer un objet MetaData de ResultSet
            ResultSet res = stmt.executeQuery("Select * from  EXAM ");
            ResultSetMetaData rsMetaData = res.getMetaData();

            // Accéder à la liste des colonnes
            int nbrColonne = rsMetaData.getColumnCount();
            for (int i = 1; i <= nbrColonne; i++) {
                // Retourner le nom de la colonne
                String nom = rsMetaData.getColumnName(i);
                // Retourner le type de la colonne
                String type = rsMetaData.getColumnTypeName(i);
                listCol.add(nom + " " + type);
            }
            // Afficher les noms et les types des colonne sur le console
            System.out.println(listCol);

        } catch (Exception err) {
            System.out.println(err);
        }
        return listCol;
    }

    /**
     * Crée un model pour un JList avec une liste d'ExamenBD
     *
     * @param id Id Patient
     * @return Default list Model rempli avec les examens
     * @throws SQLException
     */
    public DefaultListModel listeExamen(String id) throws SQLException {
        Statement st = null;
        DefaultListModel listeExam = new DefaultListModel();
        String patientId = "";
        String proId = "";
        String type = "";
        String report = "";
        Timestamp d = null;
        String status = "";

        try {

            st = con.createStatement();
            String query = "SELECT  proId, type, report, d , status, examId FROM EXAM WHERE patientId = '" + id + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                patientId = rs.getString(2);
                proId = rs.getString(3);
                type = rs.getString(4);
                report = rs.getString(5);
                d = rs.getTimestamp(6);
                status = rs.getString(7);

                String examen = "[" + patientId + "] " + proId + " " + type + " " + report + " " + d + " " + status;

                listeExam.addElement(examen);

            }
        } finally {

            if (st != null) {
                st.close();

            }
        }

        return listeExam;

    }

    /**
     * Ajoute un ExamenBD
     *
     * @param examId Id de l'ExamenBD
     * @param patientId Id du patient
     * @param proId
     * @param type
     * @param report
     * @param status
     * @param d
     * @throws SQLException
     */
    public void insererExamen(String examId, String patientId, String proId, String type, String report, String d, int status) throws SQLException {
        //this.con = BdConnection.getConnexion();
        //Connection connexion = bd.getConnexion();
        PreparedStatement ps = con.prepareStatement("INSERT INTO EXAM VALUES (?,?,?,?,?,?,?)");
        //String date = "";
        //Timestamp t = Timestamp.valueOf(date);
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //System.out.println(timestamp);
        Timestamp timestamp = Timestamp.valueOf(d);
        ps.setString(1, examId);
        ps.setString(2, patientId);
        ps.setString(3, proId);
        ps.setString(4, type);
        ps.setString(5, report);
        ps.setTimestamp(6, timestamp);
        ps.setInt(7, status);

        ps.executeUpdate();

        System.out.println("exam inséré");
        ps.close();

    }

    /**
     * Permet de récupérer une ArrayListe d'ExamenBD en attente
     *
     * @return
     * @throws SQLException
     */
    public ArrayList examenEnAttente() throws SQLException {
        Statement st = null;
        ArrayList<String> patAtt = new ArrayList<>();
        String id = "";
        String patientId = "";
        Timestamp d = null;
        try {

            st = con.createStatement();
            String query = "SELECT examId, patientId, d  FROM EXAM WHERE status = 0";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                id = rs.getString(1);
                patientId = rs.getString(2);
                d = rs.getTimestamp(6);

                patAtt.add(id);
                patAtt.add(patientId);
                patAtt.add(d.toString());
            }

        } finally {

            if (st != null) {
                st.close();

            }
        }

        return patAtt;
    }

    /**
     * Permet de modifier l'état afin d'indiquer qu'un ExamenBD est en attente
     *
     * @param examId Id de l'ExamenBD a mettre en attente
     * @throws SQLException
     */
    public void examenEnAttTrue(String examId) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            st.executeUpdate("UPDATE EXAM SET status  = 0 WHERE id ='" + examId + "'");

        } finally {

            if (st != null) {
                st.close();

            }
        }

    }

    /**
     * Recupere le prochain numero d'ExamenBD
     *
     * @return Un int numero d'ExamenBD
     * @throws SQLException
     */
    public int getNumeroExamen() throws SQLException {
        Statement st = null;
        String numExam = "";
        int numId = 0;

        try {

            st = con.createStatement();
            String query = "SELECT max(examId) FROM EXAM ";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                numExam = rs.getString(1);

                numId = Integer.parseInt(numExam);

                numId = numId + 1;

            }
        } finally {

            if (st != null) {
                st.close();
            }
        }
        return numId;

    }

    /**
     * Permet de récupérer une liste d'ExamenBD avec ses infos
     *
     * @param examId identifiant ExamenBD
     * @return Arraylist contenant les infos de l'ExamenBD
     * @throws SQLException
     */
    public ArrayList infosExam(String examId) throws SQLException {
        Statement st = null;
        ArrayList infosExam = new ArrayList();
        String id_exam = "";
        String patientId = "";
        String proId = "";
        String type = "";
        String report = "";
        Timestamp d = null;
        String status = "";
        try {

            st = con.createStatement();
            String query = "SELECT * FROM EXAM WHERE id = '" + examId + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                id_exam = rs.getString(1);
                patientId = rs.getString(2);
                proId = rs.getString(3);
                type = rs.getString(4);
                report = rs.getString(5);
                d = rs.getTimestamp(6);
                status = rs.getString(7);

                infosExam.add(id_exam);
                infosExam.add(patientId);
                infosExam.add(proId);
                infosExam.add(type);
                infosExam.add(report);
                infosExam.add(d);
                infosExam.add(status);

            }
        } finally {

            if (st != null) {
                st.close();

            }
        }
        return infosExam;

    }

    /**
     * Permet de récupérer l'id de l'ExamenBD d'un patient
     *
     * @param idPatient identifiant du patient
     * @return String identifiant de l'ExamenBD
     * @throws SQLException
     */
    public String getIdExam(String idPatient) throws SQLException {
        Statement st = null;
        String examId = "";
        try {

            st = con.createStatement();
            String query = "SELECT * FROM EXAM WHERE patientId  = '" + idPatient + "'AND status  = 1 ";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                examId = rs.getString(1);
            }
        } finally {

            if (st != null) {
                st.close();

            }
            return examId;
        }
    }

    /**
     * Permet de modifier l'état afin d'indiquer qu'un ExamenBD n'est pas en
     * attente
     *
     * @param idExam=identifiant de l'ExamenBD
     * @throws SQLException
     */
    public void examenEnAttFalse(String idExam) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            st.executeUpdate("UPDATE EXAM SET status  = 1 WHERE id ='" + idExam + "'");

        } finally {

            if (st != null) {
                st.close();

            }
        }

    }

    /**
     * Permet de récupérer le compte rendu d'un patient en fonction de son
     * identifiant
     *
     * @param id = identifiant du patient
     * @return
     * @throws SQLException
     */
    public String recupCR(String id) throws SQLException {
        Statement st = null;
        String patientId = "";
        String proId = "";
        String report = "";
        try {

            st = con.createStatement();
            String query = "SELECT patientId, proId, report FROM EXAM WHERE patientId = '" + id + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                patientId = rs.getString(1);
                proId = rs.getString(2);
                report = rs.getString(3);

            }
        } finally {

            if (st != null) {
                st.close();

            }
        }
        if (patientId == null) {
            return "";

        } else {

            return patientId + " " + proId + " " + report + " ";
        }
    }

    /**
     * Permet de recupere le compte rendu avec l'id du compte rendu
     *
     * @param examId id du compte-rendu
     * @return
     * @throws SQLException
     */
    public String recupContenuCR1(String examId) throws SQLException {
        Statement st = null;
        String contenuCr = "PAS DE COMPTE-RENDU";
        try {

            st = con.createStatement();
            String query = "SELECT report FROM EXAM WHERE examId = '" + examId + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                contenuCr = rs.getString(1);

            }
        } finally {

            if (st != null) {
                st.close();

            }

        }

        return contenuCr;
    }

    /**
     * recupere les patients en fonction nom prénom
     *
     * @param firstNameP
     * @param lastNameP
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel rechExamNomPrenom(String firstNameP, String lastNameP, DefaultTableModel model) throws SQLException {
        String patientId1 = "";
        String lastNameP1 = "";
        String firstNameP1 = "";
        String gender = "";
        Date dateDeNaissancePa = null;
        String examId = "";
        String type = "";
        Timestamp d = null;
        String nomDoc = "";
        String prenomDoc = "";
        Statement st = null;
        try {
            //System.out.println(firstNameP);
            //System.out.println(lastNameP);

            st = con.createStatement();
            String query = "SELECT patientId , lastNameP , firstNameP, gender , birthDate, examId, type, d, lastName, firstName  FROM PATIENT JOIN EXAM USING (PatientId) JOIN LOGIN USING (proId) WHERE firstNameP   = '" + firstNameP + "'AND lastNameP ='" + lastNameP + "'";

            ResultSet rs = st.executeQuery(query);
            System.out.println(rs);
            //System.out.println("entrer");

            while (rs.next()) {
                patientId1 = rs.getString(1);
                lastNameP1 = rs.getString(2);
                firstNameP1 = rs.getString(3);
                gender = rs.getString(4);
                dateDeNaissancePa = rs.getDate(5);
                examId = rs.getString(6);
                type = rs.getString(7);
                d = rs.getTimestamp(8);
                nomDoc = rs.getString(9);
                prenomDoc = rs.getString(10);

                Vector row = new Vector(10);// Vector Qui fait la ligne
                row.add(patientId1);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
                row.add(dateDeNaissancePa);
                row.add(examId);
                row.add(type);
                row.add(d);
                row.add(nomDoc);
                row.add(prenomDoc);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  
                System.out.println(row.toString());

            }

        } finally {

            if (st != null) {
                st.close();
            }
        }
        return model;

    }
/**
     * recupere les patients en fonction ID nom et prénom 
     *
     * @param id
     * @param firstNameP
     * @param lastNameP
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel rechExamNomPrenomId(String id, String firstNameP, String lastNameP, DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query1 = "SELECT patientId , lastNameP , firstNameP, gender , birthDate, examId, type, d, lastName, firstName  FROM PATIENT JOIN EXAM USING (PatientId) JOIN LOGIN USING (proId) WHERE firstNameP   = '" + firstNameP + "'AND lastNameP ='" + lastNameP + "'AND patientId ='" + id + "'";

            ResultSet rs = st.executeQuery(query1);
            System.out.println(rs);

            while (rs.next()) {

                String patientId1 = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                Date dateDeNaissancePa = rs.getDate(5);
                String examId = rs.getString(6);
                String type = rs.getString(7);
                Timestamp d = rs.getTimestamp(8);
                String nomDoc = rs.getString(9);
                String prenomDoc = rs.getString(10);

                Vector row = new Vector(10);// Vector Qui fait la ligne
                row.add(patientId1);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
                row.add(dateDeNaissancePa);
                row.add(examId);
                row.add(type);
                row.add(d);
                row.add(nomDoc);
                row.add(prenomDoc);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  
                System.out.println(row.toString());
            }

        } finally {

            if (st != null) {
                st.close();

            }
        }
        return model;

    }

    /**
     * recupere les exams en fct du nom et prénom et id et type d'exam
     *
     * @param patientId
     * @param firstNameP
     * @param lastNameP
     * @param type
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel rechExamNomPrenomIdType(String patientId, String firstNameP, String lastNameP, String type, DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query = "SELECT patientId , lastNameP , firstNameP, gender , birthDate, examId, type, d, lastName, firstName  FROM PATIENT JOIN EXAM USING (PatientId) JOIN LOGIN USING (proId) WHERE firstNameP   = '" + firstNameP + "'AND lastNameP ='" + lastNameP + "'AND patientId ='" + patientId + "'AND type ='" + type + "'";

            ResultSet rs = st.executeQuery(query);
            System.out.println(rs);

            while (rs.next()) {

                String patientId1 = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                Date dateDeNaissancePa = rs.getDate(5);
                String examId = rs.getString(6);
                String type1 = rs.getString(7);
                Timestamp d = rs.getTimestamp(8);
                String nomDoc = rs.getString(9);
                String prenomDoc = rs.getString(10);

                Vector row = new Vector(10);// Vector Qui fait la ligne
                row.add(patientId1);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
                row.add(dateDeNaissancePa);
                row.add(examId);
                row.add(type1);
                row.add(d);
                row.add(nomDoc);
                row.add(prenomDoc);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  
                System.out.println("vecteur " + row.toString());

            }

        } finally {

            if (st != null) {
                st.close();

            }
        }
        return model;

    }
/**
     * recupere les exams avec tout les paramètres 
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel listeExam(DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query1 = "SELECT patientId , lastNameP , firstNameP, gender , birthDate, examId, type, d, lastName, firstName  FROM PATIENT JOIN EXAM USING (PatientId) JOIN LOGIN USING (proId)";

            ResultSet rs = st.executeQuery(query1);
            System.out.println(rs);

            while (rs.next()) {

                String patientId1 = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                Date dateDeNaissancePa = rs.getDate(5);
                String examId = rs.getString(6);
                String type = rs.getString(7);
                Timestamp d = rs.getTimestamp(8);
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String dateFormat = formater.format(d);
                String nomDoc = rs.getString(9);
                String prenomDoc = rs.getString(10);

                Vector row = new Vector(10);// Vector Qui fait la ligne
                row.add(patientId1);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
                row.add(dateDeNaissancePa);
                row.add(examId);
                row.add(type);
                row.add(dateFormat);
                row.add(nomDoc);
                row.add(prenomDoc);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  
                System.out.println(row.toString());
            }

        } finally {

            if (st != null) {
                st.close();

            }
        }
        return model;

    }
/**
     * recupere les exams (réduits) avec l'id
     *
     * @param patId
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel listeExamRéduite(String patId, DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query1 = "SELECT examId, type, firstname, lastname  FROM PATIENT JOIN EXAM USING (PatientId) JOIN LOGIN USING (proId) WHERE patientId ='" + patId + "'";

            ResultSet rs = st.executeQuery(query1);
            System.out.println(rs);

            while (rs.next()) {

                String examId = rs.getString(1);
                String type = rs.getString(2);
                String prenomDoc = rs.getString(3);
                String nomDoc = rs.getString(4);

                Vector row = new Vector(10);// Vector Qui fait la ligne
                
                row.add(examId);
                row.add(type);
                row.add(nomDoc);
                row.add(prenomDoc);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  
                System.out.println(row.toString());
            }

        } finally {

            if (st != null) {
                st.close();

            }
        }
        return model;

    }
    
    /**récupère date exam
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public Timestamp recupDateExam(String id) throws SQLException {
        Statement st = null;
        Timestamp dateExam = null;
        try {

            st = con.createStatement();
            String query = "SELECT d FROM EXAM WHERE examId = '" + id + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                dateExam=rs.getTimestamp(1);

            }
        } finally {

            if (st != null) {
                st.close();

            }
        


            return dateExam;
       
    }
       }
     /**récupère id archivage 
     *
     * @param idExam
     * @return
     * @throws SQLException
     */
        public String recupNumeroArchivage(String idExam) throws SQLException {
        Statement st = null;
        String listeNum = "";
        try {

            st = con.createStatement();
            String query = "SELECT pacsid FROM EXAM join PACS using(examId)WHERE examId = '" + idExam + "'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                listeNum+=rs.getInt(1)+"\n";

            }
        } finally {

            if (st != null) {
                st.close();

            }
        


            return listeNum;
       
    }
        }
        
  public void updateIdpro(String proId, String examId) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            st.executeUpdate("UPDATE EXAM SET proid  = '"+proId+"' WHERE examId ='" + examId + "'");

        } finally {

            if (st != null) {
                st.close();

            }
        }
  }
   /**met un jour un compte rendu 
     *
     * @param cr
     * @param examId
     * @throws SQLException
     */
   public void updateCR(String cr, String examId) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            st.executeUpdate("UPDATE EXAM SET report  = '"+cr+"' WHERE examId ='" + examId + "'");

        } finally {

            if (st != null) {
                st.close();

            }
        }
  }
      public void updateStatus(String examId) throws SQLException {
        Statement st = null;
        try {

            st = con.createStatement();
            st.executeUpdate("UPDATE EXAM SET status  =1 ");

        } finally {

            if (st != null) {
                st.close();

            }
        }
  }
   
    /**renvoie la liste des examens en attente 
     *
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel listeExamAttente(DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query1 = "SELECT patientId , lastNameP , firstNameP, gender , birthDate, examId, type, d, lastName, firstName  FROM PATIENT JOIN EXAM USING (PatientId) JOIN LOGIN USING (proId) where status=0";

            ResultSet rs = st.executeQuery(query1);
            System.out.println(rs);

            while (rs.next()) {

                String patientId1 = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                Date dateDeNaissancePa = rs.getDate(5);
                String examId = rs.getString(6);
                String type = rs.getString(7);
                Timestamp d = rs.getTimestamp(8);
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String dateFormat = formater.format(d);
                String nomDoc = rs.getString(9);
                String prenomDoc = rs.getString(10);

                Vector row = new Vector(10);// Vector Qui fait la ligne
                row.add(patientId1);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
                row.add(dateDeNaissancePa);
                row.add(examId);
                row.add(type);
                row.add(dateFormat);
                row.add(nomDoc);
                row.add(prenomDoc);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  
                System.out.println(row.toString());
            }

        } finally {

            if (st != null) {
                st.close();

            }
        }
        return model;

    }
   
    /**renvoie les examens selon un seul type 
     *
     * @param typeE
     * @param model
     * @return
     * @throws SQLException
     */
    public DefaultTableModel listeExamParType(String typeE, DefaultTableModel model) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query1 = "SELECT patientId , lastNameP , firstNameP, gender , birthDate, examId, type, d, lastName, firstName  FROM PATIENT JOIN EXAM USING (PatientId) JOIN LOGIN USING (proId) where type='"+typeE+"'";

            ResultSet rs = st.executeQuery(query1);
            System.out.println(rs);

            while (rs.next()) {

                String patientId1 = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                Date dateDeNaissancePa = rs.getDate(5);
                String examId = rs.getString(6);
                String type = rs.getString(7);
                Timestamp d = rs.getTimestamp(8);
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String dateFormat = formater.format(d);
                String nomDoc = rs.getString(9);
                String prenomDoc = rs.getString(10);

                Vector row = new Vector(10);// Vector Qui fait la ligne
                row.add(patientId1);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
                row.add(dateDeNaissancePa);
                row.add(examId);
                row.add(type);
                row.add(dateFormat);
                row.add(nomDoc);
                row.add(prenomDoc);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  
                System.out.println(row.toString());
            }

        } finally {

            if (st != null) {
                st.close();

            }
        }
        return model;

    }
    
    /**renvoie la liste des examens en attente d'un patient en fonction de son nom et de son prénom
     *
     * @param model
     * @param nom
     * @param prenom
     * @return
     * @throws SQLException
     */
    public DefaultTableModel listeExamAttenteNomPrenom(DefaultTableModel model, String nom, String prenom) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query1 = "SELECT patientId , lastNameP , firstNameP, gender , birthDate, examId, type, d, lastName, firstName  FROM PATIENT JOIN EXAM USING (PatientId) JOIN LOGIN USING (proId) where status=0 and lastnamep='"+nom+"' and firstnamep='"+prenom+"'";

            ResultSet rs = st.executeQuery(query1);
            System.out.println(rs);

            while (rs.next()) {

                String patientId1 = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                Date dateDeNaissancePa = rs.getDate(5);
                String examId = rs.getString(6);
                String type = rs.getString(7);
                Timestamp d = rs.getTimestamp(8);
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String dateFormat = formater.format(d);
                String nomDoc = rs.getString(9);
                String prenomDoc = rs.getString(10);

                Vector row = new Vector(10);// Vector Qui fait la ligne
                row.add(patientId1);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
                row.add(dateDeNaissancePa);
                row.add(examId);
                row.add(type);
                row.add(dateFormat);
                row.add(nomDoc);
                row.add(prenomDoc);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  
                System.out.println(row.toString());
            }

        } finally {

            if (st != null) {
                st.close();

            }
        }
        return model;

    }
    
    /**renvoie la liste des examens en attente d'un patient en fonction de son nom, de son prénom et de son identifiant
     *
     * @param model
     * @param nom
     * @param prenom
     * @param idPat
     * @return
     * @throws SQLException
     */
    public DefaultTableModel listeExamAttenteNomPrenomId(DefaultTableModel model, String nom, String prenom, String idPat) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query1 = "SELECT patientId , lastNameP , firstNameP, gender , birthDate, examId, type, d, lastName, firstName  FROM PATIENT JOIN EXAM USING (PatientId) JOIN LOGIN USING (proId) where status=0 and lastnamep='"+nom+"' and firstnamep='"+prenom+"' and patientId='"+idPat+"'";

            ResultSet rs = st.executeQuery(query1);
            System.out.println(rs);

            while (rs.next()) {

                String patientId1 = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                Date dateDeNaissancePa = rs.getDate(5);
                String examId = rs.getString(6);
                String type = rs.getString(7);
                Timestamp d = rs.getTimestamp(8);
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String dateFormat = formater.format(d);
                String nomDoc = rs.getString(9);
                String prenomDoc = rs.getString(10);

                Vector row = new Vector(10);// Vector Qui fait la ligne
                row.add(patientId1);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
                row.add(dateDeNaissancePa);
                row.add(examId);
                row.add(type);
                row.add(dateFormat);
                row.add(nomDoc);
                row.add(prenomDoc);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  
                System.out.println(row.toString());
            }

        } finally {

            if (st != null) {
                st.close();

            }
        }
        return model;

    }
    
     /**renvoie la liste des examens en attente d'un patient en fonction de son nom, de son prénom, de son identifiant et de son type
     *
     * @param model
     * @param nom
     * @param prenom
     * @param idPat
     * @param typeE
     * @return
     * @throws SQLException
     */
    public DefaultTableModel listeExamAttenteNomPrenomIdType(DefaultTableModel model, String nom, String prenom, String idPat, String typeE) throws SQLException {

        Statement st = null;
        try {

            st = con.createStatement();
            String query1 = "SELECT patientId , lastNameP , firstNameP, gender , birthDate, examId, type, d, lastName, firstName  FROM PATIENT JOIN EXAM USING (PatientId) JOIN LOGIN USING (proId) where status=0 and lastnamep='"+nom+"' and firstnamep='"+prenom+"' and patientId='"+idPat+"' and type='"+typeE+"'";

            ResultSet rs = st.executeQuery(query1);
            System.out.println(rs);

            while (rs.next()) {

                String patientId1 = rs.getString(1);
                String lastNameP1 = rs.getString(2);
                String firstNameP1 = rs.getString(3);
                String gender = rs.getString(4);
                Date dateDeNaissancePa = rs.getDate(5);
                String examId = rs.getString(6);
                String type = rs.getString(7);
                Timestamp d = rs.getTimestamp(8);
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String dateFormat = formater.format(d);
                String nomDoc = rs.getString(9);
                String prenomDoc = rs.getString(10);

                Vector row = new Vector(10);// Vector Qui fait la ligne
                row.add(patientId1);
                row.add(lastNameP1);
                row.add(firstNameP1);
                row.add(gender);
                row.add(dateDeNaissancePa);
                row.add(examId);
                row.add(type);
                row.add(dateFormat);
                row.add(nomDoc);
                row.add(prenomDoc);

                model.addRow(row);// ajout du vecteur au model, ajout de la ligne au tableau  
                System.out.println(row.toString());
            }

        } finally {

            if (st != null) {
                st.close();

            }
        }
        return model;

    }
   

   

}
