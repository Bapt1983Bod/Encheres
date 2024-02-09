package fr.eni.ecole.encheres.ihm;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.EncheresService;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Controller
public class AchatController {

	private ArticlesService articlesService;
	private EncheresService encheresService;

	public AchatController(ArticlesService articlesService, EncheresService encheresService) {
		this.articlesService = articlesService;
		this.encheresService = encheresService;
	}

	@GetMapping("/acheter")
	public String afficherDetailsVente(@RequestParam("noArticle") int noArticle, Model model) {
		ArticleVendu articleVendu = articlesService.findArticleByNoArticle(noArticle);

		model.addAttribute("article", articleVendu);

		return "encheres";
	}

	@PostMapping("/encherir")
	public String encherir(@RequestParam("noArticle") int noArticle,
			@RequestParam("montantEnchere") long montantEnchere, @AuthenticationPrincipal Utilisateur utilisateur, // cherche
																													// à
																													// récupérer
																													// l'utilisateur
																													// connecté
																													// avec
																													// @Authentification
			Model model) {

		long creditUtilisateur = utilisateur.getCredit(); // récupére le crédit de l'utilisateur et stocke dans la
															// variabel creditUtilisateur
		encheresService.encherir(noArticle, montantEnchere, creditUtilisateur); // appel de la methode encherir
		return "redirect:/acheter?noArticle=" + noArticle;

	}
}