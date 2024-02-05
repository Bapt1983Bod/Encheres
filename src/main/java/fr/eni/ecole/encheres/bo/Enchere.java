package fr.eni.ecole.encheres.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Enchere implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDateTime dateEnchere;
	private int montantEnchere;
	private Utilisateur utilisateur;
	private ArticleVendu articleVendu;
	
	public Enchere() {
		}

	public Enchere(LocalDateTime dateEnchere, int montantEnchere, Utilisateur utilisateur, ArticleVendu articleVendu) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.utilisateur = utilisateur;
		this.articleVendu = articleVendu;
	}

	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public int getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public ArticleVendu getArticleVendu() {
		return articleVendu;
	}

	public void setArticleVendu(ArticleVendu articleVendu) {
		this.articleVendu = articleVendu;
	}

	@Override
	public String toString() {
		return "Enchere [dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere + ", utilisateur="
				+ utilisateur + ", articleVendu=" + articleVendu + "]";
	}

	
}
