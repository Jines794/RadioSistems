package NOYAU;


public class CompteRendu {
    private String observation;

    
    private String resultat;

   
    private String titre ;
    
    
    
    public CompteRendu(String titre, String observation, String resultat) {
        this.titre = titre;
        this.observation = observation;
        this.resultat = resultat;
    }

  

    
    String getObservation() {
        return this.observation;
    }

    
    void setObservation(String value) {
        this.observation = value;
    }

    
    String getResultat() {
        return this.resultat;
    }

    
    void setResultat(String value) {
        this.resultat = value;
    }

    
    String getTitre () {
        return this.titre ;
    }

    
    void setTitre (String value) {
        this.titre  = value;
    }

     
    public String toStringCompteRendu() {
        String cr= "";
        cr='\t'+this.titre.toUpperCase() +'\n'+'\t'+"RESULTAT"+'\n'+this.resultat+'\n'+'\t'+"OBSERVATION"+'\n'+this.observation;
        return cr;
    }
}
