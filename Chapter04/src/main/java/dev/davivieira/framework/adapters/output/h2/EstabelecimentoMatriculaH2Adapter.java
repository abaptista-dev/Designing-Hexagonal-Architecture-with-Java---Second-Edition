package dev.davivieira.framework.adapters.output.h2;

import dev.davivieira.application.ports.output.EstabelecimentoMatriculaOutputPort;
import dev.davivieira.domain.entity.Estabelecimento;
import dev.davivieira.domain.vo.EstabelecimentoId;
import dev.davivieira.framework.adapters.output.h2.data.EstabelecimentoData;
import dev.davivieira.framework.adapters.output.h2.mappers.RouterH2Mapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

public class EstabelecimentoMatriculaH2Adapter implements EstabelecimentoMatriculaOutputPort {

    private static EstabelecimentoMatriculaH2Adapter instance;

    @PersistenceContext
    private EntityManager em;

    private EstabelecimentoMatriculaH2Adapter(){
        setUpH2Database();
    }

    @Override
    public Estabelecimento fetchEstabelecimentoById(EstabelecimentoId estabelecimentoId) {
        var routerData = em.getReference(EstabelecimentoData.class, estabelecimentoId.getUUID());
        return RouterH2Mapper.toDomain(routerData);
    }

    @Override
    public boolean persistEstabelecimento(Estabelecimento estabelecimento) {
        var routerData = RouterH2Mapper.toH2(estabelecimento);
        em.persist(routerData);
        return true;
    }

    private void setUpH2Database() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("inventory");
        EntityManager em = entityManagerFactory.createEntityManager();
        this.em = em;
    }

    public static EstabelecimentoMatriculaH2Adapter getInstance() {
        if (instance == null) {
            instance = new EstabelecimentoMatriculaH2Adapter();
        }
        return instance;
    }
}
