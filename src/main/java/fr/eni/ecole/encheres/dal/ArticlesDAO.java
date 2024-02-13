package fr.eni.ecole.encheres.dal;

import java.time.LocalDate;
import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;

public interface ArticlesDAO {

	public List<ArticleVendu> findAll(LocalDate date);
	
	// Recherche des articles en fonction d'une chaine de caractères dont la vente est active
	public List<ArticleVendu> findByString (LocalDate date, String string);

	// Recherche des articles en fonction de la catégorie et d'une chaine de caractères dont la vente est active
	public List<ArticleVendu> findByCatAndString(LocalDate date, int idCat, String string);
	
	// Recherche des articles en fonction de l'utilisateur
	public List<ArticleVendu> findByNoUtilisateur (int noUtilisateur);
	
	// Recherche des articles d'un acheteur en fonction de leur categorie et d'une chaine de caractères
	public List<ArticleVendu> findByNoUtilCatString (int noUtilisateur, int idCat, String string);
	
	// Recherche des articles d'un acheteur en fonction d'une chaine de caractères
	public List<ArticleVendu> findByNoUtilString (int noUtilisateur, String string);

	public int createArticle(int idVendeur, ArticleVendu article);

	ArticleVendu findArticleByNoArticle(int noArticle);
	
	public void deleteByNoUtilisateur (int noUtilisateur);

}
