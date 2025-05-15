package dev.davivieira.application.usecases;

import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.vo.Matricula;
import dev.davivieira.domain.vo.EstabelecimentoId;

public interface EstabelecimentoMatriculaUseCase {

    Estabelecimento addMatriculaNoEstabelecimento(EstabelecimentoId estabelecimentoId, Matricula matricula);
}
