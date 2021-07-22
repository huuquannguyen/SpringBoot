package com.techmaster.crud.repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
            MappingIterator<Person> mi = oReader.readValues(reader);
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
        t.setPhotoName(t.getPhoto().getOriginalFilename());
        collections.add(t);
    }

    @Override
    public Optional<Person> get(int id) {
        return collections.stream().filter(u -> u.getId() == id).findFirst();
    }

    @Override
    public void update(Person t) {
        get(t.getId()).ifPresent(p -> {p.setName(t.getName());
                                       p.setJob(t.getJob());
                                       p.setGender(t.isGender());
                                       p.setBirthDay(t.getBirthDay());
                                       p.setPhoto(t.getPhoto());
                                       p.setPhotoName(t.getPhoto().getOriginalFilename());
        });
    }

    @Override
    public void deleteByID(int id) {
        get(id).ifPresent(p -> collections.remove(p));
    }

    @Override
    public void delete(Person t) {
        deleteByID(t.getId());
    }

    @Override
    public List<Person> searchByKeyword(String keyword) {
        return collections
        .stream()
        .filter(p -> p.matchByKeyword(keyword))
        .collect(Collectors.toList());
    }
}
