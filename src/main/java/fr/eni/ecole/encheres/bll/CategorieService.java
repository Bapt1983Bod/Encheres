package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.Categorie;

public interface CategorieService {
	
	public List<Categorie> findAll();
	
	public Categorie findById(int id);

}
