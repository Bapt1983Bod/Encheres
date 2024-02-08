
package fr.eni.ecole.encheres.ihm;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.CategorieService;
import fr.eni.ecole.encheres.bll.RetraitService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;

@Controller
public class EncheresController {


	private ArticlesService articlesService;
	private CategorieService categorieService;


	public EncheresController( ArticlesService articlesService,
			CategorieService categorieService) {
	
		this.articlesService = articlesService;
		this.categorieService = categorieService;
		
	}

	// affichage de la page d'accueil
	@GetMapping({ "/", "/accueil", "/login" })
	public String accueil(Model modele) {
		// Récupération de la liste des articles
		List<ArticleVendu> listArticles = articlesService.findAll();
		// Récupération de la liste des catégories
		List<Categorie> listCategories = categorieService.findAll();

		// Ajout des listes au modèle
		modele.addAttribute("listArticles", listArticles);
		modele.addAttribute("listCategories", listCategories);

		return "accueil";
	}



}
