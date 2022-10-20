package br.com.tawandev.msclientes.domain.dto;

import br.com.tawandev.msclientes.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequestDTO {

    private String cpf;
    private String nome;
    private Integer idade;

    public Cliente toModel() {
        return new Cliente(cpf, nome, idade);
    }
}
