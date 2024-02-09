package fr.eni.ecole.encheres.dal;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class EncheresDAOImpl implements EncheresDAO {

	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (:noUtilisateur, :noArticle, GETDATE(), :montantEnchere)";
	private static final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = :credit - :montantEnchere WHERE no_utilisateur = :noUtilisateur";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	// DataSource en parametre pour initialiser NamedParameterJdbcTemplate
	public EncheresDAOImpl(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	// prevoir try catch dans le cas ou une enchere est déja en cours

	@Override
	public void encherir(int noArticle, long montantEnchere, Utilisateur utilisateur) {
		if (utilisateur.getCredit() < montantEnchere) {
			throw new IllegalArgumentException(
					"Le crédit de l'utilisateur est insuffisant pour enchérir avec ce montant.");
		}

		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("noUtilisateur", utilisateur.getNoUtilisateur()).addValue("noArticle", noArticle)
				.addValue("montantEnchere", montantEnchere);

		jdbcTemplate.update(INSERT_ENCHERE, parameters);

		MapSqlParameterSource creditParameters = new MapSqlParameterSource().addValue("montantEnchere", montantEnchere)
				.addValue("credit", utilisateur.getCredit()).addValue("noUtilisateur", utilisateur.getNoUtilisateur());

		jdbcTemplate.update(UPDATE_CREDIT, creditParameters);
	}
}