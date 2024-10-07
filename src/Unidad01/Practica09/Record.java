package Unidad01.Practica09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa una colección de registros.
 * Cada registro es un mapa de claves y valores basado en los campos definidos.
 */
public class Record {
    private final List<Record> records;
    private final Map<String, String> record;
    private final List<String> fields;
    private final List<Integer> fieldLengths;

    public Record(List<String> fields, List<Integer> fieldLengths) {
        this.fields = fields;
        this.fieldLengths = fieldLengths;
        this.record = new HashMap<>();
        this.records = new ArrayList<>();
    }
    
    public List<Record> getRecords() {
        return this.records;
    }
    public Map<String, String> getRecord(int position) {
        if (position < 0 || position >= this.records.size()) {
            System.out.println("Error: Position out of bounds.");
            return null;
        }
        return this.records.get(position).record;
    }
    public int getRecordCount() {
        return this.records.size();
    }
    public int getRecordLength() {
        int totalLength = 0;
        for (int length : fieldLengths) {
            totalLength += length;
        }
        return totalLength;
    }
    public void addRecord(Map<String, String> rec) {
        // Validar longitudes de los campos antes de añadir
        if (validateRecord(rec)) {
            this.records.add((Record) rec);
        } else {
            System.out.println("Error: Record does not match field lengths.");
        }
    }
    public void removeRecord(int position) {
        if (position < 0 || position >= this.records.size()) {
            System.out.println("Error: Position out of bounds.");
            return;
        }
        this.records.remove(position);
    }
    private boolean validateRecord(Map<String, String> record) {
        for (int i = 0; i < fields.size(); i++) {
            String field = fields.get(i);
            String value = record.get(field);
            if (value == null || value.length() > fieldLengths.get(i)) {
                return false; // El registro no es válido
            }
        }
        return true; // El registro es válido
    }
    

    public List<String> getFields() {
        return this.fields;
    }
    public String getField(int position) {
        return this.fields.get(position);
    }
    public int getFieldsCount() {
        return fields.size();
    }
    public List<Integer> getFieldLengths() {
        return this.fieldLengths;
    }
    public int getFieldLength(int position) {
        return this.fieldLengths.get(position);
    }
    public int getFieldLengthsCount() {
        return this.fieldLengths.size();
    }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Record r : records) {
            sb.append(r.toString()).append("\n");
        }
        return sb.toString();
    }

    Integer getFielDLenght(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
