package dev.davivieira.framework.adapters.output.file.mappers;

import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.entity.Turma;
import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Matricula;
import dev.davivieira.domain.vo.EstabelecimentoId;
import dev.davivieira.domain.vo.DependenciaAdministrativa;
import dev.davivieira.domain.vo.TurmaId;
import dev.davivieira.domain.vo.Turno;
import dev.davivieira.framework.adapters.output.file.json.EstabelecimentoJson;
import dev.davivieira.framework.adapters.output.file.json.IPJson;
import dev.davivieira.framework.adapters.output.file.json.NetworkJson;
import dev.davivieira.framework.adapters.output.file.json.DependenciaAdministrativaJson;
import dev.davivieira.framework.adapters.output.file.json.TurmaJson;
import dev.davivieira.framework.adapters.output.file.json.TurnoJson;

import java.util.List;
import java.util.stream.Collectors;

public class EstabelecimentoJsonFileMapper {

    public static Estabelecimento toDomain(EstabelecimentoJson routerJson){
        var routerId = EstabelecimentoId.withId(routerJson.getRouterId().toString());
        var routerType = DependenciaAdministrativa.valueOf(routerJson.getRouterType().name());
        var switchId = TurmaId.withId(routerJson.getNetworkSwitch().getSwitchId().toString());
        var switchType = Turno.valueOf(routerJson.getNetworkSwitch().getSwitchType().toString());
        var ip = IP.fromAddress(routerJson.getNetworkSwitch().getIp().getAddress());
        var networks =  getNetworksFromJson(routerJson.getNetworkSwitch().getNetworks());

        var networkSwitch = new Turma(switchId, switchType, networks, ip);

        return new Estabelecimento(routerType, routerId, networkSwitch);
    }

    public static EstabelecimentoJson toJson(Estabelecimento estabelecimento){
        var routerId = estabelecimento.getRouterId().getUUID();
        var routerTypeJson = DependenciaAdministrativaJson.valueOf(estabelecimento.getRouterType().toString());
        var switchIdJson = estabelecimento.getNetworkSwitch().getSwitchId().getUUID();
        var switchTypeJson = TurnoJson.valueOf(estabelecimento.getNetworkSwitch().getSwitchType().toString());
        var ipJson = IPJson.fromAddress(estabelecimento.getNetworkSwitch().getAddress().getIPAddress());
        var networkDataList = getNetworksFromDomain(estabelecimento.retrieveNetworks());

        var switchJson = new TurmaJson(switchIdJson, ipJson, switchTypeJson, networkDataList);

        return new EstabelecimentoJson(routerId, routerTypeJson, switchJson);
    }

    private static List<Matricula> getNetworksFromJson(List<NetworkJson> networkJson){
        return networkJson
                .stream()
                .map(json ->  new Matricula(
                        IP.fromAddress(json.getIp().getAddress()),
                        json.getNetworkName(),
                        Integer.valueOf(json.getCidr())))
                .collect(Collectors.toList());
    }

    private static List<NetworkJson>  getNetworksFromDomain(List<Matricula> matriculas){
        return matriculas
                .stream()
                .map(network -> new NetworkJson(
                        IPJson.fromAddress(network.getAddress().getIPAddress()),
                        network.getName(),
                        String.valueOf(network.getCidr())))
                .collect(Collectors.toList());
    }

}
