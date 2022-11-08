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

    @GetMapping("/all")
    public ResponseEntity<ArrayList<DataEntity>> getAll(){
        System.out.println("Iniciando servicio externo data");
        ArrayList<DataEntity> data = dataService.getAll();
        if(data.isEmpty()){
            System.out.println("No hay datos.");
            return ResponseEntity.noContent().build();
        }
        System.out.println("Todo ok");
        return ResponseEntity.ok(data);
    }

    @PostMapping("/load")
    public ResponseEntity<String> load(@RequestParam("archivos") MultipartFile file){
        if(!(file.isEmpty())){
            System.out.println("El archivo no es vacío");
            String salida = upload.save(file);  // Guardo el archivo
            System.out.println(salida);
            DataController.info = dataService.leerTxt("uploadFiles/DATA.txt");  // Leer el archivo
            return ResponseEntity.ok(salida);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al cargar el archivo");
        }
    }

    @GetMapping("/get-by-rut/{rut}")
    public ResponseEntity<ArrayList<DataEntity>> getByRut(@PathVariable("rut") String rut){
        ArrayList<DataEntity> data = dataService.leerBdByRut(rut);
        if(data.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(data);
    }

    // Listo!
    @GetMapping("/get-by-rut-and-fecha/{rut}/{fecha}")
    public ResponseEntity<ArrayList<DataEntity>> getByRutAndFecha(@PathVariable("rut") String rut, @PathVariable("fecha") String fecha){
        System.out.println("Iniciando controllador data original");
        ArrayList<DataEntity> data = dataService.leerBdByRutAndFecha(rut, dataService.reformatFecha(fecha));
        if(data.isEmpty()){
            System.out.println("No se encontraron datos.");
            return ResponseEntity.noContent().build();
        }
        System.out.println("Todo ok.");
        return ResponseEntity.ok(data);
    }

    @GetMapping("/asistio")
    public ResponseEntity<Boolean> asistio(@RequestParam("rut") String rut, @RequestParam("fecha") String fecha){
        if(dataService.asistioEmpleadoDia(rut, fecha)){
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
