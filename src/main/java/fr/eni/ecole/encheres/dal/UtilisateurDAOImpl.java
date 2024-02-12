
package fr.eni.ecole.encheres.dal;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse, :credit, :administrateur)";
	private static final String SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = :noUtilisateur";
	private static final String SELECT_BY_EMAIL = "SELECT * FROM UTILISATEURS WHERE email = :email";
	private static final String UPDATE = "UPDATE UTILISATEURS SET nom = :nom, prenom = :prenom, email = :email, telephone = :telephone, rue = :rue, code_postal = :codePostal, ville = :ville, mot_de_passe = :mdp WHERE pseudo = :pseudo";
	private static final String DELETE = "DELETE FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String FIND_ALL = "SELECT * FROM UTILISATEURS";
	// liste des utilisateurs sans l'utilisateur connecté (pour admin)
	private static final String FIND_UTILISATEURS = "SELECT * FROM UTILISATEURS WHERE no_utilisateur != :noUtilisateur";
	// desactivation utilisateur
	private static final String STATUT ="UPDATE UTILISATEURS SET  administrateur = :statut WHERE no_utilisateur = :noUtilisateur";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private PasswordEncoder passwordEncoder;

	public UtilisateurDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, PasswordEncoder passwordEncoder) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.passwordEncoder = passwordEncoder;
	}

	public void creerUtilisateur(Utilisateur utilisateur) throws BusinessException {

		// verifier si le pseudo ne contient que des caractères alphanumériques

		if (!isValidPseudo(utilisateur.getPseudo())) {
			throw new BusinessException("Le pseudo ne doit contenir que des caractères alphanumériques");
		}

		// verifier si pseudo déja existant dans la base
		Optional<Utilisateur> existingUser = findByPseudo(utilisateur.getPseudo());
		if (existingUser.isPresent()) {

			throw new BusinessException("Le pseudo est déjà utilisé");
		}

		Optional<Utilisateur> existingUserMail = findByEmail(utilisateur.getEmail());
		if (existingUserMail.isPresent()) {
			throw new BusinessException("L'adresse mail est déja utilisée");
		}

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", utilisateur.getPseudo());
		map.addValue("nom", utilisateur.getNom());
		map.addValue("prenom", utilisateur.getPrenom());
		map.addValue("email", utilisateur.getEmail());
		map.addValue("telephone", utilisateur.getTelephone());
		map.addValue("rue", utilisateur.getRue());
		map.addValue("codePostal", utilisateur.getCodePostal());
		map.addValue("ville", utilisateur.getVille());
		map.addValue("motDePasse", passwordEncoder.encode(utilisateur.getMotDePasse()));
		map.addValue("credit", utilisateur.getCredit());
		map.addValue("administrateur", utilisateur.getAdministrateur());

		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.namedParameterJdbcTemplate.update(INSERT, map, keyHolder);

		if (keyHolder != null && keyHolder.getKey() != null) {

			utilisateur.setNoUtilisateur(keyHolder.getKey().intValue());
		}

	}

	private boolean isValidPseudo(String pseudo) {
		String regex = "^[a-zA-Z0-9]+$";
		return Pattern.matches(regex, pseudo);
	}

	@Override //
	public Optional<Utilisateur> findByPseudo(String pseudo) {
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("pseudo", pseudo);

		try {
			Utilisateur utilisateur = namedParameterJdbcTemplate.queryForObject(SELECT_BY_PSEUDO, params,
					(rs, rowNum) -> {
						Utilisateur user = new Utilisateur();
						user.setNoUtilisateur(rs.getInt("no_utilisateur"));
						user.setPseudo(rs.getString("pseudo"));
						user.setNom(rs.getString("nom"));
						user.setPrenom(rs.getString("prenom"));
						user.setEmail(rs.getString("email"));
						user.setTelephone(rs.getString("telephone"));
						user.setRue(rs.getString("rue"));
						user.setCodePostal(rs.getString("code_postal"));
						user.setVille(rs.getString("ville"));
						user.setMotDePasse(rs.getString("mot_de_passe"));
						user.setCredit(rs.getInt("credit"));
						user.setAdministrateur(rs.getInt("administrateur"));
						return user;
					});
			
			return Optional.ofNullable(utilisateur);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Utilisateur> findByEmail(String email) {
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("email", email);

		try {
			Utilisateur utilisateur = namedParameterJdbcTemplate.queryForObject(SELECT_BY_EMAIL, params,
					(rs, rowNum) -> {
						Utilisateur user = new Utilisateur();
						user.setEmail(rs.getString("email"));
						return user;
					});
			return Optional.ofNullable(utilisateur);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	@Override
	public void modifierUtilisateur(Utilisateur utilisateur) {
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nom", utilisateur.getNom());
		params.addValue("prenom", utilisateur.getPrenom());
		params.addValue("email", utilisateur.getEmail());
		params.addValue("telephone", utilisateur.getTelephone());
		params.addValue("rue", utilisateur.getRue());
		params.addValue("codePostal", utilisateur.getCodePostal());
		params.addValue("ville", utilisateur.getVille());
		params.addValue("pseudo", utilisateur.getPseudo());
		params.addValue("mdp",passwordEncoder.encode(utilisateur.getMotDePasse()) );

		namedParameterJdbcTemplate.update(UPDATE, params);

	}

	@Override
	public void supprimerUtilisateur(Utilisateur utilisateur) {
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("pseudo", utilisateur.getPseudo());
		namedParameterJdbcTemplate.update(DELETE, params);
	}

	@Override
	public List<Utilisateur> findAll() {
		return this.namedParameterJdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));
	}

	// liste des utilisateurs sans l'utilisateur connecté (pour admin)
	@Override
	public List<Utilisateur> findUtilisateurs(Utilisateur utilisateurConnecte) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noUtilisateur", utilisateurConnecte.getNoUtilisateur());
		
		return this.namedParameterJdbcTemplate.query(FIND_UTILISATEURS,map,  new BeanPropertyRowMapper<>(Utilisateur.class));
	}

	// modification du statut d'un utilisateur (désactivation, activation, passage en admin)
	@Override
	public void statutUtilisateur(int noUtilisateur, int statut) {
		System.out.println("DAO STATUT : "+ statut);
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noUtilisateur", noUtilisateur);
		map.addValue("statut", statut );
		
		this.namedParameterJdbcTemplate.update(STATUT,map);
		
	}

	@Override
	public Optional<Utilisateur> findById(int noUtilisateur) {
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("noUtilisateur", noUtilisateur);

		try {
			Utilisateur utilisateur = namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, params,
					(rs, rowNum) -> {
						Utilisateur user = new Utilisateur();
						user.setNoUtilisateur(rs.getInt("no_utilisateur"));
						user.setPseudo(rs.getString("pseudo"));
						user.setNom(rs.getString("nom"));
						user.setPrenom(rs.getString("prenom"));
						user.setEmail(rs.getString("email"));
						user.setTelephone(rs.getString("telephone"));
						user.setRue(rs.getString("rue"));
						user.setCodePostal(rs.getString("code_postal"));
						user.setVille(rs.getString("ville"));
						user.setMotDePasse(rs.getString("mot_de_passe"));
						user.setCredit(rs.getInt("credit"));
						user.setAdministrateur(rs.getInt("administrateur"));
						return user;
					});
			
			return Optional.ofNullable(utilisateur);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}


}
