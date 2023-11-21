package com.api.hateaos.apirestcuentas.respository;

import com.api.hateaos.apirestcuentas.model.Cuenta;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta,Integer> {

    @Query("UPDATE Cuenta c SET c.monto=c.monto +?1 where c.id=?2")
    @Modifying
    void actulizarMonto(float monto,Integer id);
}
