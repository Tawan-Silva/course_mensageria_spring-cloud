package br.com.tawandev.mscartoes.domain.dto;

import br.com.tawandev.mscartoes.domain.enums.BandeiraCartao;
import br.com.tawandev.mscartoes.domain.entity.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequestDTO {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel() {
        return new Cartao(nome, bandeira, renda, limite);
    }
}
