package br.com.fiap.gs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import br.com.fiap.gs.models.ExameMedico;
import br.com.fiap.gs.models.Sono;

public class SonoDao {

	private Connection conexao;

	public void inserir(Sono sono) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			comandoSql = conexao.prepareStatement(
					"insert into SONO360(ID_SONO, ID_USUARIO, META_SONO, DATA_REGISTRO, DURACAO_SONO) values (?,?,?,?,?)");
			comandoSql.setInt(1, sono.getIdSono());
			comandoSql.setInt(2, sono.getUsuario().getId());
			comandoSql.setDouble(3, sono.getMeta());
			comandoSql.setDate(4, Date.valueOf(sono.validaData(sono.getDataSono())));
			comandoSql.setDouble(5, sono.getDuracao());	
					

			comandoSql.executeUpdate();
			conexao.close();
			comandoSql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DateTimeParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void alterar(Sono sono) {
		try {
			conexao = GerenciadorBD.obterConexao();
			PreparedStatement comandoSql = null;
			String sql = "update SONO360 set META_SONO = ?, DATA_REGISTRO = ?, DURACAO_SONO = ? where ID_SONO = ?";

			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, sono.getIdSono());
			comandoSql.setDouble(2, sono.getMeta());
			comandoSql.setDate(3, Date.valueOf(sono.validaData(sono.getDataSono())));
			comandoSql.setDouble(4, sono.getDuracao());	

			comandoSql.executeUpdate();
			conexao.close();
			comandoSql.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DateTimeParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Sono buscarPorId(int id) {
		Sono sono = new Sono();
		UsuarioDao uDao = new UsuarioDao();
		PreparedStatement comandoSql = null;

		String sql = "Select * from SONO360 where ID_SONO = ?";
		conexao = GerenciadorBD.obterConexao();
		try {
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			if (rs.next()) {
				sono.setIdSono(rs.getInt(1));
				int idU = rs.getInt(2);
				sono.setUsuario(uDao.buscarPorId(idU));
				sono.setMeta(rs.getDouble(3));
				Date dta = rs.getDate(4);
				sono.setDataSono(dta.toString());
				sono.setDuracao(rs.getDouble(5));		
						
			}
			comandoSql.close();
			conexao.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return sono;
	}

	public void remover(int id) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSQL = null;
		try {
			comandoSQL = conexao.prepareStatement("delete from SONO360 where ID_SONO = ?");
			comandoSQL.setInt(1, id);
			comandoSQL.executeUpdate();
			conexao.close();
			comandoSQL.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int buscarMaiorId() {
		int id = 0;
		try {
			conexao = GerenciadorBD.obterConexao();
			PreparedStatement comandoSql = null;
			String sql = "select Max(ID_SONO) from SONO360";
			comandoSql = conexao.prepareStatement(sql);
			ResultSet rs = comandoSql.executeQuery();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			comandoSql.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id + 1;
	}
	
	public ArrayList<Sono> buscarPorIdUser(int id) {
		ArrayList<Sono> listaSonos = new ArrayList<Sono>();
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			String sql = "select ID_SONO, META_SONO, DATA_REGISTRO, DURACAO_SONO from SONO360 where ID_USUARIO = ?";
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			while (rs.next()) {
				Sono sono = new Sono();
				sono.setIdSono(rs.getInt(1));
				sono.setMeta(rs.getDouble(2));
			    LocalDate dataSono = rs.getDate(3).toLocalDate();
			    sono.setDataSono(dataSono.toString());
			    sono.setDuracao(rs.getDouble(4));
				listaSonos.add(sono);
			}

			comandoSql.close();
			conexao.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DateTimeParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaSonos;
	}

	
	public ArrayList<Sono> RegistroSono(int id){
		ArrayList<Sono> listaRegistros = new ArrayList<Sono>();
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			String sql = "select DURACAO_SONO from SONO360 where ID_USUARIO = ?";
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			while (rs.next()) {
				Sono sono = new Sono();
				sono.setDuracao(rs.getDouble(1));
				listaRegistros.add(sono);
			}

			comandoSql.close();
			conexao.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return listaRegistros;
	}
	
	public Sono buscarUltimoRegistro(int idUsuario) {
	    Sono sono = null;

	    UsuarioDao uDao = new UsuarioDao();
	    String sql = "SELECT ID_SONO, DURACAO_SONO, DATA_REGISTRO, META_SONO FROM SONO360 WHERE ID_USUARIO = ? ORDER BY DATA_REGISTRO DESC FETCH FIRST 1 ROW ONLY";

	    try (Connection conexao = GerenciadorBD.obterConexao();
	         PreparedStatement comandoSql = conexao.prepareStatement(sql)) {

	        comandoSql.setInt(1, idUsuario);
	        ResultSet rs = comandoSql.executeQuery();

	        if (rs.next()) {
	            sono = new Sono();
	            sono.setIdSono(rs.getInt("ID_SONO"));
	            sono.setDuracao(rs.getDouble("DURACAO_SONO"));
	            Date dta = rs.getDate("DATA_REGISTRO");
	            sono.setDataSono(dta.toString());
	            sono.setMeta(rs.getDouble("META_SONO"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return sono;
	}

}
