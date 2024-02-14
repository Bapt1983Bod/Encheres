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
//	private static final String UPDATE_CREDIT = "UPDATE UTILISATEURS SET credit = credit - :montantEnchere WHERE no_utilisateur = :noUtilisateur";
	private static final String DELETE_ENCHERE = "DELETE FROM ENCHERES WHERE no_article = :noArticle AND no_utilisateur = :noUtilisateur";
	private static final String FIND_ENCHERE_BY_NOARTICLE_AND_NOUTILISTEUR = "SELECT * FROM ENCHERES WHERE no_article = :noArticle AND no_utilisateur = :noUtilisateur";
//	private static final String RESTORE_CREDIT = "UPDATE UTILISATEURS SET credit = credit + :montantEnchere WHERE no_utilisateur = :noUtilisateur";
	private static final String FIND_ENCHERE_BY_NOARTICLE = "SELECT * FROM ENCHERES WHERE no_article = :noArticle";
	private static final String FIND_ENCHERE_BY_NOUTILISATEUR = "SELECT * FROM ENCHERES WHERE no_utilisateur = :noUtilisateur";
	private static final String FIND_ALL ="SELECT * FROM ENCHERES";
//	private static final String GET_HIGHEST_ENCHERE = "SELECT TOP 1 * FROM ENCHERES WHERE no_article = :noArticle ORDER BY montant_enchere DESC";
	private static final String DELETE_ENCHERE_BY_NOARTICLE = "DELETE FROM ENCHERES WHERE no_article = :noArticle";
	private static final String DELETE_ENCHERE_BY_NOUTILISATEUR = "DELETE FROM ENCHERES WHERE no_utilisateur = :noUtilisateur";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	// DataSource en parametre pour initialiser NamedParameterJdbcTemplate
	public EncheresDAOImpl(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

//	
//	// Suppression d'une enchère en fonction de l'article et de l'utilisateur
//	@Override
//	public void deleteByIdUtilisateurAndIdArticle(int noArticle, Utilisateur utilisateur) {
////		MapSqlParameterSource map = new MapSqlParameterSource();
////		map.addValue("noArticle", noArticle);
////		map.addValue("noUtilisateur", utilisateur.getNoUtilisateur());
////
////		// Récupération du montant de l'enchère
////		int montantEnchere = this.jdbcTemplate
////				.queryForObject(FIND_ENCHERE_BY_NOARTICLE_AND_NOUTILISTEUR, map, new EnchereRowMapper())
////				.getMontantEnchere();
////
////		// Re-crédit du compte de l'utilisateur
////		MapSqlParameterSource creditParameters = new MapSqlParameterSource().addValue("montantEnchere", montantEnchere)
////				.addValue("credit", utilisateur.getCredit()).addValue("noUtilisateur", utilisateur.getNoUtilisateur());
////		this.jdbcTemplate.update(RESTORE_CREDIT, creditParameters);
////
////		// Suppression de l'enchère
////		this.jdbcTemplate.update(DELETE_ENCHERE, map);
//
//	}
//
////	// suppression d'une enchere par no article
//	public void deleteByIdArticle(int noArticle) {
/////
////		MapSqlParameterSource map = new MapSqlParameterSource();
////		map.addValue("noArticle", noArticle);
////
////		// Vérification si l'utilisateur a enchéri sur cet article
////		List<Enchere> encheres = this.jdbcTemplate.query(FIND_ENCHERE_BY_NOARTICLE, map, new EnchereRowMapper());
////
////		if (!encheres.isEmpty()) {
////			for (Enchere enchere : encheres) {
////				
////				// Remboursement du montant de l'enchère à l'utilisateur
////				int montantEnchere = enchere.getMontantEnchere();
////				Utilisateur utilisateur = enchere.getUtilisateur();
////
////				MapSqlParameterSource creditParameters = new MapSqlParameterSource()
////						.addValue("montantEnchere", montantEnchere)
////						.addValue("noUtilisateur", utilisateur.getNoUtilisateur());
////				this.jdbcTemplate.update(RESTORE_CREDIT, creditParameters);
////
////			} // Suppression de l'enchère
////			this.jdbcTemplate.update(DELETE_ENCHERE_BY_NOARTICLE, map);
////
////		}
//	}
//
//	// suppression d'une enchere par NoUtilisateur
//	@Override
//	public void deleteByNoUtilisateur(int noUtilisateur) {
////		MapSqlParameterSource map = new MapSqlParameterSource();
////		map.addValue("noUtilisateur", noUtilisateur);
////
////		List<Enchere> encheres = this.jdbcTemplate.query(FIND_ENCHERE_BY_NOUTILISATEUR, map, new EnchereRowMapper2());
////
////		if (!encheres.isEmpty()) {
////			for (Enchere enchere : encheres) {
////				// Remboursement du montant de l'enchère à l'utilisateur
////				int montantEnchere = enchere.getMontantEnchere();
////				Utilisateur utilisateur = enchere.getUtilisateur();
////
////				MapSqlParameterSource creditParameters = new MapSqlParameterSource()
////						.addValue("montantEnchere", montantEnchere)
////						.addValue("noUtilisateur", utilisateur.getNoUtilisateur());
////				this.jdbcTemplate.update(RESTORE_CREDIT, creditParameters);
////
////			} // Suppression de l'enchère
////			this.jdbcTemplate.update(DELETE_ENCHERE_BY_NOUTILISATEUR, map);
////		}
//
//	}

//	// récupérer l'enchere la plus elevée
//	@Override
//	public Enchere getHighestEnchere(int noArticle) {
//		MapSqlParameterSource params = new MapSqlParameterSource().addValue("noArticle", noArticle);
//
//		try {
//			return jdbcTemplate.queryForObject(GET_HIGHEST_ENCHERE, params, new EnchereRowMapper());
//		} catch (EmptyResultDataAccessException e) {
//			return null;
//		}
//
//	}
//
//	// savoir si l'utilisateur à encheri ou non sur l'article
//	@Override
//	public boolean aEncheriSurArticle(int noArticle, Utilisateur utilisateur) {
////		MapSqlParameterSource params = new MapSqlParameterSource().addValue("noArticle", noArticle)
////				.addValue("noUtilisateur", utilisateur.getNoUtilisateur());
////
////		try {
////			jdbcTemplate.queryForObject(FIND_ENCHERE_BY_NOARTICLE_AND_NOUTILISTEUR, params, new EnchereRowMapper());
////			return true; // Si une enchère est trouvée pour cet article et cet utilisateur, alors il a
////							// enchéri
////		} catch (EmptyResultDataAccessException e) {
////			return false; // Sinon, il n'a pas enchéri
////		}
//	}
//
//	// permettre la surenchere
//	@Override
//	public void surenchere(int noArticle, long montantEnchere, Utilisateur acheteur) {
////		// Vérifiez d'abord si l'utilisateur a déjà enchéri sur cet article
////		if (aEncheriSurArticle(noArticle, acheteur)) {
////			// Si oui, supprimez son enchère précédente et remboursez-le
////			deleteByIdUtilisateurAndIdArticle(noArticle, acheteur);
////		}
////
////		// Enchérir avec le nouveau montant
////		createEnchere(acheteur, noArticle, montantEnchere );
//	}

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

		List<Enchere> encheres = this.jdbcTemplate.query(FIND_ENCHERE_BY_NOARTICLE, map, new EnchereRowMapper());
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
