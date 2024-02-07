package fr.eni.ecole.encheres.dal;

import java.time.LocalDate;
import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;

public interface ArticlesDAO {
	
	public List<ArticleVendu> findAll(LocalDate date);
	
	public List<ArticleVendu> findByCatAndString (LocalDate date, int idCat, String string);
	
	public void createArticle (int idVendeur, ArticleVendu article);

}
