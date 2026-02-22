package com.pwzt.assinaturas.infrastruct.repository;

import com.pwzt.assinaturas.infrastruct.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventoRepository extends JpaRepository<Evento, UUID> {}
