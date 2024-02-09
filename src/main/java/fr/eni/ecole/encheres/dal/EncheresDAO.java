package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface EncheresDAO {

	public void encherir(int noArticle, long montantEnchere, Utilisateur utilisateur);

}
