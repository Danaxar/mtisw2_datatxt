package com.example.datatxt_microservice.services;

import com.example.datatxt_microservice.entities.DataEntity;
import com.example.datatxt_microservice.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

@Service
public class DataService {
    @Autowired
    DataRepository dataRepository;

    // Métodos
    public String leerTxt(String direccion){
        String texto = "";
        try{
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            System.out.println("Archivo leido:\n");
            while((bfRead = bf.readLine()) != null){
                DataEntity dataEntity = new DataEntity();
                dataEntity.setFecha(bfRead.split(";")[0]);
                dataEntity.setHora(bfRead.split(";")[1]);
                dataEntity.setRut(bfRead.split(";")[2]);
                dataRepository.save(dataEntity);   // Guardar en la base de datos
                System.out.println(bfRead);
                temp = temp + bfRead;
            }
            texto = temp;
        }catch (Exception e){System.out.println("No se ha podido leer el archivo");}
        return texto;
    }

    public ArrayList<DataEntity> leerBdByRut(String rut){
        return (ArrayList<DataEntity>) dataRepository.findDataEntitiesByRut(rut);
    }

    public ArrayList<DataEntity> leerBdByRutAndFecha(String rut, String fecha){
        return (ArrayList<DataEntity>) dataRepository.findDataEntitiesByRutAndFecha(rut, fecha);
    }

    // Retorna true en caso de que el empleado asistió a trabajar dicho día
    public boolean asistioEmpleadoDia(String rut, String fecha){
        ArrayList<DataEntity> obj = (ArrayList<DataEntity>) dataRepository.findDataEntitiesByRutAndFecha(rut, fecha);
        if(obj.size() == 2){
            return true;
        }
        return false;
    }
}
