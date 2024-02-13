
package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;

public interface UtilisateurService {

	void creerUtilisateur(Utilisateur utilisateur, String confirmMdp) throws BusinessException;

	Utilisateur findByPseudo(String pseudo);
	
	Utilisateur findById (int noUtilisateur);

	Utilisateur getUtilisateurConnecte();

	public void modifierUtilisateur(Utilisateur utilisateur);

	void supprimerUtilisateur(Utilisateur utilisateur);

	List<Utilisateur> findAll();

	// liste des utilisateurs sans l'utilisateur connecté (pour admin)
	List<Utilisateur> findUtilisateurs(Utilisateur utilisateurConnecte);
	
	void statutUtilisateur (int noUtilisateur, int statut);
	
	public Boolean validePassword(String Password) throws BusinessException;
	
}
