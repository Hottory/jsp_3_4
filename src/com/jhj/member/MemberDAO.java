package com.jhj.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jhj.util.DBConnector;

public class MemberDAO {
	public MemberDTO login(MemberDTO memberDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "select id, pw, name, email, kind, m.classMate, grade, ban, fname, oname " + "from member m "
				+ "LEFT JOIN team t on (m.classmate = t.classmate) where id=? and pw=? and kind=?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		st.setString(2, memberDTO.getPw());
		st.setString(3, memberDTO.getKind());
		ResultSet rs = st.executeQuery();

		if (rs.next()) {
			memberDTO.setName(rs.getString("name"));
			memberDTO.setEmail(rs.getString("email"));
			memberDTO.setClassMate(rs.getString("classMate"));
			memberDTO.setFname(rs.getString("fname"));
			memberDTO.setOname(rs.getString("oname"));
			memberDTO.setGrade(rs.getInt("grade"));
			memberDTO.setBan(rs.getInt("ban"));
		} else {
			memberDTO = null;
		}

		DBConnector.disConnect(rs, st, con);
		return memberDTO;
	}

	public MemberDTO logout(MemberDTO memberDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "select id, pw, name, email, kind, m.classMate, grade, ban from member m "
				+ "LEFT JOIN team t on (m.classmate = t.classmate) where id=? and pw=?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		st.setString(2, memberDTO.getPw());
		ResultSet rs = st.executeQuery();
		if (rs.next()) {

		}
		DBConnector.disConnect(rs, st, con);
		return memberDTO;
	}

	public int join(MemberDTO memberDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "insert into member values(?,?,?,?,?,?,?,?)";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getId());
		st.setString(2, memberDTO.getPw());
		st.setString(3, memberDTO.getName());
		st.setString(4, memberDTO.getEmail());
		st.setString(5, memberDTO.getKind());
		st.setString(6, memberDTO.getClassMate());
		st.setString(7, memberDTO.getFname());
		st.setString(8, memberDTO.getOname());
		int result = st.executeUpdate();

		DBConnector.disConnect(st, con);
		return result;
	}

	public int update(MemberDTO memberDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "update member set pw=?, name=?, email=?, fname=?, oname=? where id=?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, memberDTO.getPw());
		st.setString(2, memberDTO.getName());
		st.setString(3, memberDTO.getEmail());
		st.setString(4, memberDTO.getFname());
		st.setString(5, memberDTO.getOname());
		st.setString(6, memberDTO.getId());
		int result = st.executeUpdate();

		DBConnector.disConnect(st, con);
		return result;
	}

	public int delete(String id) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "delete member where id=?";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, id);
		int result = st.executeUpdate();

		DBConnector.disConnect(st, con);
		return result;
	}
}
