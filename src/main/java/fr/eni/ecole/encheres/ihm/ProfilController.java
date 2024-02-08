package fr.eni.ecole.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.CategorieService;
import fr.eni.ecole.encheres.bll.RetraitService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;

@Controller
public class ProfilController {

	private UtilisateurService utilisateurService;

	public ProfilController(UtilisateurService utilisateurService, ArticlesService articlesService,
			CategorieService categorieService, RetraitService retraitService) {
		this.utilisateurService = utilisateurService;
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
	public String modifierUtilisateur(@ModelAttribute Utilisateur utilisateurModifie, @RequestParam("newPwd") String pwd, @RequestParam("newPwdConfirm") String pwdConfirm, Model model) {
		
		if (pwd.equals(pwdConfirm) && pwd.length()>0 ) {
			utilisateurModifie.setMotDePasse(pwd);
		}
		
		
			utilisateurService.modifierUtilisateur(utilisateurModifie);
			return "redirect:/profil"; // Redirige l'utilisateur vers sa page de profil après la modification
		}

	// suppression du profil

	@PostMapping("/supprimer-compte")
	public String supprimerUtilisateur() {

		Utilisateur utilisateur = utilisateurService.getIdUtilisateurConnecte();
		utilisateurService.supprimerUtilisateur(utilisateur);
		return "redirect:/logout";
	}

}
