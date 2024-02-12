package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.Utilisateur;

public interface EncheresDAO {

	public void encherir(int noArticle, long montantEnchere, Utilisateur utilisateur);

	public void deleteByIdUtilisateurAndIdArticle(int noArticle, Utilisateur utilisateur);

	public void deleteByIdArticle(int noArticle);

	public void deleteByNoUtilisateur(int noUtilisateur);

	public void getHighestEnchere(int noArticle);
}
