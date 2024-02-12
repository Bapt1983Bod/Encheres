package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface ArticlesService {

	public List<ArticleVendu> findAll();

	public List<ArticleVendu> findByCatAndString(int idCat, String string);

	public int createArticle(Utilisateur vendeur, ArticleVendu article);

	ArticleVendu findArticleByNoArticle(int noArticle);
	
	public List<ArticleVendu> findByNoUtilisateur (int noUtilisateur);
	
	public void deleteByNoUtilisateur (int noUtilisateur);

}
