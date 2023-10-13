package ar.com.laboratory.ecommerce.infrastructure.adapter;

import ar.com.laboratory.ecommerce.application.repository.UserRepository;
import ar.com.laboratory.ecommerce.domain.User;
import ar.com.laboratory.ecommerce.infrastructure.mapper.UserMapper;
import ar.com.laboratory.ecommerce.infrastructure.util.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;


@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private UserCrudRepository repository;
    private UserMapper userMapper;
    private final static String TABLE = "users";
    @Override
    public Iterable<User> getUsers() {
        return userMapper.toUsers(repository.findAll());
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.toUser(repository.findById(id).get());
    }

    @Override
    public User save(User user) {
        return userMapper.toUser(repository.save(userMapper.toEntity(user)));
    }

    @Override
    public void deleteUser(Integer id) {
        var user = repository.findById(id);
        if (Objects.isNull(user)){
            throw new ProductNotFoundException(TABLE);
        }
        repository.deleteById(id);
    }
    @Override
    public User update(User user) {
        return userMapper.toUser(repository.save(userMapper.toEntity(user)));
    }
}
