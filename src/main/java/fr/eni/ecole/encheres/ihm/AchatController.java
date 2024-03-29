package fr.eni.ecole.encheres.ihm;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.EncheresService;
import fr.eni.ecole.encheres.bll.RetraitService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Retrait;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Controller
public class AchatController {

	private ArticlesService articlesService;
	private EncheresService encheresService;
	private UtilisateurService utilisateurService;
	private RetraitService retraitService;

	public AchatController(ArticlesService articlesService, EncheresService encheresService,
			UtilisateurService utilisateurService, RetraitService retraitService) {
		this.articlesService = articlesService;
		this.encheresService = encheresService;
		this.utilisateurService = utilisateurService;
		this.retraitService = retraitService;
	}

	// affichage du détail de la vente d'un article
	@GetMapping("/acheter")
	public String afficherDetailsVente(@RequestParam("noArticle") int noArticle, Model model) {
		ArticleVendu articleVendu = articlesService.findArticleByNoArticle(noArticle);
		Enchere highestEnchere = encheresService.getHighestEnchere(noArticle); // afficher l'enchere la plus haute
		Utilisateur utilisateurConnecte = utilisateurService.getUtilisateurConnecte();
		Retrait retrait = retraitService.readRetrait(articleVendu.getNoArticle());
		boolean peutSurencherir = false;

		ArticleVendu articleRemporte = articlesService.setEtatVente(articleVendu);
		boolean aRemporteLaVente = false;

		if (articleRemporte.getEtatVente().equals("terminee")
				&& articleRemporte.getDateFinEncheres().before(new Date())) {

			aRemporteLaVente = true;
		}

		// si utilisateur connecté à déja enchéri sur l'article, retourne true et peut
		// surenchérir
		if (encheresService.aEncheriSurArticle(noArticle, utilisateurConnecte)) {
			peutSurencherir = true;
		}
		model.addAttribute("article", articleVendu);
		model.addAttribute("highestEnchere", highestEnchere);
		model.addAttribute("utilisateurConnecte", utilisateurConnecte);
		model.addAttribute("peutSurencherir", peutSurencherir);
		model.addAttribute("aRemporteLaVente", aRemporteLaVente);
		model.addAttribute("retrait", retrait);

		return "encheres";
	}

	// permettre une premiere enchere sur un article
	@PostMapping("/encherir")
	public String encherir(@RequestParam("noArticle") int noArticle,
			@RequestParam("montantEnchere") long montantEnchere, Model model) {

		Utilisateur acheteur = utilisateurService.getUtilisateurConnecte();

		encheresService.encherir(noArticle, montantEnchere, acheteur); // appel de la methode encherir
		return "redirect:/acheter?noArticle=" + noArticle;

	}

	// permettre une surenchere après une premiere enchere sur l'article
	@PostMapping("/surenchere")
	public String surenchere(@RequestParam("noArticle") int noArticle,
			@RequestParam("montantEnchere") long montantEnchere, Model model) {
		Utilisateur acheteur = utilisateurService.getUtilisateurConnecte();

		// Rembourser l'enchère précédente et la supprimer
		encheresService.deleteByIdUtilisateurAndIdArticle(noArticle, acheteur);

		// Effectuer la surenchère
		encheresService.encherir(noArticle, montantEnchere, acheteur);

		return "redirect:/acheter?noArticle=" + noArticle;
	}

}