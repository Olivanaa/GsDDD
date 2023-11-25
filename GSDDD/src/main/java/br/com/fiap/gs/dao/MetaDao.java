package br.com.fiap.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import br.com.fiap.gs.models.Meta;

public class MetaDao {

	private Connection conexao;

	public void inserir(Meta meta) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			comandoSql = conexao.prepareStatement("insert into META360(ID_META, ID_USUARIO, MENSSAGEM) " + "values (?,?,?)");
			comandoSql.setInt(1, meta.getIdMeta());
			comandoSql.setInt(2, meta.getUsuario().getId());
			comandoSql.setString(3, meta.getMensagem());
			
			comandoSql.executeUpdate();
			conexao.close();
			comandoSql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DateTimeParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void alterar(Meta meta) {
		try {
			conexao = GerenciadorBD.obterConexao();
			PreparedStatement comandoSql = null;
			String sql = "update META360 set MENSSAGEM = ? where ID_META = ?";

			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setString(1, meta.getMensagem());			

			comandoSql.executeUpdate();
			conexao.close();
			comandoSql.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Meta buscarPorId(int id) {
		Meta meta = new Meta();
		UsuarioDao uDao = new UsuarioDao();
		PreparedStatement comandoSql = null;

		String sql = "Select * from META360 where ID_META = ?";
		conexao = GerenciadorBD.obterConexao();
		try {
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			if (rs.next()) {
				meta.setIdMeta(rs.getInt(1));
				int idU = rs.getInt(2);
				meta.setUsuario(uDao.buscarPorId(idU));
				meta.setMensagem(rs.getString(3));				

			}
			comandoSql.close();
			conexao.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return meta;
	}

	public void remover(int id) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSQL = null;
		try {
			comandoSQL = conexao.prepareStatement("delete from META360 where ID_META = ?");
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
			String sql = "select Max(ID_META) from META360";
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

	public ArrayList<Meta> buscarPorIdUser(int id) {
		ArrayList<Meta> listaMeta = new ArrayList<Meta>();
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			String sql = "select ID_META, MENSSAGEM from META360 where ID_USUARIO = ?";
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			while (rs.next()) {
				Meta meta = new Meta();
				meta.setIdMeta(rs.getInt(1));
				meta.setMensagem(rs.getString(2));
				listaMeta.add(meta);
			}

			comandoSql.close();
			conexao.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return listaMeta;
	}

}
