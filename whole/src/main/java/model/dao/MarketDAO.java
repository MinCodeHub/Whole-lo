package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dto.FoodDTO;
import model.dto.MarketDTO;
import model.entity.ItemEntity;
import model.entity.PostEntity;

public class MarketDAO {
    private JDBCUtil jdbcUtil = null;

    public MarketDAO() {
        jdbcUtil = new JDBCUtil(); // JDBCUtil 객체 생성
    }
    
    // 타입 상관없이 모든 글을 보여주기
    public List<ItemEntity> findItemListAllType() throws SQLException {

        String sql = "SELECT post_id, title, type, content, image,reg_date, writer_id,cnt_like,detail,price,scrap "
                + "FROM ITEM JOIN POST USING (post_id) "
                + "WHERE type = 'MARKET' OR type='MY_REFRIGERATOR'";

        jdbcUtil.setSqlAndParameters(sql,null); // JDBCUtil에 query문 설정

  
        try {
            ResultSet rs = jdbcUtil.executeQuery(); // query 실행
           
            List<ItemEntity> allitemList = new ArrayList<ItemEntity>(); // 타입이 Market인 Post 리스트 생성
            while (rs.next()) {
                ItemEntity item = new ItemEntity();
                item.setPostId(rs.getLong("post_id"));// post 객체를 생성하여 현재 행의 정보를 저장
                item.setTitle(rs.getString("title"));
                item.setType(rs.getString("type"));
                item.setContent(rs.getString("content"));
                item.setImage(rs.getString("image"));
                item.setRegDate(rs.getDate("reg_date").toLocalDate());
                item.setWriterId(rs.getLong("writer_id"));
                item.setCntLike(rs.getInt("cnt_like"));
                item.setDetail(rs.getString("detail"));
                item.setPrice(rs.getLong("price"));
                item.setScrap(rs.getInt("scrap"));
                allitemList.add(item);             // List에 post 객체 저장
            }
            return allitemList;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return null;
    }
    
    // 글 중에서 타입에 따라서 아이템 글 리스트을 보여주기
    public List<ItemEntity> findItemListType(String type) throws SQLException {

        String sql = "SELECT post_id, title, type, content, image,reg_date, writer_id,cnt_like,detail,price,scrap "
                + "FROM ITEM JOIN POST USING (post_id) "
                + "WHERE type = ? ";

        jdbcUtil.setSqlAndParameters(sql,  new Object[]{type}); // JDBCUtil에 query문 설정

  
        try {
            ResultSet rs = jdbcUtil.executeQuery(); // query 실행
           
            List<ItemEntity> itemList = new ArrayList<ItemEntity>(); // 타입이 Market인 Post 리스트 생성
            while (rs.next()) {
                ItemEntity item = new ItemEntity();
                item.setPostId(rs.getLong("post_id"));// post 객체를 생성하여 현재 행의 정보를 저장
                item.setTitle(rs.getString("title"));
                item.setType(rs.getString("type"));
                item.setContent(rs.getString("content"));
                item.setImage(rs.getString("image"));
                item.setRegDate(rs.getDate("reg_date").toLocalDate());
                item.setWriterId(rs.getLong("writer_id"));
                item.setCntLike(rs.getInt("cnt_like"));
                item.setDetail(rs.getString("detail"));
                item.setPrice(rs.getLong("price"));
                item.setScrap(rs.getInt("scrap"));
                itemList.add(item);             // List에 post 객체 저장
            }
            return itemList;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return null;
    }
    // 글 제목으로 select해서 상세 글 보이도록
    // 사진, 제목, 가격, 지역, 태그, 업로드 날짜 가져오기
    public ItemEntity findItemDetail(String type,String title) throws SQLException {

    String sql = "SELECT post_id, item.cnt_like,item.detail, item.price,item.scrap, post.title, post.image,post.type,post.content,post.reg_date,post.writer_id "
            + "FROM ITEM JOIN POST USING (post_id) "
            + "WHERE post.type = ? and post.title = ?";
    
    jdbcUtil.setSqlAndParameters(sql, new Object[]{type, title}); // JDBCUtil에 query문 설정
 
    try {
        ResultSet rs = jdbcUtil.executeQuery(); // query 실행
   
        if(rs.next()) {
            ItemEntity item = new ItemEntity();
            item.setPostId(rs.getLong("post_id"));// post 객체를 생성하여 현재 행의 정보를 저장
            item.setCntLike(rs.getInt("cnt_like"));
            item.setDetail(rs.getString("detail"));
            item.setPrice(rs.getLong("price"));
            item.setScrap(rs.getInt("scrap"));
            item.setTitle(rs.getString("title"));
            item.setImage(rs.getString("image"));
            item.setType(rs.getString("type"));
            item.setContent(rs.getString("content"));
            item.setRegDate(rs.getDate("reg_date").toLocalDate());
            item.setWriterId(rs.getLong("writer_id"));

            return item;         
        }
      

    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        jdbcUtil.close(); // resource 반환
    }
    return null;
}

    //타입 별 같은 성별에 해당하는 아이템 전체 리스트만 보여주기 user과 join >> 성별과 마켓인지 장터인지 사용자가 입력
    
    public List<ItemEntity> findItemListSameGender(String gender, String type) throws SQLException {

        String sql = "SELECT POST.post_id, POST.title, POST.type, POST.content, POST.image, POST.reg_date, POST.writer_id,"
                + "ITEM.cnt_like, ITEM.detail, ITEM.price, ITEM.scrap, USER_TABLE.gender "
                + "FROM ITEM "
                + "JOIN POST ON ITEM.post_id = POST.post_id "
                + "JOIN USER_TABLE ON POST.writer_id = USER_TABLE.user_id "
                + "WHERE USER_TABLE.gender = ? AND POST.type= ?";

        jdbcUtil.setSqlAndParameters(sql, new Object[]{gender, type}); // JDBCUtil에 query문 설정

  
        try {
            ResultSet rs = jdbcUtil.executeQuery(); // query 실행
           
            List<ItemEntity> GenderTypeitemList = new ArrayList<ItemEntity>(); // 타입이 Market인 Post 리스트 생성
            while (rs.next()) {
                ItemEntity item = new ItemEntity();
                item.setPostId(rs.getLong("post_id"));// post 객체를 생성하여 현재 행의 정보를 저장
                item.setTitle(rs.getString("title"));
                item.setType(rs.getString("type"));
                item.setContent(rs.getString("content"));
                item.setImage(rs.getString("image"));
                item.setRegDate(rs.getDate("reg_date").toLocalDate());
                item.setWriterId(rs.getLong("writer_id"));
                item.setCntLike(rs.getInt("cnt_like"));
                item.setDetail(rs.getString("detail"));
                item.setPrice(rs.getLong("price"));
                item.setScrap(rs.getInt("scrap"));
                GenderTypeitemList.add(item);             // List에 post 객체 저장
            }
            return GenderTypeitemList;

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jdbcUtil.close(); // resource 반환
        }
        return null;
    }
   //타입이 MY_REFRIGERATOR인 글의 타이틀을 제목으로 insert?
    //아니면 그냥 단순하게 삽입..?
    public MarketDTO createMarket(MarketDTO marketDto) throws SQLException {
	    /* post에 insert */
	    String sql1 = "INSERT INTO POST VALUES (SEQUENCE_POSTID.nextval, ?, ?,"
	    		+ " ?, ?, SYSDATE, ?)";
	    Object[] param1 = new Object[]{marketDto.getTitle(), marketDto.getContent(),
	    		marketDto.getImage(), marketDto.getWriterId()};
	    jdbcUtil.setSqlAndParameters(sql1, param1);    // JDBCUtil 에 insert문과 매개 변수 설정

	    String key[] = {"post_id"};    // PK 컬럼의 이름     
	    
	    try {
	        jdbcUtil.executeUpdate(key);  // insert 문 실행
	        ResultSet rs = jdbcUtil.getGeneratedKeys();
	        if (rs.next()) {
	            Long generatedKey = rs.getLong(1);   // 생성된 PK 값
	            marketDto.setPostId(generatedKey);  // id필드에 저장 
	            String sql2 = "INSERT INTO ITEM VALUES (0,0,SEQUENCE_POSTID.currval, ?, ?, ?)";
	            Object[] param2 = new Object[]{marketDto.getPrice(),marketDto.getDetail(), marketDto.getItemType()};
	            jdbcUtil.setSqlAndParameters(sql2, param2);    // JDBCUtil 에 insert문과 매개 변수 설정
	            jdbcUtil.executeUpdate();
	        }
	        return marketDto;
	    } catch (Exception ex) {
	        jdbcUtil.rollback();
	        ex.printStackTrace();
	    } finally {        
	        jdbcUtil.commit();
	        jdbcUtil.close();    // resource 반환
	    }        
	    
        return null;            
	}

}

