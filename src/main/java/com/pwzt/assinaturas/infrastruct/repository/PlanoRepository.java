package com.pwzt.assinaturas.infrastruct.repository;

import com.pwzt.assinaturas.infrastruct.entity.Plano;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanoRepository extends JpaRepository<Plano, UUID> {}
