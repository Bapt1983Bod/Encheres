package fr.eni.ecole.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.EncheresDAO;
import fr.eni.ecole.encheres.dal.UtilisateurDAO;

@Service
public class EncheresServiceImpl implements EncheresService {

	private EncheresDAO encheresDAO;
	private UtilisateurDAO utilisateurDAO;

	public EncheresServiceImpl(EncheresDAO encheresDAO, UtilisateurDAO utilisateurDAO) {
		this.encheresDAO = encheresDAO;
		this.utilisateurDAO = utilisateurDAO;
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
		
		
		
		
	//	encheresDAO.deleteByNoUtilisateur(noUtilisateur);

	}

	@Override
	public void deleteByIdUtilisateurAndIdArticle(int noArticle, Utilisateur utilisateur) {
		// Récupération du montant de l'enchère
		int montantEnchere = encheresDAO.findEnchereUtilisateur(noArticle, utilisateur).getMontantEnchere();
		
		utilisateurDAO.restoreCredit(utilisateur, montantEnchere);
		
		encheresDAO.deleteByUtilisateurArticle(noArticle, utilisateur);
		
		
		
		
	//	encheresDAO.deleteByIdUtilisateurAndIdArticle(noArticle, utilisateur);
	}

	@Override
	public Enchere getHighestEnchere(int noArticle) {
		List<Enchere> encheres = encheresDAO.findByArticle(noArticle);
		// numéro de l'article et montant ayant l'enchère la plus haute
		Enchere enchere = new Enchere();
		long montantEnchere = 0;
		
		for (Enchere e : encheres) {
			if (e.getMontantEnchere()>montantEnchere) {
				montantEnchere = e.getMontantEnchere();
				enchere = e;
			}
		}
		return enchere;
		
		
		
		
	//	return encheresDAO.getHighestEnchere(noArticle);
	}

	@Override
	public boolean aEncheriSurArticle(int noArticle, Utilisateur acheteur) {
		if((encheresDAO.findEnchereUtilisateur(noArticle, acheteur))== null) {
			return false;
		} else {
			return true;
		}
		
		
		
		
	//	return encheresDAO.aEncheriSurArticle(noArticle, acheteur);
	}

	@Override
	public void surenchere(int noArticle, long montantEnchere, Utilisateur acheteur) {
		if((encheresDAO.findEnchereUtilisateur(noArticle, acheteur)) != null) {
			deleteByIdUtilisateurAndIdArticle(noArticle, acheteur);
		}
		
		encheresDAO.createEnchere(acheteur, noArticle, montantEnchere);
		
		
		
	//	encheresDAO.surenchere(noArticle, montantEnchere, acheteur);

	}

	@Override
	public List<Enchere> findAll() {
		return encheresDAO.findAll();
	}

}