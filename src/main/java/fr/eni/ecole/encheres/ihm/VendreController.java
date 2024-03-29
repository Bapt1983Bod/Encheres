package fr.eni.ecole.encheres.ihm;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.CategorieService;
import fr.eni.ecole.encheres.bll.RetraitService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.bo.Retrait;
import fr.eni.ecole.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

@Controller
public class VendreController {

	private UtilisateurService utilisateurService;
	private ArticlesService articlesService;
	private RetraitService retraitService;
	private CategorieService categorieService;

	public VendreController(UtilisateurService utilisateurService, ArticlesService articlesService,
			RetraitService retraitService, CategorieService categorieService) {
		this.utilisateurService = utilisateurService;
		this.articlesService = articlesService;
		this.retraitService = retraitService;
		this.categorieService = categorieService;

	}

	@GetMapping("/vendre")
	public String afficherFormulaireVente(Model modele) {
		// Création d'un article vide
		ArticleVendu article = new ArticleVendu();
		modele.addAttribute("article", article);

		// Récupération de la liste des catégories
		List<Categorie> listCategories = categorieService.findAll();
		modele.addAttribute("listCategories", listCategories);
		return "vendre";
	}

	@PostMapping("/vendre")
	public String ajouterArticle(@Valid @ModelAttribute("article") ArticleVendu article, BindingResult bindingResult,
			@RequestParam("rue") String rue, @RequestParam("codePostal") String codePostal,
			@RequestParam("ville") String ville) {

		if (bindingResult.hasErrors()) {

			return "vendre";
		} else {
			// Récupération de l'id de l'utilisateur connecté
			Utilisateur vendeur = utilisateurService.getUtilisateurConnecte();

			// Création de l'article en bdd et récupération du no d'article
			int idArticle = articlesService.createArticle(vendeur, article);

			// création du point de retrait
			retraitService.createRetrait(idArticle, vendeur, rue, codePostal, ville);

			return "redirect:/accueil";
		}

	}

	@GetMapping("/modificationArticle")
	public String modificationArticle(@RequestParam("noArticle") int noArticle, Model modele) {
		// Récupération de l'article vendu
		ArticleVendu article = articlesService.findArticleByNoArticle(noArticle);
		// Récupération de la liste des catégories
		List<Categorie> listCategories = categorieService.findAll();
		// Récupération du retrait lié à l'article
		Retrait retrait = retraitService.readRetrait(noArticle);
		System.out.println(retrait);

		article.setLieuRetrait(retrait);
		System.out.println(article);

		modele.addAttribute("article", article);
		modele.addAttribute("listCategories", listCategories);

		return "modification-article";
	}

	@PostMapping("/modificationArticle")
	public String modifierArticle(@Valid @ModelAttribute("article") ArticleVendu article, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "modification-article";
		} else {

			// suppression du retrait
			retraitService.deleteRetrait(article.getNoArticle());

			// suppression de l'article
			articlesService.deleteByNoArticle(article.getNoArticle());

			// creation du nouvel article
			int noArticle = articlesService.createArticle(utilisateurService.getUtilisateurConnecte(), article);

			// création du nouveau retrait
			retraitService.createRetrait(noArticle, utilisateurService.getUtilisateurConnecte(),
					article.getLieuRetrait().getRue(), article.getLieuRetrait().getCodePostal(),
					article.getLieuRetrait().getVille());
		}
		return "redirect:/accueil";
	}

	@PostMapping("/supprimerArticle")
	public String supprimerArticle(@ModelAttribute("article") ArticleVendu article) {
		// suppression du retrait
		retraitService.deleteRetrait(article.getNoArticle());

		// suppression de l'article
		articlesService.deleteByNoArticle(article.getNoArticle());
		
		return "redirect:/accueil";
	}
}