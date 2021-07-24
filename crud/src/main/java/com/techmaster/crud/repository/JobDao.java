package com.techmaster.crud.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.techmaster.crud.model.Job;

import org.springframework.stereotype.Repository;

@Repository
public class JobDao {

    private List<Job> collections = new ArrayList<>();

    public JobDao (){
        List<Job> listJobs = Arrays.asList(
          new Job("Developer", 1),
          new Job("Actor", 2),
          new Job("Singer", 3),
          new Job("Taxi driver", 4)
        );
        listJobs.forEach(job -> collections.add(job));
    }
    
    public List<Job> getAll() {
        return collections;
    }

    public Job add(Job j){
        int id = collections.size() + 1;
        j.setId(id);
        collections.add(j);
        return j;
    }

    public Optional<Job> get(int id) {
        return collections.stream().filter(u -> u.getId() == id).findFirst();
    }


    public List<Job> searchByKeyword(String keyword) {
        return collections
        .stream()
        .filter(job -> job.getName().toLowerCase().contains(keyword.toLowerCase()))
        .collect(Collectors.toList());
    }
}
