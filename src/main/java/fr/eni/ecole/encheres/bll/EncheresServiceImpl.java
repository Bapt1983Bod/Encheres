package fr.eni.ecole.encheres.bll;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.ArticlesDAO;
import fr.eni.ecole.encheres.dal.EncheresDAO;
import fr.eni.ecole.encheres.dal.UtilisateurDAO;

@Service
public class EncheresServiceImpl implements EncheresService {

	private EncheresDAO encheresDAO;
	private UtilisateurDAO utilisateurDAO;
	private ArticlesDAO articlesDAO;
	private ArticlesService articlesService;

	public EncheresServiceImpl(EncheresDAO encheresDAO, UtilisateurDAO utilisateurDAO, ArticlesDAO articlesDAO,
			ArticlesService articlesService) {
		this.encheresDAO = encheresDAO;
		this.utilisateurDAO = utilisateurDAO;
		this.articlesDAO = articlesDAO;
		this.articlesService = articlesService;
	}

	// Vérifier si vente ouverte avant d'insérer l'enchère (comparer date du jour
	// avec date de début et date de fin de la vente
	@Override
	public void encherir(int noArticle, long montantEnchere, Utilisateur utilisateur) {

		if (utilisateur.getCredit() > montantEnchere) {
			encheresDAO.createEnchere(utilisateur, noArticle, montantEnchere);
			utilisateurDAO.updateCredit(utilisateur, montantEnchere);
		} else {
			// remonter erreur !!!
		}
	}

	@Override
	public void deleteByIdArticle(int noArticle) {

		// Récupération de la liste des enchères d'un article
		List<Enchere> encheres = encheresDAO.findByArticle(noArticle);

		if (!encheres.isEmpty()) {
			for (Enchere enchere : encheres) {
				int montantEnchere = enchere.getMontantEnchere();
				Utilisateur utilisateur = enchere.getUtilisateur();
				// recrédit du montant des enchères avant suppression enchères
				utilisateurDAO.restoreCredit(utilisateur, montantEnchere);
			}
		}
		// Suppression des enchères
		encheresDAO.deleteByArticle(noArticle);
	}

	@Override
	public void deleteByNoUtilisateur(int noUtilisateur) {
		// Récupération de la liste des enchères d'un utilisateur
		List<Enchere> encheres = encheresDAO.findByUtilisateur(noUtilisateur);

		if (!encheres.isEmpty()) {
			for (Enchere enchere : encheres) {
				// Remboursement du montant de l'enchère à l'utilisateur
				int montantEnchere = enchere.getMontantEnchere();
				Utilisateur utilisateur = enchere.getUtilisateur();

				utilisateurDAO.restoreCredit(utilisateur, montantEnchere);
			}
		}
		// Suppression des enchères
		encheresDAO.deleteByUtilisateur(noUtilisateur);
	}

	@Override
	public void deleteByIdUtilisateurAndIdArticle(int noArticle, Utilisateur utilisateur) {
		// Récupération du montant de l'enchère
		int montantEnchere = encheresDAO.findEnchereUtilisateur(noArticle, utilisateur).getMontantEnchere();

		utilisateurDAO.restoreCredit(utilisateur, montantEnchere);

		encheresDAO.deleteByUtilisateurArticle(noArticle, utilisateur);
	}

	@Override
	public Enchere getHighestEnchere(int noArticle) {
		List<Enchere> encheres = encheresDAO.findByArticle(noArticle);
		// numéro de l'article et montant ayant l'enchère la plus haute
		Enchere enchere = new Enchere();
		long montantEnchere = 0;

		for (Enchere e : encheres) {
			if (e.getMontantEnchere() > montantEnchere) {
				montantEnchere = e.getMontantEnchere();
				enchere = e;
			}
		}
		return enchere;
	}

	@Override
	public boolean aEncheriSurArticle(int noArticle, Utilisateur acheteur) {
		if ((encheresDAO.findEnchereUtilisateur(noArticle, acheteur)) == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void surenchere(int noArticle, long montantEnchere, Utilisateur acheteur) {
		if ((encheresDAO.findEnchereUtilisateur(noArticle, acheteur)) != null) {
			deleteByIdUtilisateurAndIdArticle(noArticle, acheteur);
		}

		encheresDAO.createEnchere(acheteur, noArticle, montantEnchere);
	}

	@Override
	public List<Enchere> findAll() {
		return encheresDAO.findAll();
	}

	// Liste des enchères d'un utilisateur en cours
	@Override
	public List<ArticleVendu> listEnchUtilisateurEnCours(Utilisateur utilisateur) {
		List<Enchere> listEnchereUtil = new ArrayList<Enchere>();
		List<Enchere> encheres = encheresDAO.findAll();

		for (Enchere e : encheres) {
			if (e.getUtilisateur().getNoUtilisateur() == utilisateur.getNoUtilisateur()) {
				// Liste des enchères d'un utilisateur
				listEnchereUtil.add(e);
			}
		}

		List<ArticleVendu> articles = articlesDAO.findAllEnCours();
		List<ArticleVendu> articlesUtiliEncher = new ArrayList<ArticleVendu>();

		for (ArticleVendu art : articles) {
			for (Enchere e : listEnchereUtil) {
				if (e.getArticleVendu().getNoArticle() == art.getNoArticle()) {
					articlesUtiliEncher.add(art);
				}
			}
		}
		return articlesService.setEtatVente(articlesUtiliEncher);
	}

	@Override
	public List<ArticleVendu> listEnchPlusHaute () {
		// Liste de toutes les enchères
		List<ArticleVendu> articles = articlesDAO.findAll();
		// Liste des enchères terminées
		List<ArticleVendu> articlesClosed = new ArrayList<ArticleVendu>();

		// Obtient la date actuelle
		Calendar calendar = Calendar.getInstance();
		// Ajoute un jour à la date actuelle
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		// Convertit Calendar en Date
		Date date = calendar.getTime();

		for (ArticleVendu art : articles) {
			if (art.getDateFinEncheres().before(date)) {
				art.setEtatVente("terminee");
				articlesClosed.add(art);
			}
		}
		
		// Récupération de l'enchère la plus haute et intégration dans l'article
		for (ArticleVendu art : articlesClosed) {
			Enchere e =  getHighestEnchere(art.getNoArticle());
			List<Enchere> encheres = new ArrayList<Enchere>();
			Utilisateur acheteur = utilisateurDAO.findById(e.getUtilisateur().getNoUtilisateur()).get();
			art.setAcheteur(acheteur);
			art.setPrixVente(e.getMontantEnchere());
			encheres.add(e);
			art.setListeEnchere(encheres);
		}
		return articlesClosed;
	}

	@Override
	public List<ArticleVendu> listEnchPlusHauteUtilisateur(Utilisateur utilisateur) {
		List<ArticleVendu> articlesClosed = listEnchPlusHaute();
		
		List<ArticleVendu> articlesRemportesUtilisateur = new ArrayList<ArticleVendu>();
		
		for (ArticleVendu art : articlesClosed) {
			for (Enchere e : art.getListeEnchere()) {
				if (e.getUtilisateur() != null && e.getUtilisateur().getNoUtilisateur() == (utilisateur.getNoUtilisateur())) {
					articlesRemportesUtilisateur.add(art);
				}
			}
		}
		
		return articlesRemportesUtilisateur;
	}

}