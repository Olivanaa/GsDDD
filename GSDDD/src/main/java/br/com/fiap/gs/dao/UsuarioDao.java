package br.com.fiap.gs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

import br.com.fiap.gs.enums.ObjetivoEnum;
import br.com.fiap.gs.models.Endereco;
import br.com.fiap.gs.models.Usuario;

public class UsuarioDao {

	private Connection conexao;

	public void inserir(Usuario usuario) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			comandoSql = conexao.prepareStatement("insert into USUARIO360(ID_USUARIO, NOME_USUARIO, "
					+ "CPF_USUARIO, IDADE_USUARIO, SEXO_USUARIO, EMAIL_USUARIO, SENHA_USUARIO, OBJETIVO_USUARIO) "
					+ "values (?,?,?,?,?,?,?,?)");
			comandoSql.setInt(1, usuario.getId());
			comandoSql.setString(2, usuario.getNome());
			comandoSql.setString(3, usuario.getCpf());
			comandoSql.setInt(4, usuario.getIdade());
			comandoSql.setString(5, usuario.getGenero());
			comandoSql.setString(6, usuario.getEmail());
			String senhaCripto = Usuario.senhaCripto(usuario.getSenha());
			comandoSql.setString(7, senhaCripto);
			String objetivo = (usuario.getObjetivo() == ObjetivoEnum.UM) ? "OBJETIVO 1"
					: (usuario.getObjetivo() == ObjetivoEnum.DOIS) ? "OBJETIVO 2"
							: (usuario.getObjetivo() == ObjetivoEnum.TRES) ? "OBJETIVO 3" : "OBJETIVO 4";
			comandoSql.setString(8, objetivo);			

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

