
package fr.eni.ecole.encheres.bll;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	public Utilisateur getUtilisateurConnecte() {
		// Récupérer l'objet principal de l'utilisateur connecté à partir du contexte de
		// sécurité
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Vérifier si l'utilisateur est authentifié
		if (authentication != null && authentication.isAuthenticated()) {
			// Extraire les informations de l'utilisateur à partir de l'objet principal
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();

			// Récupérer l'identifiant de l'utilisateur connecté
			String username = userDetails.getUsername();

			// Utiliser l'identifiant pour récupérer les informations de l'utilisateur
			// depuis la base de données
			return utilisateurDAO.findByPseudo(username).orElse(null);
		}
		return null;

	}

}
