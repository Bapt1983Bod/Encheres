package fr.eni.ecole.encheres.bll;

import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;

public interface EncheresService {

	void encherir(int noArticle, long montantEnchere, Utilisateur utilisateur);

	public void deleteByIdUtilisateurAndIdArticle(int noArticle, Utilisateur utilisateur);

	public void deleteByIdArticle(int noArticle);

	public void deleteByNoUtilisateur(int noUtilisateur);

	Enchere getHighestEnchere(int noArticle);

	boolean aEncheriSurArticle(int noArticle, Utilisateur acheteur);

	void surenchere(int noArticle, long montantEnchere, Utilisateur acheteur);

}
