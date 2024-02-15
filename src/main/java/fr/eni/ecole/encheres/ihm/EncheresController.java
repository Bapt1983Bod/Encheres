
package fr.eni.ecole.encheres.ihm;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.CategorieService;
import fr.eni.ecole.encheres.bll.EncheresService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Controller
public class EncheresController {

	private ArticlesService articlesService;
	private CategorieService categorieService;
	private UtilisateurService utilisateurService;
	private EncheresService encheresService;

	public EncheresController(ArticlesService articlesService, CategorieService categorieService,
			UtilisateurService utilisateurService, EncheresService encheresService) {
		this.utilisateurService = utilisateurService;
		this.articlesService = articlesService;
		this.categorieService = categorieService;
		this.encheresService = encheresService;
	}

	// affichage de la page d'accueil
	@GetMapping({ "/", "/accueil", "/login", "/accueilLogo" })
	public String accueil(Model modele) {
		// Récupération de la liste des articles
		List<ArticleVendu> listArticles = articlesService.findAllEnCours();
		for (ArticleVendu art : listArticles) {
			articlesService.setEtatVente(art);
		}
		// Récupération de la liste des catégories
		List<Categorie> listCategories = categorieService.findAll();
		
		// Ajout des listes au modèle
		modele.addAttribute("listArticles", listArticles);
		modele.addAttribute("listCategories", listCategories);
		
		return "accueil";
	}

	// utilisation des filtres pages d'accueil
	@PostMapping("/filtres")
	public String accueilFiltre(@RequestParam("textFilter") String string,
			@RequestParam("selectCategorie") int idcategory,
			@RequestParam(value = "filtre", required = false) String filtre,
			@RequestParam(value = "vente", required = false) String vente, 
			@RequestParam(value = "encheres", required = false) String encheres,
			Model modele) {
		
		System.out.println("on passe içi");
		
		// Initialisation de la liste
		List<ArticleVendu> listArticles = new ArrayList<ArticleVendu>();

		// Récupération de la liste des articles en fonction du texte de recherche, de
		// la catégorie et du type de filtre
		if (idcategory != 0) {
			if (filtre != null) {
				// Récupération utilisateur connecté
				Utilisateur utilisateur = utilisateurService.getUtilisateurConnecte();
				if (filtre.equals("ventes")) {
					// récupération de toutes les ventes l'utilisateur connecté en fonction de la
					// categorie
					List<ArticleVendu> maListNonTriée = articlesService
							.findByNoUtilCatString(utilisateur.getNoUtilisateur(), idcategory, string);

					// Recuperation en fonction de l'état de la vente (En cours, en attente,
					// terminée)
					listArticles = articlesService.filtreVentes(vente, maListNonTriée);
				} else if (filtre.equals("achats")) {
					// Recuperation en fonction de l'état de l'enchère (en cours, remportéé)
					if (encheres.equals("enCours")) {
						listArticles = encheresService.listEnchUtilisateurEnCours(utilisateur);
					}else if (encheres.equals("remportees")) {
						listArticles = encheresService.listEnchPlusHauteUtilisateur(utilisateur);
					}
				}
			} else {
				// Si aucun filtre n'est sélectionné, récupérez tous les articles
				listArticles = articlesService.findByCatAndString(idcategory, string);
			}
		} else if (idcategory == 0) {
			if (filtre != null) {
				// Récupération utilisateur connecté
				Utilisateur utilisateur = utilisateurService.getUtilisateurConnecte();
				if (filtre.equals("ventes")) {
					// récupération de toutes les ventes l'utilisateur connecté
					List<ArticleVendu> maListNonTriée = articlesService
							.findByNoUtilString(utilisateur.getNoUtilisateur(), string);
					// Recuperation en fonction de l'état de la vente (En cours, en attente,
					// terminée)
					listArticles = articlesService.filtreVentes(vente, maListNonTriée);
				} else if (filtre.equals("achats")) {
					// Recuperation en fonction de l'état de l'enchère (en cours, remportéé)
						if (encheres.equals("enCours")) {
							listArticles = encheresService.listEnchUtilisateurEnCours(utilisateur);
						}else if (encheres.equals("remportees")) {
							listArticles = encheresService.listEnchPlusHauteUtilisateur(utilisateur);
						}
				}
			} else {
				listArticles = articlesService.findByString(string);
			}
		} 

		// Récupération de la liste des catégories
		List<Categorie> listCategories = categorieService.findAll();

		// Ajout des listes au modèle
		modele.addAttribute("listArticles", listArticles);
		modele.addAttribute("listCategories", listCategories);

		return "accueil";
	}

}
