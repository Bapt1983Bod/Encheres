package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface ArticlesService {

	public List<ArticleVendu> findAllEnCours();
	
	// Recherche des articles en fonction d'une chaine de caractères dont la vente est active
	public List<ArticleVendu> findByString (String string);

	// Recherche des articles en fonction de leur catégorie et d'une chaine de caractères dont la vente est active
	public List<ArticleVendu> findByCatAndString(int idCat, String string);
	
	// Recherche des articles d'un acheteur en fonction de leur categorie et d'une chaine de caractères
	public List<ArticleVendu> findByNoUtilCatString (int noUtilisateur, int idCat, String string);
	
	// Recherche des articles d'un acheteur en fonction d'une chaine de caractères
	public List<ArticleVendu> findByNoUtilString (int noUtilisateur, String string);
	
	public int createArticle(Utilisateur vendeur, ArticleVendu article);

	ArticleVendu findArticleByNoArticle(int noArticle);
	
	// Recherche des articles d'un utilisateur
	public List<ArticleVendu> findByNoUtilisateur (int noUtilisateur);
	
	public void deleteByNoUtilisateur (int noUtilisateur);
	
	public List<ArticleVendu> filtreVentes (String etat, List<ArticleVendu> listNonTriee);
	
	public ArticleVendu setEtatVente (ArticleVendu art);
	
	public void deleteByNoArticle (int noArticle);

}
