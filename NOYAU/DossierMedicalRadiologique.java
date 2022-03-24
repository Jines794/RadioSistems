package NOYAU;

import java.util.ArrayList;
import java.util.List;

public class DossierMedicalRadiologique {

    private List<Examen> examens = new ArrayList<Examen>();

    String idPatient;

    private Patient patient;

    public DossierMedicalRadiologique(String idPatient) {

    }

    public String affichageDMR() {
        String chaine = "";
        for (int i = 0; i < examens.size(); i++) {
            //chaine+= examen.get(i).affichaeExamen+ "\n"; 
        }
        return chaine;
    }

}
