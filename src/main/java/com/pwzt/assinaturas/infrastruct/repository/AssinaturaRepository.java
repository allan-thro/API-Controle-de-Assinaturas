package com.pwzt.assinaturas.infrastruct.repository;

import com.pwzt.assinaturas.infrastruct.entity.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssinaturaRepository extends JpaRepository<Assinatura, UUID>{}
