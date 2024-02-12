package fr.eni.ecole.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.Retrait;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.RetraitDAO;

@Service
public class RetraitServiceImpl implements RetraitService {

	private RetraitDAO retraitDAO;

	public RetraitServiceImpl(RetraitDAO retraitDAO) {
		this.retraitDAO = retraitDAO;
	}

	// Création d'un retrait
	@Override
	public void createRetrait(int idArticle, Utilisateur vendeur, String rue, String codePostal, String ville) {

		// Vérification si retrait renseigné dans le formulaire (si un des champs vides
		// => utilisation adresse du vendeur)
		if (rue.isBlank() || codePostal.isBlank() || ville.isBlank()) {
			// création point de retrait avec adresse du vendeur
			retraitDAO.createRetrait(idArticle, vendeur.getRue(), vendeur.getCodePostal(), vendeur.getVille());
		} else {
			// création point de retrait avec adresse du formulaire
			retraitDAO.createRetrait(idArticle, rue, codePostal, ville);
		}
	}

	@Override
	public Retrait readRetrait(int idArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRetrait(int idArticle) {
		retraitDAO.deleteRetrait(idArticle);
	}

}
