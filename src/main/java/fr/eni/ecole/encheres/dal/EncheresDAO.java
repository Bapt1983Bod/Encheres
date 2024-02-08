package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.ArticleVendu;

public interface EncheresDAO {

	ArticleVendu findArticleByNoArticle(Integer noArticle);

}
