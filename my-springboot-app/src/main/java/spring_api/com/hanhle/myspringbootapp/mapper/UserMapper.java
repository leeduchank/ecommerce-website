package spring_api.com.hanhle.myspringbootapp.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.control.MappingControl;
import spring_api.com.hanhle.myspringbootapp.dto.UserDto;
import spring_api.com.hanhle.myspringbootapp.entities.UserEntity;
import java.util.List;

@Mapper (componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(UserDto userDto);
    UserDto toUserDto (UserEntity userEntity);

    List<UserDto> toListUserDto (List<UserEntity> userEntityList);
    void updateUser(@MappingTarget UserEntity userEntity, UserDto userDto);
}
