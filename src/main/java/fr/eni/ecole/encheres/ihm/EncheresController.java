
package fr.eni.ecole.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;

@Controller
public class EncheresController {

	private UtilisateurService utilisateurService;

	public EncheresController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/inscription")
	public String afficherFormulaireInscription(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "inscription";
	}

	@PostMapping("/inscription")
	public String traiterFormulaireInscription(@ModelAttribute Utilisateur utilisateur, Model model)
			throws BusinessException {
		try {
			utilisateurService.creerUtilisateur(utilisateur);
			return "redirect:/inscription";
		} catch (BusinessException e) {

			model.addAttribute("erreur", e.getMessages()); // ajouter le message d'erreur au modèle
			System.out.println(model);
			return "inscription";

		}
	}
}
