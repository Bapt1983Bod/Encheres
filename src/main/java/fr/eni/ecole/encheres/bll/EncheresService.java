package fr.eni.ecole.encheres.bll;

import java.util.List;

import fr.eni.ecole.encheres.bo.ArticleVendu;
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

	// Renvoi la liste de toutes les enchères
	public List<Enchere> findAll();

	// Liste des enchères d'un utilisateur en cours
	public List<ArticleVendu> listEnchUtilisateurEnCours(Utilisateur utilisateur);

	// Renvoi des ecnchères les plus hautes
	public List<ArticleVendu> listEnchPlusHaute();

	// Renvoi des ecnchères les plus hautes
	public List<ArticleVendu> listEnchPlusHauteUtilisateur(Utilisateur utilisateur);

}
