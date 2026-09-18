package main.java.Repositories.impl;

import main.java.Models.User;
import main.java.Repositories.UserRepository;

import java.util.*;

public class InMemoryUserRepo implements UserRepository {

    private static final HashMap<String , User> users = new HashMap<>();

    @Override
    public User save(User user){
        users.put(user.getId(), user);

        return  user;
    }

    @Override
    public Optional<User> findByEmail(String email){
         for(User user : users.values()){
             if(user.getEmail().equalsIgnoreCase(email)){
                 return Optional.of(user);
             }
         }
         return Optional.empty();
    }

    @Override
    public Optional<User> findById(String id){
        for(User user : users.values()){
            if(user.getId().equalsIgnoreCase(id)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email){
        return findByEmail(email).isPresent();
    }

    @Override
      public List<User> findAll(){
          return  new ArrayList<>(users.values());
        }

    @Override
        public User update(User user){
            users.put(user.getId(), user);
            return user;
        }

        @Override
       public void updatePassword(User user , String password){
            user.setPassword(password);
        }

}
