package fr.eni.ecole.encheres.ihm;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Controller
public class AdministrationController {

	UtilisateurService utilisateurService;

	public AdministrationController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
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
