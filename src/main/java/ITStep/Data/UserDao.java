package ITStep.Data;

import ITStep.Data.model.UsersEntity;

import java.util.List;

public interface UserDao {

    UsersEntity findOne(long id);

    List<UsersEntity> findAll();

    boolean createUser(UsersEntity UsersEntity);

    boolean updateUser(UsersEntity UsersEntity);

    boolean deleteUser(UsersEntity UsersEntity);
}
