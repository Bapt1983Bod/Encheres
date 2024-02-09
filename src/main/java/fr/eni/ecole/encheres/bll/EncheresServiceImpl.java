package fr.eni.ecole.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.EncheresDAO;

@Service
public class EncheresServiceImpl implements EncheresService {

	private EncheresDAO encheresDAO;

	public EncheresServiceImpl(EncheresDAO encheresDAO) {
		this.encheresDAO = encheresDAO;
	}

	@Override
	public void encherir(int noArticle, long montantEnchere, Utilisateur utilisateur) {
		encheresDAO.encherir(noArticle, montantEnchere, utilisateur);
	}

	@Override
	public void encherir(int noArticle, long montantEnchere) {

	}
}