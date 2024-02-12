package fr.eni.ecole.encheres.dal;

import fr.eni.ecole.encheres.bo.Retrait;

public interface RetraitDAO {
	
	public void createRetrait(int idArticle, String rue, String codePostal, String ville);
	
	public Retrait readRetrait (int idArticle);
	
	public void deleteRetrait (int idArticle);
	
	

}
