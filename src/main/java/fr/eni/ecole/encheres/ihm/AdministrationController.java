package fr.eni.ecole.encheres.ihm;

import java.util.List;

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
import fr.eni.ecole.encheres.bo.Utilisateur;

@Controller
public class AdministrationController {

	UtilisateurService utilisateurService;
	ArticlesService articlesService;
	RetraitService retraitService;
	EncheresService encheresService;

	public AdministrationController(UtilisateurService utilisateurService, ArticlesService articlesService, RetraitService retraitService,EncheresService encheresService ) {
		this.utilisateurService = utilisateurService;
		this.articlesService = articlesService;
		this.retraitService = retraitService;
		this.encheresService = encheresService;
	}

	// Affichage de la page d'administration des comptes
	@GetMapping("/administration")
	public String affichagePageAdmin(Model modele) {
		// Récupération de l'utilisateur connecté
		Utilisateur utilisateur = utilisateurService.getUtilisateurConnecte();

		// liste des utilisateurs sans l'utilisateur connecté (pour admin)
		List<Utilisateur> listUtilisateurs = utilisateurService.findUtilisateurs(utilisateur);
		modele.addAttribute("listUtilisateurs", listUtilisateurs);

		return "administration";
	}

	// Désactivation d'un compte
	@PostMapping("/desactivation")
	public String desactivationCompteUtilisateur(@RequestParam("noUtilisateur") int noUtilisateur) {
		int statut = (-1);
		List<ArticleVendu> listArticleUtilisateur = articlesService.findByNoUtilisateur(noUtilisateur);
		System.out.println("liste des articles : "+listArticleUtilisateur);
		Utilisateur utilisateur = utilisateurService.findById(noUtilisateur);
	
		if (!listArticleUtilisateur.isEmpty()) {
		for (ArticleVendu article : listArticleUtilisateur) {
			retraitService.deleteRetrait(article.getNoArticle());
		//	encheresService.deleteByIdUtilisateurAndIdArticle(article.getNoArticle());
		}
		}
		articlesService.deleteByNoUtilisateur(noUtilisateur);
		utilisateurService.statutUtilisateur(noUtilisateur, statut);
		return "redirect:/administration";
	}

	// Activer un compte
	@PostMapping("/activation")
	public String activationCompteUtilisateur(@RequestParam("noUtilisateur") int noUtilisateur) {
		utilisateurService.statutUtilisateur(noUtilisateur, 0);
		return "redirect:/administration";
	}

	// Paaser un compte en admin
	@PostMapping("/admin")
	public String passageUtilisateurAdmin (@RequestParam("noUtilisateur") int noUtilisateur) {
		utilisateurService.statutUtilisateur(noUtilisateur, 1);
		return "redirect:/administration";
	}

	// Suppression d'un compte par l'administrateur
	@PostMapping("/suppressionAdmin")
	public String suppresionCompteUtilisateur(@RequestParam("pseudoUtilisateur") String pseudo) {
		Utilisateur utilisateur = utilisateurService.findByPseudo(pseudo);
		utilisateurService.supprimerUtilisateur(utilisateur);

		return "redirect:/administration";
	}
}
