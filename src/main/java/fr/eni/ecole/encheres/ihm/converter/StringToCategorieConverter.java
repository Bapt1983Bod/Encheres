package fr.eni.ecole.encheres.ihm.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.ecole.encheres.bll.CategorieService;
import fr.eni.ecole.encheres.bo.Categorie;

@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {

	private CategorieService categorieService;

	

	public StringToCategorieConverter(CategorieService categorieService) {
		super();
		this.categorieService = categorieService;
	}



	@Override
	public Categorie convert(String source) {
		Categorie categorie = categorieService.findById(Integer.parseUnsignedInt(source));
		System.out.println(categorie);
		return categorie;
	}

}
