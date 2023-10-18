package com.pontoeletronico.api.repositorys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.pontoeletronico.api.entitys.Lancamento;

import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Transactional(readOnly = true)
@NamedQueries({
	@NamedQuery(name = "LancamentoRepository.findByFuncionarioId", 
			query = "SELECT lanc FROM Lancamento lanc WHERE lanc.funcionario.id =:funcionarioId")
})
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

	
	List<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);
	
    Page<Lancamento> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
