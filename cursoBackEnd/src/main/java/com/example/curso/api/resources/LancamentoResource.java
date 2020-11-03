package com.example.curso.api.resources;

import com.example.curso.api.dto.LancamentoDTO;
import com.example.curso.service.LancamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {

    private LancamentoService lancamentoService;

    public LancamentoResource(LancamentoService lancamentoService){
        this.lancamentoService = lancamentoService;
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO lancamentoDTO){

    }
}
