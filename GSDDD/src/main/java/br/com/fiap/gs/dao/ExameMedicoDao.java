package br.com.fiap.gs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import br.com.fiap.gs.models.ExameMedico;

public class ExameMedicoDao {

	private Connection conexao;

	public void inserir(ExameMedico exame) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			comandoSql = conexao.prepareStatement("insert into EXAME360(ID_EXAME, ID_USUARIO, "
					+ "NOME_EXAME, DATA_EXAME, HORARIO_EXAME) " + "values (?,?,?,?,?)");
			comandoSql.setInt(1, exame.getIdExame());
			comandoSql.setInt(2, exame.getUsuario().getId());
			comandoSql.setString(3, exame.getNomeExame());
			comandoSql.setDate(4, Date.valueOf(exame.validaData(exame.getDataExame())));
			comandoSql.setTime(5, Time.valueOf(exame.validaHorario(exame.getHorarioExame())));
	        
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

	public void alterar(ExameMedico exame) {
		try {
			conexao = GerenciadorBD.obterConexao();
			PreparedStatement comandoSql = null;
			String sql = "update EXAME360 set NOME_EXAME = ?, DATA_EXAME = ?, HORARIO_EXAME = ? where ID_EXAME = ?";

			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setString(1, exame.getNomeExame());
			comandoSql.setDate(3, Date.valueOf(exame.validaData(exame.getDataExame())));
			comandoSql.setTime(4, Time.valueOf(exame.validaHorario(exame.getHorarioExame())));
			comandoSql.setInt(4, exame.getIdExame());

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

	public ExameMedico buscarPorId(int id) {
		ExameMedico exame = new ExameMedico();
		UsuarioDao uDao = new UsuarioDao();
		PreparedStatement comandoSql = null;

		String sql = "Select * from EXAME360 where ID_EXAME = ?";
		conexao = GerenciadorBD.obterConexao();
		try {
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			if (rs.next()) {
				exame.setIdExame(rs.getInt(1));
				int idU = rs.getInt(2);
				exame.setUsuario(uDao.buscarPorId(idU));
				exame.setNomeExame(rs.getString(3));
			    LocalDate dataExame = rs.getDate(4).toLocalDate();
			    exame.setDataExame(dataExame.toString());
			    exame.setHorarioExame(rs.getTime(5).toLocalTime().toString());				

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

		return exame;
	}

	public void remover(int id) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSQL = null;
		try {
			comandoSQL = conexao.prepareStatement("delete from EXAME360 where ID_EXAME = ?");
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
			String sql = "select Max(ID_EXAME) from EXAME360";
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

	public ArrayList<ExameMedico> buscarPorIdUser(int id) {
		ArrayList<ExameMedico> listaExame = new ArrayList<ExameMedico>();
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			String sql = "select ID_EXAME, NOME_EXAME, DATA_EXAME, HORARIO_EXAME from EXAME360 where ID_USUARIO = ?";
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			while (rs.next()) {
				ExameMedico exame = new ExameMedico();
				exame.setIdExame(rs.getInt(1));
				exame.setNomeExame(rs.getString(2));
			    LocalDate dataExame = rs.getDate(3).toLocalDate();
			    exame.setDataExame(dataExame.toString());
			    exame.setHorarioExame(rs.getTime(4).toLocalTime().toString());
				listaExame.add(exame);
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

		return listaExame;
	}

}
