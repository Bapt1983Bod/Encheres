package fr.eni.ecole.encheres.ihm;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
		// Récupération de la liste de tous les utilisateurs
		List<Utilisateur> listUtilisateurs = utilisateurService.findAll();
		modele.addAttribute("listUtilisateurs", listUtilisateurs);
		
		return "administration";
	}

	// Suppression d'un compte par l'administrateur
//	@PostMapping("/")
}
