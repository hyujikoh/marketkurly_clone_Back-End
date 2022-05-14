package com.example.marketkurly_clone.src.user;


import com.example.marketkurly_clone.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.SqlSession;
import javax.sql.DataSource;
import java.util.List;
/*
* 해당 다오는 이대로 둘 예정 하지만 사용은 되도록이면 안하고 mapper 구조로 진행할 예정
*
* */
@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Autowired private SqlSession sqlSession;

////    public List<GetUserRes> getUsers(){
////        String getUsersQuery = "select * from UserInfo";
////        return this.jdbcTemplate.query(getUsersQuery,
////                (rs,rowNum) -> new GetUserRes(
////                        rs.getInt("userIdx"),
////                        rs.getString("userName"),
////                        rs.getString("ID"),
////                        rs.getString("Email"),
////                        rs.getString("password"))
////                );
////    }
//
////    public List<GetUserRes> getUsersByIdx(int user_idx){
////        UserMapper userMapper = sqlSession.getMapper(userMapper.class);
////        List<GetUserRes> getUserRes =userMapper.selectMember(user_idx);
////    }
//

    

//    public int createUser(PostUserReq postUserReq){
//        String createUserQuery = "insert into UserInfo (userName, ID, password, email) VALUES (?,?,?,?)";
//        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getId(), postUserReq.getPassword(), postUserReq.getEmail()};
//        this.jdbcTemplate.update(createUserQuery, createUserParams);
//
//        String lastInserIdQuery = "select last_insert_id()";
//        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
//    }
//
//    public int checkEmail(String email){
//        String checkEmailQuery = "select exists(select email from UserInfo where email = ?)";
//        String checkEmailParams = email;
//        return this.jdbcTemplate.queryForObject(checkEmailQuery,
//                int.class,
//                checkEmailParams);
//
//    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }




}
