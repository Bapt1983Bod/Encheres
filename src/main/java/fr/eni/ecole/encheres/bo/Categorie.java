
package fr.eni.ecole.encheres.bo;

import java.io.Serializable;
import java.util.List;

public class Categorie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int noCategorie;
	private String libelle;
	private List<ArticleVendu> listArticleVendu;

	public Categorie() {
	}

	public Categorie(int noCategorie, String libelle) {
		this.noCategorie = noCategorie;
		this.libelle = libelle;
	}

	public Categorie(int noCategorie, String libelle, List<ArticleVendu> listArticleVendu) {
		this.noCategorie = noCategorie;
		this.libelle = libelle;
		this.listArticleVendu = listArticleVendu;
	}

	public int getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(int noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<ArticleVendu> getListArticleVendu() {
		return listArticleVendu;
	}

	public void setListArticleVendu(List<ArticleVendu> listArticleVendu) {
		this.listArticleVendu = listArticleVendu;
	}

	@Override
	public String toString() {
		return "Categorie [noCategorie=" + noCategorie + ", libelle=" + libelle + ", listArticleVendu="
				+ listArticleVendu + "]";
	}
}
