
package fr.eni.ecole.encheres.bll;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.UtilisateurDAOImpl;
import fr.eni.ecole.encheres.exception.BusinessException;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

	private UtilisateurDAOImpl utilisateurDAO;

	public UtilisateurServiceImpl(UtilisateurDAOImpl utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;
	}

	@Override
	public Utilisateur creerUtilisateur(Utilisateur utilisateur) throws BusinessException {
		utilisateurDAO.creerUtilisateur(utilisateur);
		return utilisateur;
	}
}
