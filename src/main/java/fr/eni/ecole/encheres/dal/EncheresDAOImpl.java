package fr.eni.ecole.encheres.dal;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EncheresDAOImpl implements EncheresDAO {

	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (:noUtilisateur, :noArticle, GETDATE(), :montantEnchere)";
	private static final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = credit - :montantEnchere WHERE no_utilisateur = :noUtilisateur";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	// DataSource en parametre pour initialiser NamedParameterJdbcTemplate
	public EncheresDAOImpl(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override

	public void encherir(int noArticle, long montantEnchere, long creditUtilisateur) {

		// map avec les valeurs des paramètres pour insérer l'enchère
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("creditUtilisateur", creditUtilisateur);
		parameters.put("noArticle", noArticle);
		parameters.put("montantEnchere", montantEnchere);

		// execute la requete SQL d'insertion
		jdbcTemplate.update(INSERT_ENCHERE, parameters);

		// Mettre à jour le crédit de l'utilisateur
		MapSqlParameterSource creditParameters = new MapSqlParameterSource();
		creditParameters.addValue("montantEnchere", montantEnchere);
		creditParameters.addValue("creditUtilisateur", creditUtilisateur);

		jdbcTemplate.update(UPDATE_CREDIT, creditParameters);
	}
}