package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.Categorie;

public interface CategorieDAO {

	public List<Categorie> findAll();

	public Categorie findById(int id);

	public void ajouterCategorie(String libelle);

}
