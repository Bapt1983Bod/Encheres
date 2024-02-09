package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface EncheresService {

	public void encherir(int noArticle, long montantEnchere);

	void encherir(int noArticle, long montantEnchere, Utilisateur utilisateur);
}
