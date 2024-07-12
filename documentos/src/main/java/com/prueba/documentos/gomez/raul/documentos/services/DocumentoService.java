package com.prueba.documentos.gomez.raul.documentos.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.prueba.documentos.gomez.raul.documentos.models.DocumentoEntity;
import com.prueba.documentos.gomez.raul.documentos.utils.PaginationRequest;

public interface DocumentoService {

    public void save(DocumentoEntity documento);

    /**
     * Función que importa en nuestra base de datos relacional, todos las
     * entidades que existan en el documento csv pasado por parametro.
     * 
     * Si una de las filas está en un formato incorrecto, no se guardará
     * ningún dato, y se informará del error al usuario para que corrija
     * el documento y lo vuelva a intentar.
     * 
     * @param file documento csv con información a importar en la bbdd
     * @throws IOException
     */
    public void importDocument(MultipartFile file) throws IOException;

    public List<DocumentoEntity> findAll(PaginationRequest<DocumentoEntity> page);

}
