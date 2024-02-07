
package fr.eni.ecole.encheres.bll;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
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

	@Override
	public Utilisateur findByPseudo(String pseudo) {
		Optional<Utilisateur> utilisateurOptional = utilisateurDAO.findByPseudo(pseudo);
		Utilisateur utilisateur = utilisateurOptional.get();
		return utilisateur;
	}

	
	
	@Override
	public Utilisateur getIdUtilisateurConnecte() {
		System.out.println("méthode getIdUtilisateur");
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("user: " + username);
        Utilisateur utilisateur = utilisateurDAO.findByPseudo(username).get();
        if (utilisateur != null) {
        	System.out.println("utilisateur" + utilisateur);
            return utilisateur;
        }
        return null;
	}

	


}
