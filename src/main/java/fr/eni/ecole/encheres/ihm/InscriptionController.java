package fr.eni.ecole.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.CategorieService;
import fr.eni.ecole.encheres.bll.RetraitService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;
import jakarta.validation.Valid;

@Controller
public class InscriptionController {
	
	private UtilisateurService utilisateurService;


	public InscriptionController(UtilisateurService utilisateurService, ArticlesService articlesService,
			CategorieService categorieService, RetraitService retraitService) {
		this.utilisateurService = utilisateurService;
	}
	
	@GetMapping("/inscription")
	public String afficherFormulaireInscription(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "inscription";
	}

	@PostMapping("/inscription")
	public String traiterFormulaireInscription(@Valid @ModelAttribute Utilisateur utilisateur, Model model)
			throws BusinessException {
		try {
			utilisateurService.creerUtilisateur(utilisateur);
			return "redirect:/accueil";
		} catch (BusinessException e) {

			model.addAttribute("erreur", e.getMessages()); // ajouter le message d'erreur au modèle
			System.out.println(model);
			return "inscription";
		}
	}
}
