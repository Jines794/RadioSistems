package NOYAU;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sosso
 */
public abstract class Image {
    private NumeroArchivage numeroArchivage;
    
    public Image(NumeroArchivage numeroArchivage){
        this.numeroArchivage=numeroArchivage;
       
    }

    public NumeroArchivage getNumeroArchivage() {
        return numeroArchivage;
    }

    public void setNumeroArchivage(NumeroArchivage numeroArchivage) {
        this.numeroArchivage = numeroArchivage;
    }
    
    
}
