package fr.eni.ecole.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.dal.EncheresDAO;

@Service
public class EncheresServiceImpl implements EncheresService {

	private EncheresDAO encheresDAO;

	public EncheresServiceImpl(EncheresDAO encheresDAO) {
		super();
		this.encheresDAO = encheresDAO;
	}

	@Override
	public ArticleVendu findArticleByNoArticle(int noArticle) {
		return encheresDAO.findArticleByNoArticle(noArticle);
	}
}