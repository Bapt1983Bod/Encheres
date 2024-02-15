package fr.eni.ecole.encheres.bll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	public List<ArticleVendu> findAllEnCours() {
		return articlesDAO.findAllEnCours();
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

	@Override
	public List<ArticleVendu> findByNoUtilisateur(int noUtilisateur) {
		return articlesDAO.findByNoUtilisateur(noUtilisateur);
	}

	@Override
	public void deleteByNoUtilisateur(int noUtilisateur) {
		articlesDAO.deleteByNoUtilisateur(noUtilisateur);

	}

	@Override
	public List<ArticleVendu> filtreVentes(String etat, List<ArticleVendu> listNonTriee) {
		List<ArticleVendu> listArticles = new ArrayList<ArticleVendu>();

		long miliseconds = System.currentTimeMillis();
		Date date = new Date(miliseconds);

		if (etat != null) {

			if (etat.equals("enAttente")) {
				for (ArticleVendu art : listNonTriee) {
					if (art.getDateDebutEncheres().after(date)) {
						art.setEtatVente("enAttente");
						listArticles.add(art);
					}
				}
			} else if (etat.equals("terminee")) {
				for (ArticleVendu art : listNonTriee) {
					if (art.getDateFinEncheres().before(date)) {
						art.setEtatVente("terminee");
						listArticles.add(art);
					}
				}
			} else if (etat.equals("enCours")) {
				for (ArticleVendu art : listNonTriee) {
					art.setEtatVente("enCours");
					listArticles.add(art);
				}
			} else {
				listArticles = listNonTriee;
			}
		}

		return listArticles;
	}

	@Override
	public List<ArticleVendu> findByNoUtilCatString(int noUtilisateur, int idCat, String string) {
		return articlesDAO.findByNoUtilCatString(noUtilisateur, idCat, string);
	}

	@Override
	public List<ArticleVendu> findByNoUtilString(int noUtilisateur, String string) {
		return articlesDAO.findByNoUtilString(noUtilisateur, string);
	}

	@Override
	public List<ArticleVendu> findByString(String string) {
		LocalDate date = LocalDate.now();
		return articlesDAO.findByString(date, string);
	}

	@Override
	public ArticleVendu setEtatVente(ArticleVendu art) {
		
		// Obtient la date actuelle
		Calendar calendar = Calendar.getInstance();
		// Convertit Calendar en Date
		Date dateDuJour = calendar.getTime();

		// Ajoute un jour à la date actuelle
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		// Convertit Calendar en Date
		Date datePlusUnJour = calendar.getTime();

		
			if (art.getDateDebutEncheres().after(dateDuJour)) {
				art.setEtatVente("enAttente");
			} else if (art.getDateFinEncheres().before(datePlusUnJour)) {
				art.setEtatVente("terminee");
			} else {
				art.setEtatVente("enCours");
			}
		
		return art;
	}

	@Override
	public void deleteByNoArticle(int noArticle) {
		articlesDAO.deleteByNoArticle(noArticle);
		
	}

}
