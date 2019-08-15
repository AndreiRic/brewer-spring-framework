package com.algaworks.brewer.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
		this.page       = page;
		String httpUrl  = httpServletRequest.getRequestURL().append(
				httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "")
				.toString().replaceAll("\\+", "%20").replaceAll("excluido", "");
		this.uriBuilder = ServletUriComponentsBuilder.fromHttpUrl(httpUrl);
	}
	
	public List<T> getConteudo() {
		return this.page.getContent();
	}
	
	public boolean isVazia() {
		return this.page.getContent().isEmpty();
	}
	
	public int getAtual() {
		return this.page.getNumber();
	}
	
	public boolean isPrimeira() {
		return this.page.isFirst();
	}
	
	public boolean isUltima() {
		return this.page.isLast();
	}
	
	public int getTotal() {
		return this.page.getTotalPages();
	}
	
	public String urlParaPagina(int pagina) {
		return this.uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}
	
	public String urlOrdenada(String propriedade) {
		UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder
				.fromUriString(this.uriBuilder.build(true).encode().toString());
		
		String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade));
		
		return uriBuilderOrder.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
	}
	
	public String inverterDirecao(String propriedade) {
		String direcao = "asc";
		
		Order order    = this.page.getSort() != null ? this.page.getSort().getOrderFor(propriedade) : null;
		if (order != null) {
			direcao    = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}
		
		return direcao;
	}
	
	public boolean descendente(String propriedade) {
		return inverterDirecao(propriedade).equals("asc");
	}
	
	public boolean ordenada(String propriedade) {
		Order order = this.page.getSort() != null ? this.page.getSort().getOrderFor(propriedade) : null;
		
		if (order == null) {
			return false;
		} 
		
		return this.page.getSort().getOrderFor(propriedade) != null ? true : false;
	}
}
