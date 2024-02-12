package fr.eni.ecole.encheres.dal;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class EncheresDAOImpl implements EncheresDAO {

	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (:noUtilisateur, :noArticle, GETDATE(), :montantEnchere)";
	private static final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = :credit - :montantEnchere WHERE no_utilisateur = :noUtilisateur";
	private static final String DELETE_ENCHERE = "DELETE FROM ENCHERES WHERE no_article = :noArticle AND no_utilisateur = :noUtilisateur";
	private static final String FIND_ENCHERE_BY_NOARTICLE_AND_NOUTILISTEUR = "SELECT * FROM ENCHERES WHERE no_article = :noArticle AND no_utilisateur = :noUtilisateur";
	private static final String RESTORE_CREDIT = "UPDATE UTILISATEURS SET credit = :credit + :montantEnchere WHERE no_utilisateur = :noUtilisateur";

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

		// Insertion de l'enchère
		MapSqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("noUtilisateur", utilisateur.getNoUtilisateur()).addValue("noArticle", noArticle)
				.addValue("montantEnchere", montantEnchere);

		this.jdbcTemplate.update(INSERT_ENCHERE, parameters);

		// Mise à jour du crédit utilisateur (déduction du montant de l'enchère)
		MapSqlParameterSource creditParameters = new MapSqlParameterSource().addValue("montantEnchere", montantEnchere)
				.addValue("credit", utilisateur.getCredit()).addValue("noUtilisateur", utilisateur.getNoUtilisateur());

		this.jdbcTemplate.update(UPDATE_CREDIT, creditParameters);
	}

	// Suppression d'une enchère en fonction de l'article et de l'utilisateur
	@Override
	public void deleteByIdUtilisateurAndIdArticle(int noArticle, Utilisateur utilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noArticle", noArticle);
		map.addValue("noUtilisateur", utilisateur.getNoUtilisateur());

		// Récupération du montant de l'enchère
		int montantEnchere = this.jdbcTemplate
				.queryForObject(FIND_ENCHERE_BY_NOARTICLE_AND_NOUTILISTEUR, map, Enchere.class).getMontantEnchere();

		// Re-crédit du compte de l'utilisateur
		MapSqlParameterSource creditParameters = new MapSqlParameterSource().addValue("montantEnchere", montantEnchere)
				.addValue("credit", utilisateur.getCredit()).addValue("noUtilisateur", utilisateur.getNoUtilisateur());
		this.jdbcTemplate.update(RESTORE_CREDIT, creditParameters);

		// Suppression de l'enchère
		this.jdbcTemplate.update(DELETE_ENCHERE, map);

	}

}