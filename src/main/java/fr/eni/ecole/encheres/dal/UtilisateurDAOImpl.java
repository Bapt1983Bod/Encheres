package fr.eni.ecole.encheres.dal;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Utilisateur;
import fr.eni.ecole.encheres.exception.BusinessException;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse, :credit, :administrateur)";
	private static final String SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String SELECT_BY_EMAIL = "SELECT * FROM UTILISATEURS WHERE email = :email";

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public UtilisateurDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public void creerUtilisateur(Utilisateur utilisateur) throws BusinessException {

		// verifier si pseudo déja existant dans la base
		Optional<Utilisateur> existingUser = findByPseudo(utilisateur.getPseudo());
		if (existingUser.isPresent()) {

			throw new BusinessException("Le pseudo est déjà utilisé");
		}

		Optional<Utilisateur> existingUserMail = findByEmail(utilisateur.getEmail());
		if (existingUserMail.isPresent()) {
			System.out.println("email verif");
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
		map.addValue("motDePasse", utilisateur.getMotDePasse());
		map.addValue("credit", utilisateur.getCredit());
		map.addValue("administrateur", utilisateur.getAdministrateur());

		System.out.println(utilisateur);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.namedParameterJdbcTemplate.update(INSERT, map, keyHolder);

		if (keyHolder != null && keyHolder.getKey() != null) {

			utilisateur.setNoUtilisateur(keyHolder.getKey().intValue());
		}

	}

	@Override //
	public Optional<Utilisateur> findByPseudo(String pseudo) {
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("pseudo", pseudo);

		try {
			Utilisateur utilisateur = namedParameterJdbcTemplate.queryForObject(SELECT_BY_PSEUDO, params,
					(rs, rowNum) -> {
						Utilisateur user = new Utilisateur();
						user.setPseudo(rs.getString("pseudo"));
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
	public Utilisateur save(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		return null;
	}
}
