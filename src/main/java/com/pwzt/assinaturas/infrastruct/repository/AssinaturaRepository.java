package com.pwzt.assinaturas.infrastruct.repository;

import com.pwzt.assinaturas.infrastruct.entity.Assinatura;
import com.pwzt.assinaturas.infrastruct.enumerator.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AssinaturaRepository extends JpaRepository<Assinatura, UUID>{
    List<Assinatura> findByDataProximaCobrancaBeforeAndStatus(LocalDate dataAtual, Status status);
    long countByPlanoIdAndStatus(UUID planoId, Status status);
    long countByStatus(Status status);
}
