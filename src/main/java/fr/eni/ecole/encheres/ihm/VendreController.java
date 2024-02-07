package fr.eni.ecole.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.RetraitService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Controller
public class VendreController {

	private UtilisateurService utilisateurService;
	private ArticlesService articlesService;
	private RetraitService retraitService;

	public VendreController(UtilisateurService utilisateurService, ArticlesService articlesService, RetraitService retraitService) {
		this.utilisateurService = utilisateurService;
		this.articlesService = articlesService;
		this.retraitService = retraitService;
	}

@PostMapping("/vendre")
public String ajouterArticle(@ModelAttribute("article") ArticleVendu article, BindingResult bindingResult,
		@RequestParam("rue") String rue, @RequestParam("codePostal") String codePostal,
		@RequestParam("ville") String ville) {

	
	if (bindingResult.hasErrors()) {
		return "vendre";
	} else {
		// Récupération de l'id de l'utilisateur connecté
		Utilisateur vendeur = utilisateurService.getIdUtilisateurConnecte();
		
		// Création de l'article en bdd et récupération du no d'article
		int idArticle = articlesService.createArticle(vendeur, article);
		
		
		// création du point de retrait
		retraitService.createRetrait(idArticle, vendeur, rue, codePostal, ville);
		
		return "redirect:/vendre";
	}

}

}