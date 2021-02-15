package com.ecommerce.microcommerce.controller;


import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController // cette ligne permet d'indiquer à spring que c'est ici qu'on mettra toutes les uri de notre projet
public class ProductController {


    @Autowired // indique que on a une couche d'access aux donnees qu'il devra instancier lui meme
    private ProductDao productDao;

    //Produits   : recupère la liste des produits
    @RequestMapping(value ="/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits(){


        Iterable<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat"); // on souhaite cacher le prixAchat
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);  // monFiltreDynamique est dans la classe Product
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);
        return produitsFiltres;
    }



    //produits/{id}
    @RequestMapping(value ="/Produits/{id}", method = RequestMethod.GET)
    public Product afficherproduit(@PathVariable int id){
          Product produit = productDao.findById(id);
          if(produit==null) throw  new ProduitIntrouvableException("Le produit avec l'id "+ id + " est introuvable");
        return productDao.findById( id);
    }

    @GetMapping(value = "/test/Produits/{prixLimit}")
    public  List<Product> testDeRequetes(@PathVariable int prixLimit){
       return productDao.findByPrixGreaterThan(prixLimit);
    }



    // un produit est envoyé au backend depuis le body de la request .
    @PostMapping(value="/Produits")
    public ResponseEntity<Void> ajouterproduit(@Valid @RequestBody Product product){
       Product product1= productDao.save(product);
       if(product1==null){
           return ResponseEntity.noContent().build();
       }
       // si le produit a bien ete créé, nous allons recuperer l'URI de ce produit
        URI location = ServletUriComponentsBuilder  // ServletUriComponentsBuilder permet de creer un lien
                .fromCurrentRequest() // creer le lien a partir de la requete courante
                .path("/{id}")   // nous voulons ajouter à cette uri que nous sommes entrain de creer un id ( celui du produit que nous sommes entrain d'ajouter)
                .buildAndExpand(product1.getId())
                .toUri();

       return  ResponseEntity.created(location).build();
    }



}
