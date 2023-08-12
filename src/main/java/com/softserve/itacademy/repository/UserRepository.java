package com.softserve.itacademy.repository;

import com.softserve.itacademy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from users where email =?1", nativeQuery = true)
    User getUserByEmail(String email);
    @Query(value = "select collaborator_id from todo_collaborator where todo_id =?1", nativeQuery = true)
    List<Long> getCollaboratorByTodoId(int todo_id);
    @Modifying
    @Query(value = "insert into todo_collaborator (collaborator_id, todo_id) values (?1,?2)", nativeQuery = true)
    int addCollaboratorByTodoId(long user_id, int todo_id);
    @Modifying
    @Query(value = "delete from todo_collaborator where collaborator_id =?1 and todo_id =?2", nativeQuery = true)
    int deleteCollaboratorByTodoId(long user_id, int todo_id);
}
