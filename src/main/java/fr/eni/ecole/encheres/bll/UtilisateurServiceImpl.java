
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

	/*
	 * Création d'un utilisateur
	 */
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

	/*
	 * Recherche utilisateur par son pseudo
	 */
	@Override
	public Utilisateur findByPseudo(String pseudo) {
		Optional<Utilisateur> utilisateurOptional = utilisateurDAO.findByPseudo(pseudo);
		Utilisateur utilisateur = utilisateurOptional.get();
		return utilisateur;
	}

	/*
	 * Methode de récupération des informations du l'utilisateur connecté
	 */
	@Override
	public Utilisateur getUtilisateurConnecte() {
		System.out.println("méthode getIdUtilisateur");
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println("user: " + username);
		
		Optional<Utilisateur> utilisateurOptional = utilisateurDAO.findByPseudo(username);
	    
	    if (utilisateurOptional.isPresent()) {
	        Utilisateur utilisateur = utilisateurOptional.get();
	        return utilisateur;
	    } else {
	        // Gérer le cas où aucun utilisateur n'est trouvé avec le nom d'utilisateur donné
	        return null; // ou lancer une exception appropriée
	    }
	}

	/*
	 * Méthode de modification du profil
	 */
	@Override
	public void modifierUtilisateur(Utilisateur utilisateur) {
		utilisateurDAO.modifierUtilisateur(utilisateur);
	}

	/*
	 * 
	 */
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

	
	/*
	 * Méthode de validation password (vérification regex/password identique au passwordConfirm)
	 */
	@Override
	public Boolean validePassword(String password, String pwdConfirm) throws BusinessException {
		BusinessException e = new BusinessException();
		
		// Vérifie si le mot de passe a au moins 8 caractères
		// Vérifie s'il contient au moins une lettre majuscule,une minuscule, un chiffre.
		String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

        // Création du pattern avec l'expression régulière
        Pattern pattern = Pattern.compile(regex);
        
        // Création du matcher pour vérifier le mot de passe
        Matcher matcher = pattern.matcher(password);

        // Vérification validité password par rapport au regex
        if (!matcher.matches()) {
        	e.add("le mot de passe est invalide");
        	e.add("le mot de passe doit avoir au moins 8 caractères, une majuscule, une minuscule, un chiffre");
        } 
        // Vérification que les deux mdp sont identiques
        if (!(password.equals(pwdConfirm))) {
        	e.add("les mots de passe ne sont pas identiques");
        } 
        
        // Retourne true si aucun message d'erreur
        if (e.getMessages().isEmpty()) {
        	return true;
        } else {
        	throw e;
        }
        
    }
	

	

}
