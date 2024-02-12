package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.bo.Enchere;
import fr.eni.ecole.encheres.bo.Utilisateur;

@Repository
public class EncheresDAOImpl implements EncheresDAO {

	private static final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (:noUtilisateur, :noArticle, GETDATE(), :montantEnchere)";
	private static final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = :credit - :montantEnchere WHERE no_utilisateur = :noUtilisateur";
	private static final String DELETE_ENCHERE = "DELETE FROM ENCHERES WHERE no_article = :noArticle AND no_utilisateur = :noUtilisateur";
	private static final String FIND_ENCHERE_BY_NOARTICLE_AND_NOUTILISTEUR = "SELECT * FROM ENCHERES WHERE no_article = :noArticle AND no_utilisateur = :noUtilisateur";
	private static final String RESTORE_CREDIT = "UPDATE UTILISATEURS SET credit = credit + :montantEnchere WHERE no_utilisateur = :noUtilisateur";
	private static final String FIND_ENCHERE_BY_NOARTICLE = "SELECT * FROM ENCHERES WHERE no_article = :noArticle";
	private static final String FIND_ENCHERE_BY_NOUTILISATEUR = "SELECT * FROM ENCHERES WHERE no_utilisateur = :noUtilisateur";
	private static final String GET_HIGHEST_ENCHERE = "SELECT TOP 1 * FROM ENCHERES WHERE no_article = :noArticle ORDER BY montant_enchere DESC";

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
				.queryForObject(FIND_ENCHERE_BY_NOARTICLE_AND_NOUTILISTEUR, map, new EnchereRowMapper())
				.getMontantEnchere();

		// Re-crédit du compte de l'utilisateur
		MapSqlParameterSource creditParameters = new MapSqlParameterSource().addValue("montantEnchere", montantEnchere)
				.addValue("credit", utilisateur.getCredit()).addValue("noUtilisateur", utilisateur.getNoUtilisateur());
		this.jdbcTemplate.update(RESTORE_CREDIT, creditParameters);

		// Suppression de l'enchère
		this.jdbcTemplate.update(DELETE_ENCHERE, map);

	}

	public void deleteByIdArticle(int noArticle) {

		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noArticle", noArticle);

		// Vérification si l'utilisateur a enchéri sur cet article
		List<Enchere> encheres = this.jdbcTemplate.query(FIND_ENCHERE_BY_NOARTICLE, map, new EnchereRowMapper());

		if (!encheres.isEmpty()) {
			for (Enchere enchere : encheres) {
				System.out.println(enchere);
				// Remboursement du montant de l'enchère à l'utilisateur
				int montantEnchere = enchere.getMontantEnchere();
				Utilisateur utilisateur = enchere.getUtilisateur();

				MapSqlParameterSource creditParameters = new MapSqlParameterSource()
						.addValue("montantEnchere", montantEnchere)
						.addValue("noUtilisateur", utilisateur.getNoUtilisateur());
				this.jdbcTemplate.update(RESTORE_CREDIT, creditParameters);

			} // Suppression de l'enchère
			this.jdbcTemplate.update(DELETE_ENCHERE, map);

		}
	}

	@Override
	public void deleteByNoUtilisateur(int noUtilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noUtilisateur", noUtilisateur);

		List<Enchere> encheres = this.jdbcTemplate.query(FIND_ENCHERE_BY_NOUTILISATEUR, map, new EnchereRowMapper2());

		if (!encheres.isEmpty()) {
			for (Enchere enchere : encheres) {
				System.out.println(enchere);
				// Remboursement du montant de l'enchère à l'utilisateur
				int montantEnchere = enchere.getMontantEnchere();
				Utilisateur utilisateur = enchere.getUtilisateur();

				MapSqlParameterSource creditParameters = new MapSqlParameterSource()
						.addValue("montantEnchere", montantEnchere)
						.addValue("noUtilisateur", utilisateur.getNoUtilisateur());
				this.jdbcTemplate.update(RESTORE_CREDIT, creditParameters);

			} // Suppression de l'enchère
			this.jdbcTemplate.update(DELETE_ENCHERE, map);
		}

	}

	@Override
	public Enchere getHighestEnchere(int noArticle) {
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("noArticle", noArticle);

		try {
			return jdbcTemplate.queryForObject(GET_HIGHEST_ENCHERE, params, new EnchereRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	class EnchereRowMapper implements RowMapper<Enchere> {

		@Override
		public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"));
			ArticleVendu article = new ArticleVendu(rs.getInt("no_article"));
			Enchere enchere = new Enchere(rs.getDate("date_enchere").toLocalDate().atStartOfDay(),
					rs.getInt("montant_enchere"));

			enchere.setUtilisateur(utilisateur);
			enchere.setArticleVendu(article);

			return enchere;

		}

	}

	class EnchereRowMapper2 implements RowMapper<Enchere> {

		@Override
		public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur utilisateur = new Utilisateur(rs.getInt("no_utilisateur"));
			Enchere enchere = new Enchere(rs.getDate("date_enchere").toLocalDate().atStartOfDay(),
					rs.getInt("montant_enchere"));

			enchere.setUtilisateur(utilisateur);

			return enchere;

		}

	}

}