	public int buscarMaiorId() {
		int id = 0;
		try {
			conexao = GerenciadorBD.obterConexao();
			PreparedStatement comandoSql = null;
			String sql = "select Max(ID_USUARIO) from USUARIO360";
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

	public boolean validarUsuario(String email, String senha) {
		Usuario user = new Usuario();
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSQL = null;
		try {
			comandoSQL = conexao.prepareStatement("select * from USUARIO360 where EMAIL_USUARIO = ? ");
			comandoSQL.setString(1, email);
			ResultSet rs = comandoSQL.executeQuery();
			if (rs.next()) {
				if (user.verificaSenha(senha, rs.getString(7)))
					return true;
			}
			conexao.close();
			comandoSQL.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean verificaLogin(String email) {
	    try (Connection conexao = GerenciadorBD.obterConexao();
	         PreparedStatement comandoSQL = conexao.prepareStatement("SELECT * FROM USUARIO360 WHERE EMAIL_USUARIO = ?")) {

	        comandoSQL.setString(1, email);
	        ResultSet rs = comandoSQL.executeQuery();

	        return rs.next();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; 
	    }
	}


	public int buscarIdLogin(String email) throws SQLException {
		Usuario user = new Usuario();
		PreparedStatement comandosql = null;

		String sql = "select ID_USUARIO from USUARIO360 where EMAIL_USUARIO = ?";
		conexao = GerenciadorBD.obterConexao();
		comandosql = conexao.prepareStatement(sql);
		comandosql.setString(1, email);
		ResultSet rs = comandosql.executeQuery();
		if (rs.next()) {
			user.setId(rs.getInt(1));
			comandosql.close();
			conexao.close();
		}
		return user.getId();

	}

	public void alterar(Usuario usuario) {
        conexao = GerenciadorBD.obterConexao();
        PreparedStatement comandoSql = null;
        try {
            String sql = "update USUARIO360 set NOME_USUARIO = ?, CPF_USUARIO = ?," +
                    "IDADE_USUARIO = ?, SEXO_USUARIO = ?,OBJETIVO_USUARIO = ?" +
                    "where ID_USUARIO = ?";

            comandoSql = conexao.prepareStatement(sql);
			comandoSql.setString(1, usuario.getNome());
			comandoSql.setString(2, usuario.getCpf());
			comandoSql.setInt(3, usuario.getIdade());
			comandoSql.setString(4, usuario.getGenero());
			String objetivo = (usuario.getObjetivo() == ObjetivoEnum.UM) ? "OBJETIVO 1"
					: (usuario.getObjetivo() == ObjetivoEnum.DOIS) ? "OBJETIVO 2"
							: (usuario.getObjetivo() == ObjetivoEnum.TRES) ? "OBJETIVO 3" : "OBJETIVO 4";
			comandoSql.setString(5, objetivo);
			comandoSql.setInt(6, usuario.getId());
            comandoSql.executeUpdate();
            conexao.close();
            comandoSql.close();
        }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public Usuario buscarPorId(int id) throws SQLException {
		Usuario user = new Usuario();
		PreparedStatement comandoSql = null;
		conexao = GerenciadorBD.obterConexao();
		try {
			String sql = "Select * from USUARIO360 where ID_USUARIO = ?";
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			if (rs.next()) {
				user.setId(rs.getInt(1));
				user.setNome(rs.getString(2));
				user.setCpf(rs.getString(3));
				user.setIdade(rs.getInt(4));
				user.setGenero(rs.getString(5));				
				user.setEmail(rs.getString(6));
				user.setSenha(rs.getString(7));
			
				String obj = rs.getString(8);

				ObjetivoEnum objetivoEnum;

				switch (obj) {
				case "OBJETIVO 1":
					objetivoEnum = ObjetivoEnum.UM;
					break;
				case "OBJETIVO 2":
					objetivoEnum = ObjetivoEnum.DOIS;
					break;
				case "OBJETIVO 3":
					objetivoEnum = ObjetivoEnum.TRES;
					break;
				case "OBJETIVO 4":
					objetivoEnum = ObjetivoEnum.QUATRO;
					break;
				default:
					objetivoEnum = null;
					break;
				}

				user.setObjetivo(objetivoEnum);

			}
			comandoSql.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public ObjetivoEnum buscarObjetivoUsuario(int id) {
	    ObjetivoEnum objetivoEnum = null;
	    PreparedStatement comandoSql = null;
	    conexao = GerenciadorBD.obterConexao();
	    
	    try {
	        String sql = "SELECT OBJETIVO_USUARIO FROM USUARIO360 WHERE ID_USUARIO = ?";
	        comandoSql = conexao.prepareStatement(sql);
	        comandoSql.setInt(1, id);
	        ResultSet rs = comandoSql.executeQuery();

	        if (rs.next()) {
	            String objetivo = rs.getString(1);

	            switch (objetivo) {
	                case "OBJETIVO 1":
	                    objetivoEnum = ObjetivoEnum.UM;
	                    break;
	                case "OBJETIVO 2":
	                    objetivoEnum = ObjetivoEnum.DOIS;
	                    break;
	                case "OBJETIVO 3":
	                    objetivoEnum = ObjetivoEnum.TRES;
	                    break;
	                case "OBJETIVO 4":
	                    objetivoEnum = ObjetivoEnum.QUATRO;
	                    break;
	                default:
	                    objetivoEnum = null;
	                    break;
	            }
	        }

	        comandoSql.close();
	        conexao.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return objetivoEnum;
	}

//	public int buscarIdEnd(int idUser) throws SQLException {
//		int idEndereco = 0;
//
//		PreparedStatement comandosql = null;
//		String sql = "SELECT idEnd FROM USUARIO360 WHERE ID_USUARIO = ?";
//
//		conexao = GerenciadorBD.obterConexao();
//
//		try {
//			comandosql = conexao.prepareStatement(sql);
//			comandosql.setInt(1, idUser);
//			ResultSet rs = comandosql.executeQuery();
//
//			if (rs.next()) {
//				idEndereco = rs.getInt("idEnd");
//			}
//
//			comandosql.close();
//			conexao.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return idEndereco;
//	}
	
	public int buscarOId(int id) {
		int idUser = 0;

		PreparedStatement comandosql = null;
		String sql = "SELECT ID_USUARIO FROM USUARIO360 WHERE ID_USUARIO = ?";

		conexao = GerenciadorBD.obterConexao();

		try {
			comandosql = conexao.prepareStatement(sql);
			comandosql.setInt(1, idUser);
			ResultSet rs = comandosql.executeQuery();

			if (rs.next()) {
				idUser = rs.getInt("ID_USUARIO");
			}

			comandosql.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return idUser;
	}
		
	
	}

//
//public void inserir(Usuario usuario) {
//	conexao = GerenciadorBD.obterConexao();
//	PreparedStatement comandoSql = null;
//	try {
//		comandoSql = conexao.prepareStatement("insert into t_usuario360(idUser, nome, "
//				+ "cpf, genero, dtaNasc, telefone, email, senha, idade, idEnd, objetivo) "
//				+ "values (?,?,?,?,?,?,?,?,?,?,?)");
//		comandoSql.setInt(1, usuario.getId());
//		comandoSql.setString(2, usuario.getNome());
//		comandoSql.setString(3, usuario.getCpf());
//		comandoSql.setString(4, usuario.getGenero());
//		comandoSql.setDate(5, Date.valueOf(usuario.validaData(usuario.getDtaNasc())));
//		comandoSql.setString(6, usuario.getTelefone());
//		comandoSql.setString(7, usuario.getEmail());
//		String senhaCripto = Usuario.senhaCripto(usuario.getSenha());
//		comandoSql.setString(8, senhaCripto);
//		comandoSql.setInt(9, usuario.calculaIdade(usuario.validaData(usuario.getDtaNasc())));
//		comandoSql.setInt(10, usuario.getEndereco().getId());
//		String objetivo = (usuario.getObjetivo() == ObjetivoEnum.UM) ? "OBJETIVO 1"
//				: (usuario.getObjetivo() == ObjetivoEnum.DOIS) ? "OBJETIVO 2"
//						: (usuario.getObjetivo() == ObjetivoEnum.TRES) ? "OBJETIVO 3" : "OBJETIVO 4";
//		comandoSql.setString(12, objetivo);
//
//		comandoSql.executeUpdate();
//		conexao.close();
//		comandoSql.close();
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (DateTimeParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//
//}
//
//public int buscarMaiorId() {
//	int id = 0;
//	try {
//		conexao = GerenciadorBD.obterConexao();
//		PreparedStatement comandoSql = null;
//		String sql = "select Max(idUser) from t_usuario360";
//		comandoSql = conexao.prepareStatement(sql);
//		ResultSet rs = comandoSql.executeQuery();
//		if (rs.next()) {
//			id = rs.getInt(1);
//		}
//		comandoSql.close();
//		conexao.close();
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	return id + 1;
//}
//
//public boolean validarUsuario(String email, String senha) {
//	Usuario user = new Usuario();
//	conexao = GerenciadorBD.obterConexao();
//	PreparedStatement comandoSQL = null;
//	try {
//		comandoSQL = conexao.prepareStatement("select * from t_usuario360 where email = ? ");
//		comandoSQL.setString(1, email);
//		ResultSet rs = comandoSQL.executeQuery();
//		if (rs.next()) {
//			if (user.verificaSenha(senha, rs.getString(9)))
//				return true;
//		}
//		conexao.close();
//		comandoSQL.close();
//
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	return false;
//}
//
//public boolean verificaLogin(String email) {
//	conexao = GerenciadorBD.obterConexao();
//	PreparedStatement comandoSQL = null;
//	try {
//		String sql = "select * from t_usuario360 where email = ? ";
//		comandoSQL = conexao.prepareStatement(sql);
//		comandoSQL.setString(1, email);
//		ResultSet rs = comandoSQL.executeQuery();
//		if (rs.next()) {
//			return true;
//		}
//
//		conexao.close();
//		comandoSQL.close();
//
//	} catch (SQLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//
//	}
//
//	return false;
//}
//
//public int buscarIdLogin(String email) throws SQLException {
//	Usuario user = new Usuario();
//	PreparedStatement comandosql = null;
//
//	String sql = "select iduser from t_usuario360 where email = ?";
//	conexao = GerenciadorBD.obterConexao();
//	comandosql = conexao.prepareStatement(sql);
//	comandosql.setString(1, email);
//	ResultSet rs = comandosql.executeQuery();
//	if (rs.next()) {
//		user.setId(rs.getInt(1));
//		comandosql.close();
//		conexao.close();
//	}
//	return user.getId();
//
//}
//
//public void alterar(Usuario usuario, Endereco endereco) {
//	Connection conexao = null;
//	PreparedStatement comandoSqlUsuario = null;
//	PreparedStatement comandoSqlEndereco = null;
//	UsuarioDao dao = new UsuarioDao();
//	try {
//		conexao = GerenciadorBD.obterConexao();
//
//		conexao.setAutoCommit(false);
//
//		String sqlUsuario = "UPDATE t_usuario360 SET nome=?, genero=?, telefone=?, objetivo =? WHERE idUser=?";
//		comandoSqlUsuario = conexao.prepareStatement(sqlUsuario);
//		comandoSqlUsuario.setString(1, usuario.getNome());
//		comandoSqlUsuario.setString(2, usuario.getGenero());
//		comandoSqlUsuario.setString(3, usuario.getTelefone());
//		comandoSqlUsuario.setInt(4, usuario.getId());
//		String objetivo = (usuario.getObjetivo() == ObjetivoEnum.UM) ? "OBJETIVO 1"
//				: (usuario.getObjetivo() == ObjetivoEnum.DOIS) ? "OBJETIVO 2"
//						: (usuario.getObjetivo() == ObjetivoEnum.TRES) ? "OBJETIVO 3" : "OBJETIVO 4";
//		comandoSqlUsuario.setString(5, objetivo);
//		comandoSqlUsuario.executeUpdate();
//
//		String sqlEndereco = "UPDATE t_end360 SET cep=?, logradouro=?, numero=?, complemento=?, bairro=?, cidade=?, estado=? WHERE idEnd=?";
//		comandoSqlEndereco = conexao.prepareStatement(sqlEndereco);
//		comandoSqlEndereco.setString(1, endereco.getCep());
//		comandoSqlEndereco.setString(2, endereco.getLogradouro());
//		comandoSqlEndereco.setString(3, endereco.getNumero());
//		comandoSqlEndereco.setString(4, endereco.getComplemento());
//		comandoSqlEndereco.setString(5, endereco.getBairro());
//		comandoSqlEndereco.setString(6, endereco.getLocalidade());
//		comandoSqlEndereco.setString(7, endereco.getUf());
//		comandoSqlEndereco.setInt(8, dao.buscarIdEnd(usuario.getId()));
//		comandoSqlEndereco.executeUpdate();
//
//		conexao.commit();
//		comandoSqlEndereco.close();
//		comandoSqlUsuario.close();
//		conexao.close();
//	} catch (SQLException e) {
//		throw new RuntimeException(e);
//	}
//
//}
//
//public Usuario buscarPorId(int id) throws SQLException {
//	Usuario user = new Usuario();
//	EnderecoDao eDao = new EnderecoDao();
//	PreparedStatement comandoSql = null;
//	conexao = GerenciadorBD.obterConexao();
//	try {
//		String sql = "Select * from t_usuario360 where idUser = ?";
//		comandoSql = conexao.prepareStatement(sql);
//		comandoSql.setInt(1, id);
//		ResultSet rs = comandoSql.executeQuery();
//		if (rs.next()) {
//			user.setId(rs.getInt(1));
//			user.setNome(rs.getString(2));
//			user.setCpf(rs.getString(3));
//			user.setGenero(rs.getString(4));
//			Date dataNsc = rs.getDate(5);
//			user.setDtaNasc(dataNsc.toString());
//			user.setTelefone(rs.getString(6));
//			user.setEmail(rs.getString(7));
//			user.setSenha(rs.getString(8));
//			user.setIdade(rs.getString(9));
//			int idEnd = rs.getInt(10);
//			user.setEndereco(eDao.buscarPorId(idEnd));
//
//			String obj = rs.getString(12);
//
//			ObjetivoEnum objetivoEnum;
//
//			switch (obj) {
//			case "OBJETIVO 1":
//				objetivoEnum = ObjetivoEnum.UM;
//				break;
//			case "OBJETIVO 2":
//				objetivoEnum = ObjetivoEnum.DOIS;
//				break;
//			case "OBJETIVO 3":
//				objetivoEnum = ObjetivoEnum.TRES;
//				break;
//			case "OBJETIVO 4":
//				objetivoEnum = ObjetivoEnum.QUATRO;
//				break;
//			default:
//				objetivoEnum = null;
//				break;
//			}
//
//			user.setObjetivo(objetivoEnum);
//
//		}
//		comandoSql.close();
//		conexao.close();
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	return user;
//}
//
//public int buscarIdEnd(int idUser) throws SQLException {
//	int idEndereco = 0;
//
//	PreparedStatement comandosql = null;
//	String sql = "SELECT idEnd FROM t_usuario360 WHERE idUser = ?";
//
//	conexao = GerenciadorBD.obterConexao();
//
//	try {
//		comandosql = conexao.prepareStatement(sql);
//		comandosql.setInt(1, idUser);
//		ResultSet rs = comandosql.executeQuery();
//
//		if (rs.next()) {
//			idEndereco = rs.getInt("idEnd");
//		}
//
//		comandosql.close();
//		conexao.close();
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//
//	return idEndereco;
//}
//
//public int buscarOId(int id) {
//	int idUser = 0;
//
//	PreparedStatement comandosql = null;
//	String sql = "SELECT idUser FROM t_usuario360 WHERE idUser = ?";
//
//	conexao = GerenciadorBD.obterConexao();
//
//	try {
//		comandosql = conexao.prepareStatement(sql);
//		comandosql.setInt(1, idUser);
//		ResultSet rs = comandosql.executeQuery();
//
//		if (rs.next()) {
//			idUser = rs.getInt("idUser");
//		}
//
//		comandosql.close();
//		conexao.close();
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//
//	return idUser;
//}

