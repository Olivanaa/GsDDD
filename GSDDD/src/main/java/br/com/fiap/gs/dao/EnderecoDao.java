package br.com.fiap.gs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.gs.models.Endereco;

public class EnderecoDao {

	private Connection conexao = null;

	public void inserir(Endereco endereco) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			String sql = "insert into t_end360(idEnd, cep, logradouro, numero, complemento, bairro, cidade, estado)"
					+ " VALUES (?,?,?,?,?,?,?,?)";
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, endereco.getId());
			comandoSql.setString(2, endereco.getCep());
			comandoSql.setString(3, endereco.getLogradouro());
			comandoSql.setString(4, endereco.getNumero());
			comandoSql.setString(5, endereco.getComplemento());
			comandoSql.setString(6, endereco.getBairro());
			comandoSql.setString(7, endereco.getLocalidade());
			comandoSql.setString(8, endereco.getUf());

			comandoSql.executeUpdate();
			conexao.close();
			comandoSql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int buscarMaiorId() {
		int id = 0;
		try {
			conexao = GerenciadorBD.obterConexao();
			PreparedStatement comandoSql = null;
			String sql = "select Max(idEnd) from t_end360";
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

	public void alterar(Endereco endereco) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;

		String sql = "update t_end360 set cep = ?, logradouro = ?, numero = ?, complemento = ?, "
				+ "bairro = ?, cidade = ?, estado = ? where idEnd = ?";
		try {
			comandoSql = conexao.prepareStatement(sql);

			comandoSql.setString(1, endereco.getCep());
			comandoSql.setString(2, endereco.getLogradouro());
			comandoSql.setString(3, endereco.getNumero());
			comandoSql.setString(4, endereco.getComplemento());
			comandoSql.setString(5, endereco.getBairro());
			comandoSql.setString(6, endereco.getLocalidade());
			comandoSql.setString(7, endereco.getUf());
			comandoSql.setInt(8, endereco.getId());
			comandoSql.executeUpdate();
			conexao.close();
			comandoSql.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public Endereco buscarPorId(int id) {
		Endereco endereco = new Endereco();
		PreparedStatement comandosql = null;
		try {
			String sql = "select * from t_end360 where idEnd = ?";
			conexao = GerenciadorBD.obterConexao();
			comandosql = conexao.prepareStatement(sql);
			comandosql.setInt(1, id);
			ResultSet rs = comandosql.executeQuery();
			if (rs.next()) {
				endereco.setId(rs.getInt(1));
				endereco.setCep(rs.getString(2));
				endereco.setLogradouro(rs.getString(3));
				endereco.setNumero(rs.getString(4));
				endereco.setComplemento(rs.getString(5));
				endereco.setBairro(rs.getString(6));
				endereco.setLocalidade(rs.getString(7));
				endereco.setUf(rs.getString(8));

				comandosql.close();
				conexao.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return endereco;
	}

}
