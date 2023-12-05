package com.backEnd.tecback.repositories;

import com.backEnd.tecback.models.ProdutoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoDetalheRepository extends JpaRepository<ProdutoDetalhe,Long> {
}
