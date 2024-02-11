
package fr.eni.ecole.encheres.bo;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Utilisateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int noUtilisateur;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Le pseudo ne doit contenir que des caractères alphanumériques")
	private String pseudo;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z-' ]+$", message = "Le nom ne doit contenir que des lettres, des tirets (-) et des apostrophes (')")
	private String nom;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z-' ]+$", message = "Le nom ne doit contenir que des lettres, des tirets (-) et des apostrophes (')")
	private String prenom;
	@NotBlank
	private String email;
	@NotBlank
	@Pattern(regexp = "\\d+", message = "Le code postal ne doit contenir que des chiffres")
	private String telephone;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z-' ]+$", message = "Le nom de la rue ne doit contenir que des lettres, des tirets (-) et des apostrophes (')")
	private String rue;
	@NotBlank
	@Pattern(regexp = "\\d+", message = "Le code postal ne doit contenir que des chiffres")
	private String codePostal;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z-' ]+$", message = "Le nom de la ville ne doit contenir que des lettres, des tirets (-) et des apostrophes (')")
	private String ville;
	@NotBlank
	private String motDePasse;
	private int credit;
	private int administrateur;
	private List<ArticleVendu> listArticleVendu;
	private List<Enchere> listEncheres;

	public Utilisateur() {
	}

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
	}

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, int credit, int administrateur,
			List<ArticleVendu> listArticleVendu, List<Enchere> listEncheres) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
		this.listArticleVendu = listArticleVendu;
		this.listEncheres = listEncheres;
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(int administrateur) {
		this.administrateur = administrateur;
	}

	public List<ArticleVendu> getListArticleVendu() {
		return listArticleVendu;
	}

	public void setListArticleVendu(List<ArticleVendu> listArticleVendu) {
		this.listArticleVendu = listArticleVendu;
	}

	public List<Enchere> getListEncheres() {
		return listEncheres;
	}

	public void setListEncheres(List<Enchere> listEncheres) {
		this.listEncheres = listEncheres;
	}

	@Override
	public String toString() {
		return "Utilisateur [noUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + ", listArticleVendu=" + listArticleVendu + ", listEncheres="
				+ listEncheres + "]";
	}

}
