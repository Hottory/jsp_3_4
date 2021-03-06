package com.jhj.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.jhj.board.BoardDAO;
import com.jhj.board.BoardDTO;
import com.jhj.board.BoardReply;
import com.jhj.board.BoardReplyDTO;
import com.jhj.page.RowNumber;
import com.jhj.page.Search;
import com.jhj.util.DBConnector;

public class QnaDAO implements BoardDAO, BoardReply {
	public int replyNum() throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "select qna_seq.nextval from dual";

		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		rs.next();
		int result = rs.getInt(1);

		DBConnector.disConnect(rs, st, con);
		return result;
	}

	@Override
	public int reply(BoardReplyDTO boardReplyDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "insert into qna valuse(?,?,?,?,sysdate,0,?,?,?)";

		PreparedStatement st = con.prepareStatement(sql);

		int result = st.executeUpdate();
		st.setInt(1, boardReplyDTO.getNum());
		st.setString(2, boardReplyDTO.getTitle());
		st.setString(3, boardReplyDTO.getContents());
		st.setString(4, boardReplyDTO.getWriter());
		st.setInt(5, boardReplyDTO.getRef());
		st.setInt(6, boardReplyDTO.getStep() + 1);
		st.setInt(7, boardReplyDTO.getDepth() + 1);
		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public int replyUpdate(BoardReplyDTO boardReplyDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "update qna set step = step+1 where ref = ? and step > ?";

		PreparedStatement st = con.prepareStatement(sql);
		int result = st.executeUpdate();

		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public List<BoardDTO> selectList(RowNumber rowNumber) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "select * from " + "(select rownum R, N.* from " + "(select * from qna " + "where "
				+ rowNumber.getSearch().getKind() + " like ? " + "order by ref desc, step asc) N) "
				+ "where R between ? and ?";

		PreparedStatement st = con.prepareStatement(sql);

		st.setString(1, "%" + rowNumber.getSearch().getSearch() + "%");
		st.setInt(2, rowNumber.getStartRow());
		st.setInt(3, rowNumber.getLastRow());

		ResultSet rs = st.executeQuery();
		List<BoardDTO> ar = new ArrayList<>();
		QnaDTO qnaDTO = null;
		while (rs.next()) {
			qnaDTO = new QnaDTO();
			qnaDTO.setNum(rs.getInt("num"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setWriter(rs.getString("writer"));
			qnaDTO.setReg_date(rs.getDate("reg_date"));
			qnaDTO.setHit(rs.getInt("hit"));
			qnaDTO.setDepth(rs.getInt("depth"));
			ar.add(qnaDTO);
		}
		DBConnector.disConnect(rs, st, con);
		return ar;
	}

	@Override
	public BoardDTO selectOne(int num) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "select * from qna where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		QnaDTO noticeDTO = null;
		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			noticeDTO = new QnaDTO();
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

	@Override
	public int insert(BoardDTO boardDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "insert into qna valuse(qna_seq.nextval,?,?,?,sysdate,0,qna_seq.currcal,0,0)";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, boardDTO.getTitle());
		st.setString(2, boardDTO.getContents());
		st.setString(3, boardDTO.getWriter());
		int result = st.executeUpdate();

		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public int update(BoardDTO boardDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "update qna set title=?, contents=?, writer=? where num=?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, boardDTO.getTitle());
		st.setString(2, boardDTO.getContents());
		st.setString(3, boardDTO.getWriter());
		st.setInt(4, boardDTO.getNum());
		int result = st.executeUpdate();

		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public int delete(int num) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "delete qna where num=?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		int result = st.executeUpdate();

		DBConnector.disConnect(st, con);
		return result;
	}

	@Override
	public int getCount(Search search) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "select count(num) from qna where " + search.getKind() + " like ?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, "%" + search.getSearch() + "%");

		ResultSet rs = st.executeQuery();
		rs.next();
		int result = rs.getInt(1);

		DBConnector.disConnect(rs, st, con);

		return result;
	}

}
