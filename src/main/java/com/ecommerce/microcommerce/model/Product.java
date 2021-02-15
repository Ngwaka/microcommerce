package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Entity      //Vous annotez la classe avec @Entity afin qu'elle soit scannée et prise en compte .
            // ainsi une table nomee PRODUCT sera cree automatiquement dans notre base de donnee
//@JsonFilter("monFiltreDynamique")
public class Product {
    @Id  // cette anotation est placee avant l'attribu qui sert de clef (id)
    @GeneratedValue  // indique que l'attribut id est generated et incremented
    private int id;

    @Length( min = 3, max = 20, message = "Nom trop long ou trop courts ") // Hibernate Validator : control sur la longueur du nom
    private String nom;

    @Min(value = 1)  // Hibernate Validator: control sur le prix: indique que le prix doit etre >=1
    private int prix ;
    private int prixAchat;


    public Product(){
        
    }
    public Product(int id, String nom, int prix, int prixAchat ){
        this.id= id;
        this.nom = nom;
        this.prix = prix ;
        this.prixAchat= prixAchat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }

}
