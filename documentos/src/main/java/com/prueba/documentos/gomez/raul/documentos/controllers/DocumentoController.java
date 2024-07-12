package com.prueba.documentos.gomez.raul.documentos.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.documentos.gomez.raul.documentos.models.ApiMessage;
import com.prueba.documentos.gomez.raul.documentos.models.DocumentoEntity;
import com.prueba.documentos.gomez.raul.documentos.services.DocumentoService;
import com.prueba.documentos.gomez.raul.documentos.utils.PaginationRequest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController()
@RequestMapping(path = "documentos")
public class DocumentoController {

    private final DocumentoService documentService;
    Logger logger = LoggerFactory.getLogger(DocumentoController.class);

    DocumentoController(DocumentoService documentService){
        this.documentService = documentService;
    }

    @GetMapping("findAll")
    public ResponseEntity<Object> findAll(@RequestBody PaginationRequest<DocumentoEntity> page) {
        try {

            List<DocumentoEntity> doc = this.documentService.findAll(page);

            //Código diferente dependiendo del contenido
            return !doc.isEmpty() ? ResponseEntity.ok().body(doc) : ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                                .body(new ApiMessage("Se ha producido un error inesperado: " + e.getMessage()));
        }
        
    }
    

    @PostMapping("import")
    public ResponseEntity<ApiMessage> importDocument(@RequestBody MultipartFile file) {

        try {
            //Intentamos importar el documento
            documentService.importDocument(file);
            return ResponseEntity.ok().body(new ApiMessage("Operación completada correctamente"));
        } catch (Exception e) {
            logger.error("Se ha producido un error al importar los datos: " + e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiMessage("Se ha producido un error al importar los datos: " + e.getMessage()));
        }

        
    }
    
}
