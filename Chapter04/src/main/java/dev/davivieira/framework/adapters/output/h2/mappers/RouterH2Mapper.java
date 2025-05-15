package dev.davivieira.framework.adapters.output.h2.mappers;

import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.entity.Turma;
import dev.davivieira.domain.vo.IP;
import dev.davivieira.domain.vo.Matricula;
import dev.davivieira.domain.vo.EstabelecimentoId;
import dev.davivieira.domain.vo.DependenciaAdministrativa;
import dev.davivieira.domain.vo.TurmaId;
import dev.davivieira.domain.vo.Turno;
import dev.davivieira.framework.adapters.output.h2.data.IPData;
import dev.davivieira.framework.adapters.output.h2.data.MatriculaData;
import dev.davivieira.framework.adapters.output.h2.data.EstabelecimentoData;
import dev.davivieira.framework.adapters.output.h2.data.DependenciaAdministrativaData;
import dev.davivieira.framework.adapters.output.h2.data.TurmaData;
import dev.davivieira.framework.adapters.output.h2.data.TurnoData;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RouterH2Mapper {

        public static Estabelecimento toDomain(EstabelecimentoData estabelecimentoData){
            var routerType = DependenciaAdministrativa.valueOf(estabelecimentoData.getRouterType().name());
            var routerId = EstabelecimentoId.withId(estabelecimentoData.getRouterId().toString());
            var switchId = TurmaId.withId(estabelecimentoData.getNetworkSwitch().getSwitchId().toString());
            var switchType = Turno.valueOf(estabelecimentoData.getNetworkSwitch().getSwitchType().toString());
            var ip = IP.fromAddress(estabelecimentoData.getNetworkSwitch().getIp().getAddress());
            var networks =  getNetworksFromData(estabelecimentoData.getNetworkSwitch().getNetworks());

            var networkSwitch = new Turma(switchId, switchType,networks, ip);
            return new Estabelecimento(routerType, routerId, networkSwitch);
        }


        public static EstabelecimentoData toH2(Estabelecimento estabelecimento){
            var routerTypeData = DependenciaAdministrativaData.valueOf(estabelecimento.getRouterType().toString());
            var routerId = estabelecimento.getRouterId().getUUID();
            var switchId = estabelecimento.getNetworkSwitch().getSwitchId().getUUID();
            var switchTypeData = TurnoData.valueOf(estabelecimento.getNetworkSwitch().getSwitchType().toString());
            var ipData = IPData.fromAddress(estabelecimento.getNetworkSwitch().getAddress().getIPAddress());
            var networkDataList = getNetworksFromDomain(estabelecimento.retrieveNetworks(), routerId);

            var switchData = new TurmaData(
                    routerId,
                    switchId,
                    switchTypeData,
                    networkDataList,
                    ipData);
            return new EstabelecimentoData(routerId, routerTypeData, switchData);
        }

        private static List<Matricula> getNetworksFromData(List<MatriculaData> networkData){
            return networkData
                    .stream()
                    .map(network -> new Matricula(
                            IP.fromAddress(network.getIp().getAddress()),
                            network.getName(),
                            network.getCidr()))
                    .collect(Collectors.toList());
        }

        private static List<MatriculaData> getNetworksFromDomain(List<Matricula> matriculas, UUID switchId){
            return  matriculas
                     .stream()
                     .map(network -> new MatriculaData(
                            switchId,
                            IPData.fromAddress(network.getAddress().getIPAddress()),
                            network.getName(),
                            network.getCidr()))
                     .collect(Collectors.toList());
        }
}
