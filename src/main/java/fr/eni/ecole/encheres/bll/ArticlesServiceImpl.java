package fr.eni.ecole.encheres.bll;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.ecole.encheres.bo.ArticleVendu;
import fr.eni.ecole.encheres.dal.ArticlesDAO;

@Service
public class ArticlesServiceImpl implements ArticlesService {
	
	private ArticlesDAO articlesDAO;
	
	

	public ArticlesServiceImpl(ArticlesDAO articlesDAO) {
		this.articlesDAO = articlesDAO;
	}



	@Override
	public List<ArticleVendu> findAll() {
		LocalDate date = LocalDate.now();
		System.out.println("service : "+ date);
		return articlesDAO.findAll(date);
	}

}
