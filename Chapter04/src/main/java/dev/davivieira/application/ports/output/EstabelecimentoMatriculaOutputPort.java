package dev.davivieira.application.ports.output;

import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.vo.EstabelecimentoId;

public interface EstabelecimentoMatriculaOutputPort {
    Estabelecimento fetchEstabelecimentoById(EstabelecimentoId estabelecimentoId);

    boolean persistEstabelecimento(Estabelecimento estabelecimento);
}
