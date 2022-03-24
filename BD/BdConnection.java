package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author francois
 */
public final class BdConnection {

    private String url;
    private String user;
    private String password;
    private boolean estConnecte;
    private static Connection con;

    //Constructeur avec les informations de connexion pour la BD (besoin d'être connecté au VPN de l'univ
    public BdConnection() throws SQLException {
        this.estConnecte = false;
        this.password = "c64c79f9ad";
        this.user = "vagneurf";
        this.url = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
    }

//    public boolean isConnected() {
//        return estConnecte;
//    }
    public BdConnection(String url, String user, String password) throws SQLException {
        this.estConnecte = false;
        this.password = "c64c79f9ad";
        this.user = "vagneurf";
        this.url = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag";
        this.url = url;
        this.user = user;
        this.password = password;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("le driver est chargé");
            //test de connection

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connection établie ! ");
            this.estConnecte = true;

        } catch (SQLException e) {
            System.out.println("Impossible de se connecter à la BD : " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BdConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.estConnecte = false;

    }

    //Permet d'obtenir une connexion à la base de données
    public static Connection getConnexion() {
        if (con == null) {
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                con = DriverManager.getConnection("jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:im2ag", "vagneurf", "c64c79f9ad");
                //this.estConnecte = true;

            }catch (SQLException e) {
                System.out.println("Impossible de se connecter à la BD : " + e.getMessage());
            }catch (ClassNotFoundException ex) {
                Logger.getLogger(BdConnection.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("cheh");
            }
            //this.estConnecte = false;

    }

    return con ;

}
}
