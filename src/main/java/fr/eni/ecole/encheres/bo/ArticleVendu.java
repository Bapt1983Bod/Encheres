package fr.eni.ecole.encheres.bo;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleVendu implements Serializable {
	 
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int noArticle;
	private String nomArticle;
	private String description;
	private Date dateDebutEncheres;
	private Date dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private String etatVente;
	private List<Enchere> listeEnchere;
	private Retrait lieuRetrait;
	private Utilisateur acheteur;
	private Utilisateur vendeur;
	private Categorie categorie;
 
	public ArticleVendu() {
		super();
	}
 
	
	
	public ArticleVendu(String nomArticle, String description, Date dateDebutEncheres, Date dateFinEncheres,
			int miseAPrix, Categorie categorie) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.categorie = categorie;
	}



	public ArticleVendu(int noArticle, String nomArticle, String description, Date dateDebutEncheres,
			Date dateFinEncheres, int miseAPrix, int prixVente) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
	}
	
	
	
	public ArticleVendu(int noArticle, String nomArticle, String description, Date dateDebutEncheres,
			Date dateFinEncheres, int miseAPrix, int prixVente, String etatVente, List<Enchere> listeEnchere,
			Retrait lieuRetrait, Utilisateur acheteur, Utilisateur vendeur, Categorie categorie) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.listeEnchere = listeEnchere;
		this.lieuRetrait = lieuRetrait;
		this.acheteur = acheteur;
		this.vendeur = vendeur;
		this.categorie = categorie;
	}
 
	public int getNoArticle() {
		return noArticle;
	}
 
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}
 
	public String getNomArticle() {
		return nomArticle;
	}
 
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
 
	public String getDescription() {
		return description;
	}
 
	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateDebutEncheres() {
		return dateDebutEncheres;
	}
 
	public void setDateDebutEncheres(Date dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}
 
	public Date getDateFinEncheres() {
		return dateFinEncheres;
	}
 
	public void setDateFinEncheres(Date dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}
 
	public int getMiseAPrix() {
		return miseAPrix;
	}
 
	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}
 
	public int getPrixVente() {
		return prixVente;
	}
 
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}
 
	public String getEtatVente() {
		return etatVente;
	}
 
	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}
 
	public List<Enchere> getListeEnchere() {
		return listeEnchere;
	}
 
	public void setListeEnchere(List<Enchere> listeEnchere) {
		this.listeEnchere = listeEnchere;
	}
 
	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}
 
	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}
 
	public Utilisateur getUtilsateurA() {
		return acheteur;
	}
 
	public void setUtilsateurA(Utilisateur utilsateurA) {
		this.acheteur = utilsateurA;
	}
 
	public Utilisateur getUtilisateurV() {
		return vendeur;
	}
 
	public void setUtilisateurV(Utilisateur utilisateurV) {
		this.vendeur = utilisateurV;
	}
 
	public Categorie getCategorie() {
		return categorie;
	}
 
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}



	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", listeEnchere=" + listeEnchere
				+ ", lieuRetrait=" + lieuRetrait + ", acheteur=" + acheteur + ", vendeur=" + vendeur + ", categorie="
				+ categorie + "]";
	}
 

 
}
