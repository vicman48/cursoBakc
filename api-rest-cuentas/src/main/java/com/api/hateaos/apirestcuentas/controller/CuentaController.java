package com.api.hateaos.apirestcuentas.controller;

import com.api.hateaos.apirestcuentas.model.Cuenta;
import com.api.hateaos.apirestcuentas.model.Monto;
import com.api.hateaos.apirestcuentas.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<Cuenta>> listarCuentas(){
        List<Cuenta> cuentas = cuentaService.listAll();

        if(cuentas.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        for(Cuenta cuenta:cuentas){
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId()))
                    .withSelfRel());
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas())
                    .withRel(IanaLinkRelations.COLLECTION));
        }

        CollectionModel<Cuenta> modelo = CollectionModel.of(cuentas);
        modelo.add(linkTo(methodOn(CuentaController.class).listarCuentas()).withSelfRel());
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> listarCuenta(@PathVariable Integer id){
        try{
            Cuenta cuenta = cuentaService.get(id);
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId()))
                      .withSelfRel());
            cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas())
                      .withRel(IanaLinkRelations.COLLECTION));

            return  new ResponseEntity<>(cuenta,HttpStatus.OK);
        }catch(Exception e){
            ResponseEntity.notFound().build();
        }

        return null;
    }

    @PostMapping
    public ResponseEntity<Cuenta> save(@RequestBody Cuenta cuenta){
        Cuenta saveCuenta = cuentaService.save(cuenta);
        return new ResponseEntity<>(cuenta,HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Cuenta> editarCuenta(@RequestBody Cuenta cuenta){
        Cuenta saveCuenta = cuentaService.save(cuenta);

        cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId()))
                .withSelfRel());
        cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas())
                .withRel(IanaLinkRelations.COLLECTION));

        return new ResponseEntity<>(saveCuenta,HttpStatus.OK);
    }
    @PatchMapping("/{id}/depositos")
    public ResponseEntity<Cuenta> depositarDinero(@PathVariable Integer id,@RequestBody Monto monto){
        Cuenta cuenta = cuentaService.depositar(monto.getMonto(),id);

        cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId()))
                .withSelfRel());
        cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(),null))
                .withRel("depositos"));
        cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas())
                .withRel(IanaLinkRelations.COLLECTION));
        return new ResponseEntity<>(cuenta,HttpStatus.OK);
    }

    @PatchMapping("/{id}/retiro")
    public ResponseEntity<Cuenta> retiroDinero(@PathVariable Integer id,@RequestBody Monto monto){
        Cuenta cuenta = cuentaService.retirar(monto.getMonto(),id);

        cuenta.add(linkTo(methodOn(CuentaController.class).listarCuenta(cuenta.getId()))
                .withSelfRel());
        cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(),null))
                .withRel("depositos"));
        cuenta.add(linkTo(methodOn(CuentaController.class).depositarDinero(cuenta.getId(),null))
                .withRel("retiro"));
        cuenta.add(linkTo(methodOn(CuentaController.class).listarCuentas())
                .withRel(IanaLinkRelations.COLLECTION));
        return new ResponseEntity<>(cuenta,HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> eliminarCuenta(@PathVariable Integer id){
        try{
            cuentaService.delete(id);
            ResponseEntity.noContent().build();
        }catch (Exception e){
            ResponseEntity.notFound().build();
        }
        return null;
    }
}
