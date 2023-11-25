package br.com.fiap.gs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import br.com.fiap.gs.enums.TipoAtividadeEnum;
import br.com.fiap.gs.models.AtividadeFisica;
import br.com.fiap.gs.models.Sono;

public class AtividaFisicaDao {

	private Connection conexao;

	public void inserir(AtividadeFisica atvF) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			comandoSql = conexao.prepareStatement("insert into ATIVIDADE_FISICA360(ID_ATIVIDADE, ID_USUARIO, "
					+ "TIPO_ATIVIDADE, DURACAO, DATA_ATIVIDADE) " + "values (?,?,?,?,?)");
			comandoSql.setInt(1, atvF.getIdAF());
			comandoSql.setInt(2, atvF.getUsuario().getId());
			comandoSql.setString(3, atvF.getTipo().name());
			comandoSql.setDouble(4, atvF.getDuracao());
			comandoSql.setDate(5, Date.valueOf(atvF.validaData(atvF.getDataAtiv())));
			

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

	public void alterar(AtividadeFisica atvF) {
		try {
			conexao = GerenciadorBD.obterConexao();
			PreparedStatement comandoSql = null;
			String sql = "update ATIVIDADE_FISICA360 set TIPO_ATIVIDADE = ?, DURACAO = ?, DATA_ATIVIDADE = ? where ID_ATIVIDADE = ?";

			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setString(1, atvF.getTipo().name());
			comandoSql.setDouble(2, atvF.getDuracao());
			comandoSql.setDate(3, Date.valueOf(atvF.validaData(atvF.getDataAtiv())));
			comandoSql.setInt(4, atvF.getIdAF());

			comandoSql.executeUpdate();
			conexao.close();
			comandoSql.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public AtividadeFisica buscarPorId(int id) {
		AtividadeFisica atvF = new AtividadeFisica();
		UsuarioDao uDao = new UsuarioDao();
		PreparedStatement comandoSql = null;

		String sql = "Select * from ATIVIDADE_FISICA360 where ID_ATIVIDADE = ?";
		conexao = GerenciadorBD.obterConexao();
		try {
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			if (rs.next()) {
				atvF.setIdAF(rs.getInt(1));
				int idU = rs.getInt(2);
				atvF.setUsuario(uDao.buscarPorId(idU));
				String tipo = rs.getString(3);
				TipoAtividadeEnum tipoEnum = TipoAtividadeEnum.valueOf(tipo);
				atvF.setTipo(tipoEnum);
				atvF.setDuracao(rs.getDouble(4));
				Date dta = rs.getDate(5);
				atvF.setDataAtiv(dta.toString());
				

			}
			comandoSql.close();
			conexao.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return atvF;
	}

	public void remover(int id) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSQL = null;
		try {
			comandoSQL = conexao.prepareStatement("delete from ATIVIDADE_FISICA360 where ID_ATIVIDADE = ?");
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
			String sql = "select Max(ID_ATIVIDADE) from ATIVIDADE_FISICA360";
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

	public ArrayList<AtividadeFisica> buscarPorIdUser(int id) {
		ArrayList<AtividadeFisica> listaAtiv = new ArrayList<AtividadeFisica>();
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			String sql = "select ID_ATIVIDADE, TIPO_ATIVIDADE, DURACAO, DATA_ATIVIDADE from ATIVIDADE_FISICA360 where ID_USUARIO = ?";
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			while (rs.next()) {
				AtividadeFisica atvF = new AtividadeFisica();
				atvF.setIdAF(rs.getInt(1));
				String tipo = rs.getString(2);
				TipoAtividadeEnum tipoEnum = TipoAtividadeEnum.valueOf(tipo);
				atvF.setTipo(tipoEnum);
				atvF.setDuracao(rs.getDouble(3));
				Date dta = rs.getDate(4);
				atvF.setDataAtiv(dta.toString());
				listaAtiv.add(atvF);
			}

			comandoSql.close();
			conexao.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return listaAtiv;
	}
	
	public ArrayList<AtividadeFisica> RegistroAtividade(int id){
		ArrayList<AtividadeFisica> listaRegistros = new ArrayList<AtividadeFisica>();
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			String sql = "select DURACAO from ATIVIDADE_FISICA360 where ID_USUARIO = ?";
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			while (rs.next()) {
				AtividadeFisica atv = new AtividadeFisica();
				atv.setDuracao(rs.getDouble(1));
				listaRegistros.add(atv);
			}

			comandoSql.close();
			conexao.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return listaRegistros;
	}
	
	public AtividadeFisica buscarUltimoRegistro(int idUsuario) {
	    AtividadeFisica aiv = null;

	    UsuarioDao uDao = new UsuarioDao();
	    String sql = "SELECT ID_ATIVIDADE, TIPO_ATIVIDADE, DURACAO, DATA_ATIVIDADE FROM ATIVIDADE_FISICA360 WHERE ID_USUARIO = ? ORDER BY DATA_ATIVIDADE DESC FETCH FIRST 1 ROW ONLY";

	    try (Connection conexao = GerenciadorBD.obterConexao();
	         PreparedStatement comandoSql = conexao.prepareStatement(sql)) {

	        comandoSql.setInt(1, idUsuario);
	        ResultSet rs = comandoSql.executeQuery();

	        if (rs.next()) {
	            aiv = new AtividadeFisica();
	            aiv.setIdAF(rs.getInt("ID_ATIVIDADE"));
	            String tipo = rs.getString("TIPO_ATIVIDADE");
	            TipoAtividadeEnum tipoEnum = TipoAtividadeEnum.valueOf(tipo.toUpperCase());
	            aiv.setTipo(tipoEnum);
	            aiv.setDuracao(rs.getDouble("DURACAO"));
	            Date dta = rs.getDate("DATA_ATIVIDADE");
	            aiv.setDataAtiv(dta.toString());
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return aiv;
	}

}
