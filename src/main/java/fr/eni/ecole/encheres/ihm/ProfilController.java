package fr.eni.ecole.encheres.ihm;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.CategorieService;
import fr.eni.ecole.encheres.bll.EncheresService;
import fr.eni.ecole.encheres.bll.RetraitService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;
import jakarta.validation.Valid;

@Controller
public class ProfilController {

	private UtilisateurService utilisateurService;
	private ArticlesService articlesService;
	private RetraitService retraitService;
	private EncheresService encheresService;

	public ProfilController(UtilisateurService utilisateurService, ArticlesService articlesService,
			RetraitService retraitService, EncheresService encheresService) {
		this.utilisateurService = utilisateurService;
		this.articlesService = articlesService;
		this.retraitService = retraitService;
		this.encheresService = encheresService;
	}

	// affichage du profil
	@GetMapping("/profil")
	public String afficherProfil(Model modele) {
		// Récupérer l'utilisateur connecté
		Utilisateur utilisateurConnecte = utilisateurService.getUtilisateurConnecte();

		// Ajouter l'utilisateur au modèle
		modele.addAttribute("utilisateur", utilisateurConnecte);
		return "profil";
	}

	// afficher page de modification de profil

	@GetMapping("/modification-profil")
	public String afficherPageModificationProfil(Model model) {
		Utilisateur utilisateurConnecte = utilisateurService.getUtilisateurConnecte();

		model.addAttribute("utilisateur", utilisateurConnecte);
		return "modification-profil";
	}

	// modification du profil

	@PostMapping("/modification-profil")
	public String modifierUtilisateur(@Valid @ModelAttribute Utilisateur utilisateur,
			BindingResult bindingResult, @RequestParam("motDePasse") String pwd,
			@RequestParam("newPwdConfirm") String pwdConfirm, Model model) {

		System.out.println("on rentre ici");
		
		if (pwd.equals(pwdConfirm) && pwd.length() > 0) {
				utilisateur.setMotDePasse(pwd);
				System.out.println("mot de passe");
			}
		
		System.out.println("utilisateur modifié : "+ utilisateur);
		
		if (bindingResult.hasErrors()) {
			System.out.println("erreur");
			return "modification-profil";
		} else {
			// Modification du mot de passe
			
			
			// Modification de l'utilisateur
			utilisateurService.modifierUtilisateur(utilisateur);
			System.out.println("utilisateur modifié");

			return "redirect:/profil"; // Redirige l'utilisateur vers sa page de profil après la modification
		}
	}

	// suppression du profil

	@PostMapping("/supprimer-compte")
	public String supprimerUtilisateur() {

		Utilisateur utilisateur = utilisateurService.getUtilisateurConnecte();

		// Récupération de la liste des articles de l'utilisateur
		List<ArticleVendu> listArticleUtilisateur = articlesService.findByNoUtilisateur(utilisateur.getNoUtilisateur());

		if (!listArticleUtilisateur.isEmpty()) {

			for (ArticleVendu article : listArticleUtilisateur) {

				// suppression des points de retrait liés aux articles de l'utilisateur
				retraitService.deleteRetrait(article.getNoArticle());
				// suppression des encheres sur les articles de l'utilisateur et recredit des
				// utilisateurs
				encheresService.deleteByIdArticle(article.getNoArticle());
			}

			// suppression des articles de l'utilisateur
			articlesService.deleteByNoUtilisateur(utilisateur.getNoUtilisateur());
		}

		// suppression des enchères de l'utilisateursur les autres articles
		encheresService.deleteByNoUtilisateur(utilisateur.getNoUtilisateur());

		// suppression de l'utilisateur
		utilisateurService.supprimerUtilisateur(utilisateur);
		return "redirect:/logout";
	}

}
