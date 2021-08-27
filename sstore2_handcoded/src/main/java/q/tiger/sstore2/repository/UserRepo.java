package q.tiger.sstore2.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import q.tiger.sstore2.model.Account;
import q.tiger.sstore2.model.User;

@Repository
public class UserRepo implements Dao<User> {

    List<User> list = new ArrayList<>();

    @Override
    public List<User> getall() {
        return list;
    }

    @Override
    public Optional<User> search(User u) {
        Optional<User> user = list
        .stream()
        .filter(us -> us.getAccount().equals(u.getAccount()))
        .findFirst();
        return user;
    }

    @Override
    public Optional<User> searchById(int id) {
        return list.stream().filter(u -> u.getId() == id).findFirst();
    }

    @Override
    public void create(User t) {
        int index = list.size();
        t.setId(index + 1);
        list.add(t);
    }

    @Override
    public void remove(int id) {
        searchById(id).ifPresent(delAccount -> list.remove(delAccount));
    }

    @Override
    public void update(User u) {
        Optional<User> user = searchById(u.getId());
        user.ifPresent(updateUser -> {
            updateUser.setAccount(u.getAccount());
            updateUser.setAddress(u.getAddress());
            updateUser.setEmail(u.getEmail());
            updateUser.setName(u.getName());
            updateUser.setPhone(u.getPhone());
            updateUser.setPhoto(u.getPhoto());
        });
        // System.out.println(list.get(u.getId()));
    }

    public Optional<User> searchByAccount(Account a){
        return list.stream().filter(u -> u.getAccount().equals(a)).findFirst();
    }

    public Optional<User> searchUsername(User u){
        return list
        .stream()
        .filter(user -> user.getAccount().getUsername().equals(u.getAccount().getUsername())).findFirst();
    }

    public void updateUserInfo(User u){
        this.list.set(u.getId(), u);
    }
    
}
