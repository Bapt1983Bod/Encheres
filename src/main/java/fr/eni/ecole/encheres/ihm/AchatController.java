package fr.eni.ecole.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.ecole.encheres.bll.ArticlesService;
import fr.eni.ecole.encheres.bo.ArticleVendu;

@Controller
public class AchatController {

	private ArticlesService articlesService;

	public AchatController(ArticlesService articlesService) {
		this.articlesService = articlesService;
	}

	@GetMapping("/acheter")
	public String afficherDetailsVente(@RequestParam("noArticle") int noArticle, Model model) {
		ArticleVendu articleVendu = articlesService.findArticleByNoArticle(noArticle);
		model.addAttribute("article", articleVendu);
		return "encheres";
	}

	@PostMapping("/encherir")  //faire le nécessaire dans Security
	
	public String encherir(@RequestParam("articleId") Long articleId,
			@RequestParam("montantEnchere") int montantEnchere, Model model) {
		return "encheres";

	}
}