package br.com.tawandev.mscartoes.infra.mqueue;

import br.com.tawandev.mscartoes.domain.dto.DadosSolicitacaoEmissaoCartaoDTO;
import br.com.tawandev.mscartoes.domain.entity.Cartao;
import br.com.tawandev.mscartoes.domain.entity.ClienteCartao;
import br.com.tawandev.mscartoes.infra.repository.CartaoRepository;
import br.com.tawandev.mscartoes.infra.repository.ClienteCartaoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues =  "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            DadosSolicitacaoEmissaoCartaoDTO dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartaoDTO.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteCartaoRepository.save(clienteCartao);
        } catch (Exception e) {
            log.error("Erro ao receber solicitação de emissao de cartão: {} ", e.getMessage());
        }
    }
}
