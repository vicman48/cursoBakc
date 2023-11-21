package com.api.hateaos.apirestcuentas;


import com.api.hateaos.apirestcuentas.model.Cuenta;
import com.api.hateaos.apirestcuentas.respository.CuentaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@Rollback(value = true)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class CuentaRepositoryTest {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Test
    void testAgregarCuenta(){
        Cuenta cuenta = new Cuenta(1224,"100");
        Cuenta cuentaGuardad = cuentaRepository.save(cuenta);

        Assertions.assertThat(cuentaGuardad).isNotNull();// cuenta no null
        Assertions.assertThat(cuentaGuardad.getId());//comprobamos que el id de la cuenta
                                                                            // guardada sea made uno
    }
}
