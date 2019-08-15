package com.algaworks.brewer.repository.helper.cliente;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;

public class ClientesImpl implements ClientesQueries {
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	@Transactional(readOnly =  true)
	public Page<Cliente> filtrar(ClienteFilter filter, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filter, criteria);
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);
		
		return new PageImpl<>(criteria.list(), pageable, total(filter));
	}

	@SuppressWarnings("deprecation")
	@Transactional(readOnly = true)
	@Override
	public Cliente buscarComCidade(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		criteria.createAlias("endereco.cidade", "c", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("c.estado", "e", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Cliente) criteria.uniqueResult();
	}
	
	private void adicionarFiltro(ClienteFilter filter, Criteria criteria) {
		if (filter != null) {
			if (!StringUtils.isEmpty(filter.getNome())) {
				criteria.add(Restrictions.ilike("nome", filter.getNome(), MatchMode.ANYWHERE));
			}
			if (!StringUtils.isEmpty(filter.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("cpf_cnpj", filter.getCpfOuCnpj()));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private Long total(ClienteFilter filter) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		adicionarFiltro(filter, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
}
