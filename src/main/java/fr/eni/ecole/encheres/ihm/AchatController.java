package fr.eni.ecole.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.eni.ecole.encheres.bll.EncheresService;

@Controller
public class AchatController {

	private EncheresService encheresService;

	public AchatController(EncheresService encheresService) {
		this.encheresService = encheresService;
	}

	@GetMapping("/acheter")
	public String AfficherDetailsVente(Model model) {
//		ArticleVendu articleVendu = encheresService.findArticleByNoArticle(null);
//
//		model.addAttribute("article", articleVendu);

		return "encheres";

	}
}