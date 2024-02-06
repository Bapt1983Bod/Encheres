package fr.eni.ecole.encheres.bll;

import java.time.LocalDate;
import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;

public interface ArticlesService {
	
	public List<ArticleVendu> findAll();
	
	public List<ArticleVendu> findByCatAndString (int idCat, String string);
	
	

}
