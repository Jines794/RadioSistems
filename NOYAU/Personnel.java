package NOYAU;


import BD.IdPro;


public abstract class Personnel {
    
    private String nom;

    
    private String prenom;

  
    private IdPro id;

    
    private String mdp;

    
    public String toString() {
        return nom + " " + prenom + " ";
    }

 
    public Personnel(IdPro id, String mdp, String nom, String prenom) {
        this.id=id;
        this.mdp = mdp;
        this.nom=nom;
        this.prenom=prenom;
        
    }
    public String getNom(){
        return this.nom;    
    }
    public String getPrenom(){
       return this.prenom; 
    }
    public IdPro getId(){
        return this.id;
    }
    public String getMdp(){
        return this.mdp;
    }

}
