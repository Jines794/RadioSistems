package NOYAU;

import BD.ExamenBD;
import BD.IdExamen;
import BD.ImageBD;
import BD.PatientsBD;
import BD.PersonnelBD;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Examen {

    private Date dateExamen;
    private String typeExamen;
    private String idPatient;
    private String idExamen;
    private String idPro;
    //private static   NumeroArchivage NUMERO;

    private CompteRendu cr;
    private int statut;
    
    public Examen(){
        this.statut= 0; // initialisation du satut à 0. l'examen est en attente
        cr= new CompteRendu ("","","");
    };
    public Examen(String idExamen, String idPatient, String idPro, String typeExamen,CompteRendu cr, Date dateExamen) throws SQLException {
        this.statut = 0;
        PatientsBD patient = new PatientsBD();
        PersonnelBD personnel = new PersonnelBD();
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");
        ExamenBD examen = new ExamenBD();
        this.cr=cr;
        //if ((personnel.rechFuncId(idPro).equals("1")) || (personnel.rechFuncId(idPro).equals("3"))) {
            if (patient.rechIdPatients2(idPatient.toString()) == true) {
                this.idExamen = idExamen.toString();
                this.idPatient = idPatient.toString();
                this.idPro = idPro.toString();
                this.typeExamen = typeExamen;
                this.dateExamen = dateExamen;
              //examen.insererUnExamen(this.idExamen, this.idPatient, this.idPro, this.typeExamen, this.cr.toString(), date.format(this.dateExamen), this.statut);
            examen.insererExamen(this.idExamen, this.idPatient, this.idPro, this.typeExamen, null, date.format(this.dateExamen), this.statut);
        }
    }


    Date getDateExamen() {
        return this.dateExamen;
    }

    String getTypeExamen() {
        return this.typeExamen;
    }

    public void affichageExamen(CompteRendu compteRendu, IdExamen idExam) throws SQLException {
        ImageBD image = new ImageBD();
        System.out.println(compteRendu.toStringCompteRendu());
        //System.out.println(image.getImageArchive(idExam));

//setNomPH(String value);        
//affichage de l'image correspondante au numéro grâce à la base de donnée
    }


    public void ajouterCompteRendu(CompteRendu compteRendu){
        if(this.cr.equals(null)){
        this.cr = compteRendu;
        }
    }
}  

