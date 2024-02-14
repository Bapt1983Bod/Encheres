package fr.eni.ecole.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String traiterFormulaireInscription(@Valid @ModelAttribute Utilisateur utilisateur,
			BindingResult bindingResult, Model model, @RequestParam ("confirmationMotDePasse") String confirmMdp) {
		if (bindingResult.hasErrors()) {
			return "inscription";
		} else {
			try {
				utilisateurService.validePassword(utilisateur.getMotDePasse(), confirmMdp); 
				utilisateurService.creerUtilisateur(utilisateur, confirmMdp);
				
				return "redirect:/accueil";
				
			} catch (BusinessException e) {
				e.getMessages().forEach(erreur -> {
					ObjectError objectError = new ObjectError("globalError", erreur); // nom globalError obligatoire, ne peut pas être modifié.
					bindingResult.addError(objectError);
				});
				return "inscription";
			}
		}
	}
}
