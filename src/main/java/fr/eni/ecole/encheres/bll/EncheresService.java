package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface EncheresService {

	void encherir(int noArticle, long montantEnchere, Utilisateur utilisateur);
	
	public void deleteByIdUtilisateurAndIdArticle (int noArticle, Utilisateur utilisateur);
}
