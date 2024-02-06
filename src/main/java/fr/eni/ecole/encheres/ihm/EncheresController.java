package fr.eni.ecole.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Controller
public class EncheresController {
	
	
	private UtilisateurService utilisateurService;

	
	public EncheresController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	
	// affichage de la page d'accueil
	@GetMapping ({"/","/accueil"})
	public String accueil() {
		return "accueil";
	}
	
	// login
	@GetMapping ("/login")
	String login() {
		return "accueil";
	}
	
	
	//test
	@GetMapping ("/test")
	public String test() {
		return "test";
	}
	
	@GetMapping("/inscription")
	public String afficherFormulaireInscription(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "inscription";
	}

	@PostMapping("/inscription")
	public String traiterFormulaireInscription(@ModelAttribute Utilisateur utilisateur) {
		utilisateurService.creerUtilisateur(utilisateur);

		return "/accueil";
	}
	

}
