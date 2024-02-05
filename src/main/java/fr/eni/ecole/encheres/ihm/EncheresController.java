package fr.eni.ecole.encheres.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EncheresController {
	
	public EncheresController() {
		}
	
	
	
	// affichage de la page d'accueil
	@GetMapping ({"/","/accueil"})
	public String accueil() {
		return "accueil";
	}
	
	@GetMapping ("/login")
	String login() {
		return "accueil";
	}
	
	@GetMapping ("/test")
	public String test() {
		return "test";
	}
	

}
