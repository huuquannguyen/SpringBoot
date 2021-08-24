package q.tiger.sstore2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import q.tiger.sstore2.model.Account;
import q.tiger.sstore2.model.User;
import q.tiger.sstore2.repository.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    public boolean login(Account account){
        return userRepo.searchByAccount(account).isPresent();
    }

    public boolean signUp(User u){
        if(!userRepo.searchUsername(u).isPresent()){
            return true;
        }else{
            return false;
        }
    }

    public void addUserToDB(User u){
        userRepo.add(u);
    }
}
