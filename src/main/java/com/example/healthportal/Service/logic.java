package com.example.healthportal.Service;


import com.example.healthportal.Model.User;
import com.example.healthportal.Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class logic {
private final UserRepository repository;//use repo

public  logic (UserRepository repository)
{
    this.repository = repository;//pointing the repo to push the conditions
}

public User adddata(User data){
    return repository.save(data);

}

public List <User> getall(){
return repository.findAll();
}

}
