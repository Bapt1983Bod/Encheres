package fr.eni.ecole.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.dal.EncheresDAO;

@Service
public class EncheresServiceImpl implements EncheresService {

	private EncheresDAO encheresDAO;

	@Override
	public ArticleVendu findArticleByNoArticle(Integer noArticle) {

		return encheresDAO.findArticleByNoArticle(noArticle);
	}

}
