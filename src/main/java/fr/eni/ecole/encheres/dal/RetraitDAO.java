package fr.eni.ecole.encheres.dal;

public interface RetraitDAO {
	
	public void createRetrait(int idArticle, String rue, String codePostal, String ville);

}
