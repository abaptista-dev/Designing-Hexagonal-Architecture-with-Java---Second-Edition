package dev.davivieira.application.usecases;

import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.vo.DependenciaAdministrativa;

import java.util.List;

public interface RouterViewUseCase {

    List<Estabelecimento> getRelatedRouters(RelatedRoutersCommand relatedRoutersCommand);

    class RelatedRoutersCommand {

        private DependenciaAdministrativa type;

        public RelatedRoutersCommand(String type){
            this.type = DependenciaAdministrativa.valueOf(type);
        }

        public DependenciaAdministrativa getType() {
            return type;
        }
    }
}
