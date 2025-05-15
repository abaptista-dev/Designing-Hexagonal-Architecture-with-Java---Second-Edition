package dev.davivieira.domain.service;

import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.vo.DependenciaAdministrativa;

import java.util.ArrayList;
import java.util.List;

public class RouterSearch {

    public static List<Estabelecimento> getRouters(DependenciaAdministrativa type, List<Estabelecimento> estabelecimentos) {
        var routersList = new ArrayList<Estabelecimento>();
        estabelecimentos.forEach(router -> {
            if(router.isType(type)){
                routersList.add(router);
            }
        });
        return routersList;
    }
}
