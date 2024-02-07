
package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;

public interface UtilisateurService {

	Utilisateur creerUtilisateur(Utilisateur utilisateur) throws BusinessException;

	Utilisateur findByPseudo(String pseudo);

	Utilisateur getIdUtilisateurConnecte();

	public void modifierUtilisateur(Utilisateur utilisateur);

	void supprimerUtilisateur(Utilisateur utilisateur);
}
