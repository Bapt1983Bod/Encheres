package fr.eni.ecole.encheres.bll;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.ArticlesDAO;

@Service
public class ArticlesServiceImpl implements ArticlesService {

	private ArticlesDAO articlesDAO;

	public ArticlesServiceImpl(ArticlesDAO articlesDAO) {
		this.articlesDAO = articlesDAO;
	}

	// Récupère la liste des articles en vente à la date du jour
	@Override
	public List<ArticleVendu> findAll() {
		LocalDate date = LocalDate.now();

		return articlesDAO.findAll(date);
	}

	// Récupère la liste des articles dans la bdd dont la vente est en cours en
	// appliquant le filtre
	@Override
	public List<ArticleVendu> findByCatAndString(int idCat, String string) {
		LocalDate date = LocalDate.now();

		return articlesDAO.findByCatAndString(date, idCat, string);
	}

	@Override
	public int createArticle(Utilisateur vendeur, ArticleVendu article) {

		// crée l'article avec le noUtilisateur du vendeur et retourne le no de
		// l'article
		return articlesDAO.createArticle(vendeur.getNoUtilisateur(), article);

	}

	@Override
	public ArticleVendu findArticleByNoArticle(int noArticle) {
		return articlesDAO.findArticleByNoArticle(noArticle);
	}

}
