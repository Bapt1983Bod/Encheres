package fr.eni.ecole.encheres.dal;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;

public interface ArticlesDAO {
	
	public List<ArticleVendu> findAll();

}
