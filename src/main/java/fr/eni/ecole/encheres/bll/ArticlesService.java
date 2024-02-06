package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;

public interface ArticlesService {
	
	public List<ArticleVendu> findAll();

}
