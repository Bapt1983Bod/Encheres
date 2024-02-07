package fr.eni.ecole.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.dal.RetraitDAO;

@Service
public class RetraitServiceImpl implements RetraitService {

	private RetraitDAO retraitDAO;
	
	

	public RetraitServiceImpl(RetraitDAO retraitDAO) {
		this.retraitDAO = retraitDAO;
	}


	// Création d'un retrait
	@Override
	public void createRetrait(int idArticle, String rue, String codePostal, String ville) {
		retraitDAO.createRetrait(idArticle, rue, codePostal, ville);
	}

}
