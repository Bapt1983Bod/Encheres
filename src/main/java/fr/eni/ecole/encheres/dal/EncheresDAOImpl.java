package fr.eni.ecole.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
	private static final String DELETE_ENCHERE = "DELETE FROM ENCHERES WHERE no_article = :noArticle AND no_utilisateur = :noUtilisateur";
	private static final String FIND_ENCHERE_BY_NOARTICLE_AND_NOUTILISTEUR = "SELECT * FROM ENCHERES WHERE no_article = :noArticle AND no_utilisateur = :noUtilisateur";
	private static final String FIND_ENCHERE_BY_NOARTICLE = "SELECT * FROM ENCHERES WHERE no_article = :noArticle";
	private static final String FIND_ENCHERE_BY_NOUTILISATEUR = "SELECT * FROM ENCHERES WHERE no_utilisateur = :noUtilisateur";
	private static final String FIND_ALL ="SELECT * FROM ENCHERES";
	private static final String DELETE_ENCHERE_BY_NOARTICLE = "DELETE FROM ENCHERES WHERE no_article = :noArticle";
	private static final String DELETE_ENCHERE_BY_NOUTILISATEUR = "DELETE FROM ENCHERES WHERE no_utilisateur = :noUtilisateur";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	// DataSource en parametre pour initialiser NamedParameterJdbcTemplate
	public EncheresDAOImpl(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}


	@Override
	public List<Enchere> findAll() {
		return this.jdbcTemplate.query(FIND_ALL, new EnchereRowMapper());
	}

	@Override
	public void createEnchere(Utilisateur utilisateur, int noArticle, long montantEnchere) {
		// Insertion de l'enchère
				MapSqlParameterSource parameters = new MapSqlParameterSource()
						.addValue("noUtilisateur", utilisateur.getNoUtilisateur()).addValue("noArticle", noArticle)
						.addValue("montantEnchere", montantEnchere);

				this.jdbcTemplate.update(INSERT_ENCHERE, parameters);
	}

	@Override
	public void deleteByArticle(int noArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noArticle", noArticle);
		
		// Suppression de l'enchère
		this.jdbcTemplate.update(DELETE_ENCHERE_BY_NOARTICLE, map);
		
	}

	@Override
	public void deleteByUtilisateur(int noUtilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noUtilisateur", noUtilisateur);
		
		// Suppression de l'enchère
		this.jdbcTemplate.update(DELETE_ENCHERE_BY_NOUTILISATEUR, map);
	}

	@Override
	public void deleteByUtilisateurArticle(int noArticle, Utilisateur utilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noArticle", noArticle);
		map.addValue("noUtilisateur", utilisateur.getNoUtilisateur());
		
		// Suppression de l'enchère
		this.jdbcTemplate.update(DELETE_ENCHERE, map);
	}

	@Override
	public List<Enchere> findByUtilisateur(int noUtilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noUtilisateur", noUtilisateur);

		List<Enchere> encheres = this.jdbcTemplate.query(FIND_ENCHERE_BY_NOUTILISATEUR, map, new EnchereRowMapper2());
		return encheres;
	}

	@Override
	public List<Enchere> findByArticle(int noArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("noArticle", noArticle);

		List<Enchere> encheres = this.jdbcTemplate.query(FIND_ENCHERE_BY_NOARTICLE, map, new EnchereRowMapper2());
		return encheres;
	}

	@Override
	public Enchere findEnchereUtilisateur(int noArticle, Utilisateur utilisateur) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noArticle", noArticle);
		params.addValue("noUtilisateur", utilisateur.getNoUtilisateur());
		
		try {
	        Enchere enchere = jdbcTemplate.queryForObject(FIND_ENCHERE_BY_NOARTICLE_AND_NOUTILISTEUR, params, new EnchereRowMapper());
	        return enchere;
	    } catch (EmptyResultDataAccessException e) {
	        // Aucune enchère trouvée, retourner null 
	        return null;
	    }
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
