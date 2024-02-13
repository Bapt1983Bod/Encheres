
package fr.eni.ecole.encheres.bll;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.kafka.SslBundleSslEngineFactory;
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
	public void creerUtilisateur(Utilisateur utilisateur, String confirmMdp) throws BusinessException {
		BusinessException e = new BusinessException();
		
		if (utilisateurDAO.findByPseudo(utilisateur.getPseudo()).isPresent()) {
			e.add("Ce pseudo est dèjà utilisé");
		}
		
		if (!utilisateur.getMotDePasse().equals(confirmMdp)) {
			e.add("Les mots de passe ne sont pas identiques");
		}
		
		if (e.getMessages().isEmpty()) {
			utilisateurDAO.creerUtilisateur(utilisateur);
		} else {
			throw e;
		}
	}

	@Override
	public Utilisateur findByPseudo(String pseudo) {
		Optional<Utilisateur> utilisateurOptional = utilisateurDAO.findByPseudo(pseudo);
		Utilisateur utilisateur = utilisateurOptional.get();
		return utilisateur;
	}

	@Override
	public Utilisateur getUtilisateurConnecte() {
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

	@Override
	public void modifierUtilisateur(Utilisateur utilisateur) {
		utilisateurDAO.modifierUtilisateur(utilisateur);
	}

	@Override
	public void supprimerUtilisateur(Utilisateur utilisateur) {
		utilisateurDAO.supprimerUtilisateur(utilisateur);

	}

	@Override
	public List<Utilisateur> findAll() {
		return utilisateurDAO.findAll();
	}

	// liste des utilisateurs sans l'utilisateur connecté (pour admin)
	@Override
	public List<Utilisateur> findUtilisateurs(Utilisateur utilisateurConnecte) {
		return utilisateurDAO.findUtilisateurs(utilisateurConnecte);
	}

	@Override
	public void statutUtilisateur(int noUtilisateur, int statut) {
		utilisateurDAO.statutUtilisateur(noUtilisateur, statut);
	}

	@Override
	public Utilisateur findById(int noUtilisateur) {
		return utilisateurDAO.findById(noUtilisateur).get();
	}

	
	// Méthode de validation password
	@Override
	public Boolean validePassword(String password) throws BusinessException {
		BusinessException e = new BusinessException();
		
		// Vérifie si le mot de passe a au moins 8 caractères
		// Vérifie s'il contient au moins une lettre majuscule,une minuscule, un chiffre.
		String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        // Création du pattern avec l'expression régulière
        Pattern pattern = Pattern.compile(regex);
        
        // Création du matcher pour vérifier le mot de passe
        Matcher matcher = pattern.matcher(password);

        if (matcher.matches()) {
        	return true;
        } else {
        	e.add("le mot de passe est invalide");
        	e.add("le mot de passe doit avoir au moins 8 caractères, une majuscule, une minuscule, un chiffre");
        	throw e;
        }
        
    }
	

	

}
