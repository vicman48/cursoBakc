package com.api.hateaos.apirestcuentas.service;

import com.api.hateaos.apirestcuentas.exception.CuentaNotFoundException;
import com.api.hateaos.apirestcuentas.model.Cuenta;
import com.api.hateaos.apirestcuentas.respository.CuentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> listAll(){
        return cuentaRepository.findAll();
    }

    public Cuenta get(Integer id){
        return cuentaRepository.findById(id).get();
    }

    public Cuenta save(Cuenta cuenta){
        return cuentaRepository.save(cuenta);
    }

    public void delete(Integer id) throws CuentaNotFoundException {
        if(cuentaRepository.existsById(id)){
            throw new CuentaNotFoundException("Cuenta no encontrada con el id"+id);
        }
        cuentaRepository.deleteById(id);

    }

    public Cuenta depositar(float monto,Integer id){
        cuentaRepository.actulizarMonto(monto,id);
        return cuentaRepository.findById(id).get();
    }

    public Cuenta retirar(float monto,Integer id){
        cuentaRepository.actulizarMonto(-monto,id);
        return cuentaRepository.findById(id).get();
    }
}
