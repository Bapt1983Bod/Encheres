package fr.eni.ecole.encheres.bll;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.dal.UtilisateurRepository;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private UtilisateurRepository utilisateurRepository;

	@Override
	public Utilisateur creerUtilisateur(Utilisateur utilisateur) {
		// Valider le pseudo unique et les autres contraintes
		if (utilisateurRepository.findByPseudo(utilisateur.getPseudo()).isPresent()
				|| utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
			throw new DataIntegrityViolationException("Le pseudo ou l'email existe déjà.");
		}

		return utilisateurRepository.save(utilisateur);
	}
}
