package br.com.tawandev.mscartoes.resource;

import br.com.tawandev.mscartoes.domain.dto.CartaoSaveRequestDTO;
import br.com.tawandev.mscartoes.domain.dto.CartoesPorClienteResponseDTO;
import br.com.tawandev.mscartoes.domain.entity.Cartao;
import br.com.tawandev.mscartoes.domain.entity.ClienteCartao;
import br.com.tawandev.mscartoes.services.CartaoService;
import br.com.tawandev.mscartoes.services.ClienteCartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
public class CartoesResource {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ClienteCartaoService clienteCartaoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody CartaoSaveRequestDTO request) {
        Cartao cartao = cartaoService.save(request.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartao);
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
        List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponseDTO>> getCartoesByCliente(@RequestParam("cpf") String cpf) {
        List<ClienteCartao> list = clienteCartaoService.listCartoesByCpf(cpf);
        List<CartoesPorClienteResponseDTO> resultList = list.stream()
                .map(CartoesPorClienteResponseDTO::fromModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultList);
    }
}
