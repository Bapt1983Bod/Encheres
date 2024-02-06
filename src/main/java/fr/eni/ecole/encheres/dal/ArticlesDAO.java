package fr.eni.ecole.encheres.dal;

import java.sql.Date;
import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;

public interface ArticlesDAO {
	
	public List<ArticleVendu> findAll(Date date);

}
