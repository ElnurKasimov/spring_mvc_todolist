package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
            return userRepository.save(user);
    }

    @Override
    public User readById(long id) {
        Optional<User> optional = userRepository.findById(id);
            return optional.get();
    }

    @Override
    public User update(User user) {
            User oldUser = readById(user.getId());
                return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        User user = readById(id);
            userRepository.delete(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }
    public List<User> getCollaboratorByTodoId(int todo_id){
        List<User> collaborators = new ArrayList<>();
        for(long id : userRepository.getCollaboratorByTodoId(todo_id)){
            collaborators.add(readById(id));
        }
        return collaborators;
    }
    @Transactional
    public void addCollaborator(long user_id, int todo_id){
        userRepository.addCollaboratorByTodoId(user_id, todo_id);
    }
    @Transactional
    public void deleteCollaborator(long user_id, int todo_id){
        userRepository.deleteCollaboratorByTodoId(user_id, todo_id);
    }
}
