package com.techmaster.crud.repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.techmaster.crud.model.Person;


import org.springframework.util.ResourceUtils;


public class PersonDao extends Dao<Person> {
    
    public PersonDao(String csvFile){
        this.readCSV(csvFile);
    }

    @Override
    public void readCSV (String csvFile){
        try{
            File file = ResourceUtils.getFile("classpath:static/" + csvFile);
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator('|');
            ObjectReader oReader = mapper.readerFor(Person.class).with(schema);
            Reader reader = new FileReader(file);
            MappingIterator<Person> mi = oReader.readValue(reader);
            while(mi.hasNext()){
                Person person = mi.next();
                this.add(person);
            }
        }catch(IOException e){
            System.out.println(e);
        }
    }

    @Override
    public List<Person> getAll() {
        return collections;
    }

    @Override
    public void add(Person t) {
        // int id = collections.size() + 1;
        int id;
        if (collections.isEmpty()) {
            id = 1;
        } else {
            Person lastPerson = collections.get(collections.size() - 1);
            id = lastPerson.getId() + 1;      
        }
        t.setId(id);
        collections.add(t);
    }
}
