
package fr.eni.ecole.encheres.ihm;

import java.util.List;

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

	private UtilisateurService utilisateurService;
	private ArticlesService articlesService;
	private CategorieService categorieService;
	private RetraitService retraitService;

	public EncheresController(UtilisateurService utilisateurService, ArticlesService articlesService,
			CategorieService categorieService, RetraitService retraitService) {
		this.utilisateurService = utilisateurService;
		this.articlesService = articlesService;
		this.categorieService = categorieService;
		this.retraitService = retraitService;
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

	// affichage du profil
	@GetMapping("/profil")
	public String afficherProfil(Model modele) {
		// Récupérer l'utilisateur connecté
		Utilisateur utilisateurConnecte = utilisateurService.getIdUtilisateurConnecte();

		// Ajouter l'utilisateur au modèle
		modele.addAttribute("utilisateur", utilisateurConnecte);
		return "profil";
	}

	// afficher page de modification de profil

	@GetMapping("/modification-profil")
	public String afficherPageModificationProfil(Model model) {
		Utilisateur utilisateurConnecte = utilisateurService.getIdUtilisateurConnecte();
		model.addAttribute("utilisateur", utilisateurConnecte);
		return "modification-profil";
	}

	// modification du profil

	@PostMapping("/modification-profil")
	public String modifierUtilisateur(@ModelAttribute Utilisateur utilisateurModifie, Model model) {
		utilisateurService.modifierUtilisateur(utilisateurModifie);
		return "redirect:/profil"; // Redirige l'utilisateur vers sa page de profil après la modification
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
	public String traiterFormulaireInscription(@ModelAttribute Utilisateur utilisateur, Model model)
			throws BusinessException {
		try {
			utilisateurService.creerUtilisateur(utilisateur);
			return "redirect:/accueil";
		} catch (BusinessException e) {

			model.addAttribute("erreur", e.getMessages()); // ajouter le message d'erreur au modèle
			System.out.println(model);
			return "inscription";
		}
	}

	@GetMapping("/vendre")
	public String afficherFormulaireVente(Model modele) {
		// Création d'un article vide
		ArticleVendu article = new ArticleVendu();
		modele.addAttribute("article", article);

		System.out.println("id utilisateur : " + utilisateurService.getIdUtilisateurConnecte());

		// Récupération de la liste des catégories
		List<Categorie> listCategories = categorieService.findAll();
		modele.addAttribute("listCategories", listCategories);
		return "vendre";
	}

//	@PostMapping("/vendre")
//	public String ajouterArticle(@ModelAttribute("article") ArticleVendu article, BindingResult bindingResult,
//			@RequestParam("rue") String rue, @RequestParam("codePostal") String codePostal,
//			@RequestParam("ville") String ville) {
//		System.out.println("méthode ajouter article");
//		if (bindingResult.hasErrors()) {
//			return "vendre";
//		} else {
//
//			int idVendeur = utilisateurService.getIdUtilisateurConnecte().getNoUtilisateur();
//
//			articlesService.createArticle(idVendeur, article);
//			return "redirect:/vendre";
//		}
//
//	}

}
