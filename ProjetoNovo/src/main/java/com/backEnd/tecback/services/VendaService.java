package com.backEnd.tecback.services;

import com.backEnd.tecback.dto.ProdutoDTO;
import com.backEnd.tecback.dto.VendaDTO;
import com.backEnd.tecback.models.Venda;
import com.backEnd.tecback.repositories.ProdutoDetalheRepository;
import com.backEnd.tecback.repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository repository;

    @Autowired
    private ProdutoDetalheRepository detalheRepository;

    public Optional<Venda> findById(Venda venda, Long id) {
        return repository.findById(id);
    }

    public List<Venda> listaTodas() {
        return repository.findAll();
    }

    public Venda create(Venda venda) {
        return repository.save(venda);
    }

    public Venda update(Venda venda) {
        return repository.save(venda);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Venda> listPorDocumento(String documento) {
        return repository.findByDocumentoContainsIgnoreCase(documento);
    }

    public List<VendaDTO> listVendasSemDoc(){
        List<Venda> vendas = repository.findAll();

        if(!vendas.isEmpty()){

            List<VendaDTO> vendaDTOS = new ArrayList<>();
            VendaDTO vendaDTO = new VendaDTO();
            vendas.stream().forEach(v -> {
                vendaDTO.setNomeComprador(v.getNomeComprador());

                v.getProdutos().stream().forEach(pv -> {

                    ProdutoDTO produtoDTOS = new ProdutoDTO();

                    produtoDTOS.setValorCompra(pv.getProdutoDetalhe().getValorCompra());
                    produtoDTOS.setValorVenda(pv.getProdutoDetalhe().getValorVenda());
                    produtoDTOS.setNome(pv.getNome());

                    vendaDTO.setProdutoDTO(produtoDTOS);

                });

            } );

            vendaDTOS.add(vendaDTO);

            return vendaDTOS;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existem vendas cadastradas.");
    }

}