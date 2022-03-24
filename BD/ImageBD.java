/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;

/**
 *
 * @author francois
 */
//[ Colonne de la BDD  pacsId int primary key, nexamId char(10), image blob]
public class ImageBD {

    private Connection con;

    public ImageBD() {
        this.con = BdConnection.getConnexion();
    }
    
    public int GenererIdPacs() throws SQLException {
        IdPacs id = new IdPacs(con);
        int idPacs= id.getIdPacs();
        return idPacs;
        
    }

    /**
     * Permet de récuper la connection
     *
     * @param idExam
     * @return 
     * @throws SQLException
     */
    public DefaultListModel  listImage(String idExam) throws SQLException {
        Statement st = null;
        int id= 0;
        String stringId="";
       
        DefaultListModel model = new DefaultListModel();
        ArrayList<String> listeBD= new ArrayList();
        try {

            st = con.createStatement();
            String query = "SELECT pacsId, image FROM PACS where ExamId='"+idExam+"'";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                int i = 0;
                id = rs.getInt(1);
                stringId=Integer.toString(id);
                System.out.println(stringId);
                listeBD.add(stringId);
                model.addElement(stringId);
                i++;
            }
        } finally {

            if (st != null) {
                st.close();

        }
        
       
    }
  
        
        return model;
        
    }

    /**
     * Recupere l'image en fonction du numéro d'archivage
     *
     * @throws SQLException
     */
    public InputStream recupImage(int pacsId) throws SQLException {
        Statement st = null;
        InputStream image=null;
        try {

            st = con.createStatement();
            String query = "SELECT image FROM PACS where pacsId='"+pacsId+"'";
            ResultSet rs = st.executeQuery(query);

            int i = 0;
            while (rs.next()) {

                 image= rs.getBinaryStream(1);
                // InputStream input = rs.getBinaryStream(1);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return image;
    }

    /**
     * Recupere les colonnes de la base de données
     *
     * @return ArrayList
     */
    public ArrayList getColumnTablesImage() {
        ArrayList listCol = new ArrayList();
        try {

            Statement stmt = con.createStatement();
            // Créer un objet MetaData de ResultSet
            ResultSet res = stmt.executeQuery("Select * from  PACS ");
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
     * Permet de sauvegarder une Image dans la BD
     *
     * @param id
     * @param IdExam
     * @param image
     * @throws java.sql.SQLException
     */
    public void insererUneImage(IdPacs id, int IdExam, String image) throws SQLException {
        Statement st = null;
        BdConnection BD = new BdConnection();
        try {

            String query = "INSERT INTO PACS VALUES (" + id + "," + IdExam + ",'" + image;
            System.out.println(query);
            st = con.createStatement();
            st.executeUpdate(query);

            System.out.println("ajouté");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Permet de telecharger une ImageBD depuis la BDD
     *
     * @param name Nom del'ImageBD
     * @param location chemin où l'ImageBD va aller
     * @throws Exception
     */
    public void chargeIMG(String name, String location) throws Exception {
        File monImage = new File(location);
        FileOutputStream ostreamImage = new FileOutputStream(monImage);

        try {
            PreparedStatement ps = con.prepareStatement("select image from PACS where nom=?");

            try {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();

                try {
                    if (rs.next()) {
                        InputStream istreamImage = rs.getBinaryStream("bin");

                        byte[] buffer = new byte[1024];
                        int length = 0;

                        while ((length = istreamImage.read(buffer)) != -1) {
                            ostreamImage.write(buffer, 0, length);
                        }
                    }
                } finally {
                    rs.close();
                }
            } finally {
                ps.close();
            }
        } finally {
            ostreamImage.close();
        }
    }

    /**
     * Recupere Image de types buffered en fonction de l'identifiant examen
     *
     * @param idExam
     * @return Buffered Image
     * @throws SQLException
     * @throws IOException
     */
    public BufferedImage imgExam(int idExam) throws SQLException, IOException {

        try {
            PreparedStatement ps = con.prepareStatement("select image from PACS where examId ='" + idExam + "'");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Blob blob = rs.getBlob(1);
                    try (InputStream inputStream = blob.getBinaryStream()) {
                        return ImageIO.read(inputStream);
                    } finally {
                        blob.free();
                    }
                }
            }

        } catch (Exception e) {
            //JoptionPane.showMesssageDialog(null, "Erreur affichage ImageBD");
            System.out.println(e);
        }

        return null;
    }

    /**
     * ArrayList permettant de récupérer la liste des images d'un même examen
     *
     * @param idExam
     * @return ArrayList de Buffered Image d'un meme examen
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList imgExamliste(int idExam) throws SQLException, IOException {
        ArrayList listeImage = new ArrayList<BufferedImage>();
        try {
            PreparedStatement ps = con.prepareStatement("select bin from PACS where examId ='" + idExam + "'");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Blob blob = rs.getBlob(1);
                    try (InputStream inputStream = blob.getBinaryStream()) {
                        listeImage.add(ImageIO.read(inputStream));
                    } finally {
                        blob.free();
                    }
                }
            }

        } catch (Exception e) {
            //JoptionPane.showMesssageDialog(null, "Erreur affichage ImageBD");
            System.out.println(e);
        }

        return listeImage;
    }

    /**
     * Verifie si l'archive est unique
     *
     * @param numeroArchive
     * @return Boolean true si le numero d'archive existe deja et false si il
     * n'existe pas
     * @throws SQLException
     */
    public boolean uniqueArchive(int numeroArchive) throws SQLException {
        Statement st = null;
        boolean unique = false;
        int numArchive = 0;

        try {

            st = con.createStatement();
            String query = "SELECT pacsId FROM PACS WHERE pacsId = " + numeroArchive + " ";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                numArchive = rs.getInt(1);

            }
        } finally {

            if (st != null) {
                st.close();
            }
            if (con != null) {
                //con.close();
            }
        }
        if (numArchive == 0) {
            unique = false;
        } else {
            unique = true;
        }
        return unique;

    }

    /**
     * Permet de savvoir si l'examen contient une ImageBD d'archive
     *
     * @param idExam
     * @return Boolean true si il possede des images archives et false si non
     * @throws SQLException
     */
    public boolean imageArchive(int idExam) throws SQLException {
        Statement st = null;
        boolean unique = false;
        int numArchive = 0;

        try {

            st = con.createStatement();
            String query = "SELECT pacsId FROM PACS WHERE examId = " + idExam + " ";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                numArchive = rs.getInt(1);
                System.out.println("Num archivage : " + numArchive);

            }
        } finally {

            if (st != null) {
                st.close();
            }
            if (con != null) {
                //con.close();
            }
        }
        if (numArchive == 0) {
            unique = false;
        } else {
            unique = true;
        }
        return unique;

    }

    /**
     * Permet de récupere le numero de l'ImageBD archivé en fonction de
     * l'identifiant examen
     *
     * @param idExam Identifiant Examen
     * @return
     * @throws SQLException
     */
    public int getImageArchive(int idExam) throws SQLException {
        Statement st = null;

        int numArchive = 0;

        try {

            st = con.createStatement();
            String query = "SELECT pacsId FROM PACS WHERE examId = " + idExam + " ";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                numArchive = rs.getInt(1);

            }
        } finally {

            if (st != null) {
                st.close();
            }
            if (con != null) {
                //con.close();
            }
        }

        return numArchive;

    }

    /**met à jour l'image 
     *
     * @param numArchivage
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public void UpdateImage(int numArchivage) throws SQLException, FileNotFoundException {
        Statement st = null;
        File monImage = new File("./src/jpg/newImage4.jpg");
        FileInputStream istreamImage = new FileInputStream(monImage);
        int num = 0;
        String examId = "";
        InputStream image = null;
        try {

            st = con.createStatement();
            String query = "SELECT * FROM PACS WHERE pacsId = " + numArchivage + " ";

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                num = rs.getInt(1);
                examId = rs.getString(2);
                image = rs.getBinaryStream(3);

            }
        } finally {

            if (st != null) {
                st.close();
            }
            if (con != null) {
                //con.close();
            }
        }

        //st.executeUpdate("UPDATE pacs SET image  = '" + istreamImage + "' WHERE pacsId ='" + numArchivage + "'");
        /*PreparedStatement ps = con.prepareStatement("insert into PACS values(?,?,?)");
        ps.setInt(1, num);
        ps.setString(2, examId);
        ps.setBinaryStream(3, (InputStream) istreamImage, (int) monImage.length());

        ps.executeUpdate();

        System.out.println("nouvelle image inséré");
        ps.close();*/
    }
}
