package fr.eni.ecole.encheres.bo;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Retrait implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@NotBlank(message = "Le nom de l'article ne peut pas être vide")
	//@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Le nom de la rue ne doit contenir que des caractères alphanumériques")
	private String rue;
	//@Pattern(regexp = "\\d+", message = "Le code postal ne doit contenir que des chiffres")
	private String codePostal;
	//@NotBlank(message = "Le nom de l'article ne peut pas être vide")
	//@Pattern(regexp = "^[a-zA-Z-' ]+$", message = "Le nom de la ville ne doit contenir que des lettres, des tirets (-) et des apostrophes (')")
	private String ville;
	private ArticleVendu articleVendu;

	public Retrait() {
	}

	public Retrait(String rue, String codePostal, String ville) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	public Retrait(String rue, String codePostal, String ville, ArticleVendu articleVendu) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.articleVendu = articleVendu;
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

	public ArticleVendu getArticleVendu() {
		return articleVendu;
	}

	public void setArticleVendu(ArticleVendu articleVendu) {
		this.articleVendu = articleVendu;
	}

	@Override
	public String toString() {
		return "Retrait [rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville + ", articleVendu="
				+ articleVendu + "]";
	}

}