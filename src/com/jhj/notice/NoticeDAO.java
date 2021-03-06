package com.jhj.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jhj.board.BoardDAO;
import com.jhj.board.BoardDTO;
import com.jhj.page.RowNumber;
import com.jhj.page.Search;
import com.jhj.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;

public class NoticeDAO implements BoardDAO{

	@Override
	public List<BoardDTO> selectList(RowNumber rowNumber) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql="select * from "
				+ "(select rownum R, N.* from "
				+ "(select num, title, writer, reg_date, hit from notice "
				+ "where "+rowNumber.getSearch().getKind()+" like ? "
				+ "order by num desc) N) "
				+ "where R between ? and ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+rowNumber.getSearch().getSearch()+"%");
		st.setInt(2, rowNumber.getStartRow());
		st.setInt(3, rowNumber.getLastRow());
		
		ResultSet rs = st.executeQuery();
		List<BoardDTO> ar = new ArrayList<>();
		NoticeDTO noticeDTO=null;
		while(rs.next()) {
			noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
			ar.add(noticeDTO);
		}
		DBConnector.disConnect(rs, st, con);
		return ar;
	}

	@Override
	public BoardDTO selectOne(int num) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "select * from notice where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		NoticeDTO noticeDTO=null;
		ResultSet rs = st.executeQuery();
		
		if(rs.next()) {
			noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setContents(rs.getString("contents"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
		}
		DBConnector.disConnect(rs, st, con);
		return noticeDTO;
	}

	//seq 
	public int getNum() throws Exception {
		Connection con = DBConnector.getConnect();
		String sql="select notice_seq.nextval from dual";
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		rs.next();
		int num = rs.getInt(1);
		DBConnector.disConnect(rs, st, con);
		return num;
	}
	
	
	@Override
	public int insert(BoardDTO boardDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "insert into notice values(?,?,?,?,sysdate,0)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, boardDTO.getNum());
		st.setString(2, boardDTO.getTitle());
		st.setString(3, boardDTO.getContents());
		st.setString(4, boardDTO.getWriter());
		
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		
		return result;
	}

	@Override
	public int update(BoardDTO boardDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql ="update notice set title=?, contents=? where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, boardDTO.getTitle());
		st.setString(2, boardDTO.getContents());
		st.setInt(3, boardDTO.getNum());
		int result = st.executeUpdate();
		
		DBConnector.disConnect(st, con);
		
		return result;
	}

	@Override
	public int delete(int num) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql="delete notice where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public int getCount(Search search) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql="select count(num) from notice "
				+ "where "+search.getKind()+" like ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%"+search.getSearch()+"%");
		
		ResultSet rs = st.executeQuery();
		rs.next();
		int result=rs.getInt(1);
		
		DBConnector.disConnect(rs, st, con);
		
		return result;
		
	}

	
	
}
