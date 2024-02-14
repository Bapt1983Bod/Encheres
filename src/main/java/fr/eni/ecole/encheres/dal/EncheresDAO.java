package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface EncheresDAO {

//	public void deleteByIdUtilisateurAndIdArticle(int noArticle, Utilisateur utilisateur);
//
//	public void deleteByIdArticle(int noArticle);
//
//	public void deleteByNoUtilisateur(int noUtilisateur);
//
//	Enchere getHighestEnchere(int noArticle);
//
//	public void surenchere(int noArticle, long montantEnchere, Utilisateur acheteur);
//
//	boolean aEncheriSurArticle(int noArticle, Utilisateur utilisateur);
	
	// Renvoi la liste de toutes les enchères
	public List<Enchere> findAll();
	
	// Création d'une enchère
	public void createEnchere (Utilisateur utilisateur, int noArticle, long montantEnchere);
	
	// Suupression d'une enchère par no article
	public void deleteByArticle (int noArticle);
	
	// Suppression d'une enchère par no utilisateur
	public void deleteByUtilisateur (int noUtilisateur);
	
	// Suppression d'une enchère par no utilisateur et no article
	public void deleteByUtilisateurArticle (int noArticle, Utilisateur utilisateur);
	
	// Récupération des enchères d'un utilisateur
	public List<Enchere> findByUtilisateur (int noUtilisateur);
	
	// Récupération des enchères d'un article
	public List<Enchere> findByArticle (int noArticle);
	
	// Récupération de l'enchère d'un utilisateur sur un article
	public Enchere findEnchereUtilisateur (int noArticle, Utilisateur utilisateur);
	
	

}
