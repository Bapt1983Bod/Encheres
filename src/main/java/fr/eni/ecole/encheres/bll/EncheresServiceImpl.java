package fr.eni.ecole.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.EncheresDAO;

@Service
public class EncheresServiceImpl implements EncheresService {

	private EncheresDAO encheresDAO;

	public EncheresServiceImpl(EncheresDAO encheresDAO) {
		this.encheresDAO = encheresDAO;
	}

	// Vérifier si vente ouverte avant d'insérer l'enchère (comparer date du jour
	// avec date de début et date de fin de la vente
	@Override
	public void encherir(int noArticle, long montantEnchere, Utilisateur utilisateur) {
		encheresDAO.encherir(noArticle, montantEnchere, utilisateur);
	}

	@Override
	public void deleteByIdArticle(int noArticle) {
		encheresDAO.deleteByIdArticle(noArticle);
	}

	@Override
	public void deleteByNoUtilisateur(int noUtilisateur) {
		encheresDAO.deleteByNoUtilisateur(noUtilisateur);

	}

	@Override
	public void deleteByIdUtilisateurAndIdArticle(int noArticle, Utilisateur utilisateur) {
		encheresDAO.deleteByIdUtilisateurAndIdArticle(noArticle, utilisateur);
	}

	@Override
	public Enchere getHighestEnchere(int noArticle) {
		return encheresDAO.getHighestEnchere(noArticle);
	}

}