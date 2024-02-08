package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.ArticleVendu;

public interface EncheresService {

	ArticleVendu findArticleByNoArticle(int noArticle);

}
