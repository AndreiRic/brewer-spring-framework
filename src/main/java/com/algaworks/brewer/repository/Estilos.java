package com.algaworks.brewer.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;

@Repository
public interface Estilos extends JpaRepository<Estilo, Long> {
	
	public Optional<Estilo> findByNomeIgnoreCase(String nome);

	public Page<Estilo> filtrar(EstiloFilter estiloFilter, Pageable pageable);

}
