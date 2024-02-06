package fr.eni.ecole.encheres.ihm;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.CategorieService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.CategorieDAO;

@Controller
public class EncheresController {

	private UtilisateurService utilisateurService;
	private ArticlesService articlesService;
	private CategorieService categorieService;

	public EncheresController(UtilisateurService utilisateurService, ArticlesService articlesService,
			CategorieService categorieService) {
		this.utilisateurService = utilisateurService;
		this.articlesService = articlesService;
		this.categorieService = categorieService;
	}

	// affichage de la page d'accueil
	@GetMapping({ "/", "/accueil" ,"/login"})
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



	// utilisation des filtres pages d'accueil
//	@PostMapping ("/filtres")
//	public String accueilFiltre (@RequestParam ("textFilter") String string, @RequestParam ("selectCategorie") int id, Model modele) {
//		System.out.println("on passe par la");
//		
//		// Récupération de la liste des articles
//		List<ArticleVendu>listArticles=articlesService.findByCatAndString(id, string);
//		// Récupération de la liste des catégories
//		List<Categorie> listCategories = categorieService.findAll();
//
//		// Ajout des listes au modèle
//		modele.addAttribute("listArticles",listArticles);
//		modele.addAttribute("listCategories",listCategories);
//		System.out.println(modele);
//		
//		return "accueil";
//	}

	@GetMapping("/inscription")
	public String afficherFormulaireInscription(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "inscription";
	}

	@PostMapping("/inscription")
	public String traiterFormulaireInscription(@ModelAttribute Utilisateur utilisateur) {
		utilisateurService.creerUtilisateur(utilisateur);

		return "/accueil";
	}

	@GetMapping ("/vendre")
	public String afficherFormulaireVente(Model modele) {
		// Création d'un article vide
		ArticleVendu article = new ArticleVendu();
		modele.addAttribute("article", article);
				
		// Récupération de la liste des catégories
		List<Categorie> listCategories = categorieService.findAll();
		modele.addAttribute("listCategories", listCategories);
		return "vendre";
	}
	
	@PostMapping ("/vendre")
	public String ajouterArticle(@ModelAttribute ("article") ArticleVendu article, BindingResult bindingResult) {
		System.out.println("méthode ajouter article");
		if (bindingResult.hasErrors()) {
			return "vendre";
		} else {
			System.out.println(article);
			return "redirect:/vendre";
		}
		
		
		
	}
	
}
