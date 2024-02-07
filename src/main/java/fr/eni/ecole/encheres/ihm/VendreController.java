package fr.eni.ecole.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bll.CategorieService;
import fr.eni.ecole.encheres.bll.RetraitService;
import fr.eni.ecole.encheres.bll.UtilisateurService;
import fr.eni.ecole.encheres.bo.ArticleVendu;

@Controller
public class VendreController {

	private UtilisateurService utilisateurService;
	private ArticlesService articlesService;
	private CategorieService categorieService;
	private RetraitService retraitService;

	public VendreController(UtilisateurService utilisateurService, ArticlesService articlesService,
			CategorieService categorieService, RetraitService retraitService) {
		this.utilisateurService = utilisateurService;
		this.articlesService = articlesService;
		this.categorieService = categorieService;
		this.retraitService = retraitService;
	}

@PostMapping("/vendre")
public String ajouterArticle(@ModelAttribute("article") ArticleVendu article, BindingResult bindingResult,
		@RequestParam("rue") String rue, @RequestParam("codePostal") String codePostal,
		@RequestParam("ville") String ville) {
	System.out.println("méthode ajouter article");
	if (bindingResult.hasErrors()) {
		return "vendre";
	} else {

		int idVendeur = utilisateurService.getIdUtilisateurConnecte().getNoUtilisateur();

		articlesService.createArticle(idVendeur, article);
		return "redirect:/vendre";
	}

}

}