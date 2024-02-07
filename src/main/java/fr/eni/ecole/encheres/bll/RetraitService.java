package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface RetraitService {
	
	public void createRetrait(int idArticle, Utilisateur vendeur, String rue, String codePostal, String ville);

}
