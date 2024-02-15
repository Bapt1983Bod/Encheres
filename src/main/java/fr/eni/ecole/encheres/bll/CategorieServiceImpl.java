package fr.eni.ecole.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.Categorie;
import fr.eni.ecole.encheres.dal.CategorieDAO;

@Service
public class CategorieServiceImpl implements CategorieService {

	private CategorieDAO categorieDAO;

	public CategorieServiceImpl(CategorieDAO categorieDAO) {
		this.categorieDAO = categorieDAO;
	}

	// Récpuération de toutes les catégories
	@Override
	public List<Categorie> findAll() {
		return categorieDAO.findAll();
	}

	@Override
	public Categorie findById(int id) {
		return categorieDAO.findById(id);
	}

	@Override
	public void ajouterCategorie(Categorie categorie) {
		categorieDAO.ajouterCategorie(categorie);
	}

}
