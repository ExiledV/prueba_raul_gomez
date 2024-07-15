package com.prueba.documentos.gomez.raul.documentos.services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.prueba.documentos.gomez.raul.documentos.models.DocumentoEntity;
import com.prueba.documentos.gomez.raul.documentos.repositories.DocumentoRepository;
import com.prueba.documentos.gomez.raul.documentos.utils.PaginationRequest;

import jakarta.transaction.Transactional;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    static final String CSV_SAVING_PATH = "logs/csv/";
    DocumentoRepository documentoRepository;

    DocumentoServiceImpl(DocumentoRepository documentoRepository){
        this.documentoRepository = documentoRepository;
    }

    Logger logger = LoggerFactory.getLogger(DocumentoServiceImpl.class);

    @Override
    public List<DocumentoEntity> findAll(PaginationRequest<DocumentoEntity> page) {
        return this.documentoRepository.findAll(page);
    }

    @Override
    @Transactional
    public void save(DocumentoEntity documento) {
        this.documentoRepository.save(documento);
    }

    @Override
    public void importDocument(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("The uploaded file is empty");
        }

        Integer linesReaded = 0;
        List<DocumentoEntity> docEntities = new ArrayList<>();

        //Creamos la fecha para agregarla al nombre del fichero junto con su formato
        Date currentDate = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String formattedDate = dateFormat.format(currentDate);

        //Creamos el directorio si no existe
        Files.createDirectories(Paths.get(CSV_SAVING_PATH));

        //Abrimos el fichero
        try (
                CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream())); 
                CSVWriter writer = new CSVWriter(
                                    new FileWriter(CSV_SAVING_PATH + formattedDate + ".csv"))
            ) {

            
            String[] cabeceras = reader.readNext();
            

            String[] line;

            //Leemos el csv
            while ((line = reader.readNext()) != null) {

                //Generamos la entidad, si la linea del csv es incorrecta, saltará el catch y no guardará
                DocumentoEntity entity = DocumentoEntity.generateFromCsvLine(line);

                docEntities.add(entity);
                linesReaded++;
            }

            //Escribimos las cabeceras
            writer.writeNext(cabeceras);

            
            //Guardamos todas una vez validadas y las ordenamos por orderId
            docEntities = docEntities.stream()
                .sorted((e1, e2) -> e1.getOrderId().compareTo(e2.getOrderId()))
                .collect(Collectors.toList());

            
            this.documentoRepository.saveAll(docEntities);

            //Escribimos el documento resumen ordenado por orderId
            writer.writeAll(docEntities.stream().map(e -> e.formatToCsv()).collect(Collectors.toList()));

            //Hacemos el log de los datos guardados
            this.logSavedCsvData(docEntities);

        } catch (IOException | CsvValidationException e) {
            throw new IOException("Error al leer o crear el fichero csv en la linea + " + linesReaded);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error en la fila " + linesReaded + " .\n" + e.getMessage());
        }

    }

    private void logSavedCsvData(List<DocumentoEntity> docEntities) {
        HashMap <String, Long> regionsSaved = new HashMap<String, Long>();
        HashMap <String, Long> countriesSaved = new HashMap<String, Long>();
        HashMap <String, Long> itemTypesSaved = new HashMap<String, Long>();
        HashMap <String, Long> salesChannelsSaved = new HashMap<String, Long>();
        HashMap <String, Long> ordersPrioSaved = new HashMap<String, Long>();

        docEntities.stream()
        .forEach(e -> {
            //Guardamos la informacion
            this.accumDataInHashMap(regionsSaved, e.getRegion());
            this.accumDataInHashMap(countriesSaved, e.getCountry());
            this.accumDataInHashMap(itemTypesSaved, e.getItemType());
            this.accumDataInHashMap(salesChannelsSaved, e.getSalesChannel());
            this.accumDataInHashMap(ordersPrioSaved, e.getOrderPriority());
        });

        //Loggeamos la informacion
        logger.info("Regions saved:");
        logger.info(regionsSaved.toString());

        logger.info("Countries saved:");
        logger.info(countriesSaved.toString());

        logger.info("Item types saved:");
        logger.info(itemTypesSaved.toString());

        logger.info("Sales channels saved:");
        logger.info(salesChannelsSaved.toString());

        logger.info("Order priorities saved:");
        logger.info(ordersPrioSaved.toString());
    }

    private void accumDataInHashMap(HashMap<String, Long> hashMap, String key) {
        if(!hashMap.containsKey(key)){
            hashMap.put(key, Long.valueOf(1));
        } else {
            hashMap.put(key, hashMap.get(key) + 1);
        }
    }
    
}
