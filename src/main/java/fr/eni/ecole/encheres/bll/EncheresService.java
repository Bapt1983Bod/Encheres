package fr.eni.ecole.encheres.bll;

public interface EncheresService {

	public void encherir(int noArticle, long montantEnchere);

	void encherir(int noArticle, long montantEnchere, long creditUtilisateur);
}
