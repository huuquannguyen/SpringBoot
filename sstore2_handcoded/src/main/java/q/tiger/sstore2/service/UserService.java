package q.tiger.sstore2.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import q.tiger.sstore2.exception.StorageException;
import q.tiger.sstore2.model.Account;
import q.tiger.sstore2.model.User;
import q.tiger.sstore2.repository.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Value("${upload.path.user}")
    private String path;

    public boolean login(Account account) {
        return userRepo.searchByAccount(account).isPresent();
    }

    public boolean signUp(User u) {
        if (!userRepo.searchUsername(u).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void addUserToDB(User u) {
        userRepo.create(u);
    }

    public void updateUserInfo(User user, HttpSession session){
        userRepo.updateUserInfo(user);
        session.setAttribute("userSession", user);
        session.setAttribute("photo", user.getPhoto().getOriginalFilename());
    }

    public void saveUserToSession(Account account, HttpSession session){
        User user = userRepo.searchByAccount(account).get();
        session.setAttribute("userSession", user);
        session.setAttribute("photo", user.getPhoto().getOriginalFilename());
    }

    public void uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        String fileName = file.getOriginalFilename();
        try {
            var is = file.getInputStream();
            Files.copy(is, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            var msg = String.format("Failed to store file %s", fileName);
            throw new StorageException(msg, e);
        }
    }
}
