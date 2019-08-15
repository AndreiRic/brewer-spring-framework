package com.algaworks.brewer.repository.helper.cidade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class CidadesImpl implements CidadesQueries {

	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	@Transactional(readOnly = true)
	public Page<Cidade> filtrar(CidadeFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filter, criteria);
		criteria.createAlias("estado", "e");
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}
	
	@SuppressWarnings({ "deprecation" })
	@Transactional(readOnly = true)
	@Override
	public Cidade buscarComEstados(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		criteria.createAlias("estado", "e");
		criteria.add(Restrictions.eq("codigo", codigo));
		return (Cidade) criteria.uniqueResult();
	}

	private void adicionarFiltro(CidadeFilter filter, Criteria criteria) {
		if (filter != null) {
			if (isEstadoPresente(filter)) {
				criteria.add(Restrictions.eq("estado", filter.getEstado()));
			}
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private Long total(CidadeFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cidade.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	private boolean isEstadoPresente(CidadeFilter filter) {
		return filter.getEstado() != null && filter.getEstado().getCodigo() != null;
	}
	
}
