package com.example.datatxt_microservice.controllers;

import com.example.datatxt_microservice.entities.DataEntity;
import com.example.datatxt_microservice.services.DataService;
import com.example.datatxt_microservice.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;


@RestController
@RequestMapping("/data")
public class DataController{
    @Autowired
    DataService dataService;

    @Autowired
    private UploadService upload;

    static String info;

    @PostMapping("/load")
    public ResponseEntity<String> load(@RequestParam("archivos") MultipartFile file){
        if(!(file.isEmpty())){
            System.out.println("El archivo no es vacío");
            String salida = upload.save(file);  // Guardo el archivo
            System.out.println(salida);
            DataController.info = dataService.leerTxt("uploadFiles/DATA.txt");  // Leer el archivo
            // Este proceso también guarda en la base de datos
            return ResponseEntity.ok(salida);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al cargar el archivo");
        }
    }

    @GetMapping("/get-by-rut")
    public ResponseEntity<ArrayList<DataEntity>> getByRut(String rut){
        ArrayList<DataEntity> data = dataService.leerBdByRut(rut);
        if(data.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/get-by-rut-and-fecha")
    public ResponseEntity<ArrayList<DataEntity>> getByRutAndFecha(String rut, String fecha){
        ArrayList<DataEntity> data = dataService.leerBdByRutAndFecha(rut, fecha);
        if(data.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    @GetMapping("/asistio")
    public ResponseEntity<Boolean> asistio(String rut, String fecha){
        if(dataService.asistioEmpleadoDia(rut, fecha)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
