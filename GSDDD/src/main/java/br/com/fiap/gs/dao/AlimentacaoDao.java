package br.com.fiap.gs.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import br.com.fiap.gs.enums.TipoAlimentacaoEnum;
import br.com.fiap.gs.models.Alimentacao;
import br.com.fiap.gs.models.Sono;

public class AlimentacaoDao {

	private Connection conexao;
	
    private TipoAlimentacaoEnum getTipoAlimentacaoEnum(String tipo) {
        switch (tipo) {
            case "CAFÉ DA MANHÃ":
                return TipoAlimentacaoEnum.CAFE_DA_MANHA;
            case "ALMOÇO":
                return TipoAlimentacaoEnum.ALMOCO;
            case "JANTAR":
                return TipoAlimentacaoEnum.JANTAR;
            case "LANCHE DA MANHÃ":
                return TipoAlimentacaoEnum.LANCHE_DA_MANHA;
            case "LANCHE DA TARDE":
                return TipoAlimentacaoEnum.LANCHE_DA_TARDE;
            case "LANCHE DA NOITE":
                return TipoAlimentacaoEnum.LANCHE_DA_NOITE;
            default:
                return null;
        }
    }

	public void inserir(Alimentacao ali) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			comandoSql = conexao.prepareStatement("insert into ALIMENTACAO360(ID_ALIMENTACAO, ID_USUARIO, "
					+ "TIPO_REFEICAO, ALIMENTOS_CONSUMIDOS, DATA_ALIMENTACAO) " + "values (?,?,?,?,?)");
			comandoSql.setInt(1, ali.getIdAlim());
			comandoSql.setInt(2, ali.getUsuario().getId());
			String tipo = (ali.getTipo() == TipoAlimentacaoEnum.CAFE_DA_MANHA) ? "CAFÉ DA MANHÃ"
			        : (ali.getTipo() == TipoAlimentacaoEnum.ALMOCO) ? "ALMOÇO"
			        : (ali.getTipo() == TipoAlimentacaoEnum.JANTAR) ? "JANTAR"
			        : (ali.getTipo() == TipoAlimentacaoEnum.LANCHE_DA_MANHA) ? "LANCHE DA MANHÃ"
			        : (ali.getTipo() == TipoAlimentacaoEnum.LANCHE_DA_TARDE) ? "LANCHE DA TARDE"			        
			        : "LANCHE DA NOITE";

			comandoSql.setString(3, tipo);
			comandoSql.setString(4, ali.getAlimentosConsumidos());
			comandoSql.setDate(5, Date.valueOf(ali.validaData(ali.getDtaAlim())));
			

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

	public void alterar(Alimentacao ali) {
		try {
			conexao = GerenciadorBD.obterConexao();
			PreparedStatement comandoSql = null;
			String sql = "update ALIMENTACAO360 set TIPO_REFEICAO = ?, ALIMENTOS_CONSUMIDOS = ?, DATA_ALIMENTACAO = ? where ID_ALIMENTACAO = ?";

			comandoSql = conexao.prepareStatement(sql);
			String tipo = (ali.getTipo() == TipoAlimentacaoEnum.CAFE_DA_MANHA) ? "CAFÉ DA MANHÃ"
			        : (ali.getTipo() == TipoAlimentacaoEnum.ALMOCO) ? "ALMOÇO"
			        : (ali.getTipo() == TipoAlimentacaoEnum.JANTAR) ? "JANTAR"
			        : (ali.getTipo() == TipoAlimentacaoEnum.LANCHE_DA_MANHA) ? "LANCHE DA MANHÃ"
			        : (ali.getTipo() == TipoAlimentacaoEnum.LANCHE_DA_TARDE) ? "LANCHE DA TARDE"			        
			        : "LANCHE DA NOITE";

			comandoSql.setString(1, tipo);
			comandoSql.setString(2, ali.getAlimentosConsumidos());
			comandoSql.setDate(3, Date.valueOf(ali.validaData(ali.getDtaAlim())));
			comandoSql.setInt(4, ali.getIdAlim());

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

	public Alimentacao buscarPorId(int id) {
		Alimentacao ali = new Alimentacao();
		UsuarioDao uDao = new UsuarioDao();
		PreparedStatement comandoSql = null;

		String sql = "Select * from ALIMENTACAO360 where ID_ALIMENTACAO = ?";
		conexao = GerenciadorBD.obterConexao();
		try {
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			if (rs.next()) {
				ali.setIdAlim(rs.getInt(1));
				int idU = rs.getInt(2);
				ali.setUsuario(uDao.buscarPorId(idU));
				String tipo = rs.getString(3);
				TipoAlimentacaoEnum tipoEnum;

				switch (tipo) {
				case "CAFÉ DA MANHÃ":
					tipoEnum = TipoAlimentacaoEnum.CAFE_DA_MANHA;
					break;
				case "ALMOÇO":
					tipoEnum = TipoAlimentacaoEnum.ALMOCO;
					break;
				case "JANTAR":
					tipoEnum = TipoAlimentacaoEnum.JANTAR;
					break;
				case "LANCHE DA MANHÃ":
					tipoEnum = TipoAlimentacaoEnum.LANCHE_DA_MANHA;
					break;
				case "LANCHE DA TARDE":
					tipoEnum = TipoAlimentacaoEnum.LANCHE_DA_TARDE;
					break;
				case "LANCHE DA NOITE": 
					tipoEnum = TipoAlimentacaoEnum.LANCHE_DA_NOITE;
					break;
				default:
					tipoEnum = null;
					break;
				}

				ali.setTipo(tipoEnum);
				ali.setAlimentosConsumidos(rs.getString(4));
				Date dta = rs.getDate(5);
				ali.setDtaAlim(dta.toString());
				

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

		return ali;
	}

	public void remover(int id) {
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSQL = null;
		try {
			comandoSQL = conexao.prepareStatement("delete from ALIMENTACAO360 where ID_ALIMENTACAO = ?");
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
			String sql = "select Max(ID_ALIMENTACAO) from ALIMENTACAO360";
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

	public ArrayList<Alimentacao> buscarPorIdUser(int id) {
		ArrayList<Alimentacao> listaAlim = new ArrayList<Alimentacao>();
		conexao = GerenciadorBD.obterConexao();
		PreparedStatement comandoSql = null;
		try {
			String sql = "select ID_ALIMENTACAO, TIPO_REFEICAO, ALIMENTOS_CONSUMIDOS, DATA_ALIMENTACAO from ALIMENTACAO360 where ID_USUARIO = ?";
			comandoSql = conexao.prepareStatement(sql);
			comandoSql.setInt(1, id);
			ResultSet rs = comandoSql.executeQuery();
			while (rs.next()) {
				Alimentacao ali = new Alimentacao();
				ali.setIdAlim(rs.getInt(1));
				String tipo = rs.getString(2);
				TipoAlimentacaoEnum tipoEnum;

				switch (tipo) {
				case "CAFÉ DA MANHÃ":
					tipoEnum = TipoAlimentacaoEnum.CAFE_DA_MANHA;
					break;
				case "ALMOÇO":
					tipoEnum = TipoAlimentacaoEnum.ALMOCO;
					break;
				case "JANTAR":
					tipoEnum = TipoAlimentacaoEnum.JANTAR;
					break;
				case "LANCHE DA MANHÃ":
					tipoEnum = TipoAlimentacaoEnum.LANCHE_DA_MANHA;
					break;
				case "LANCHE DA TARDE":
					tipoEnum = TipoAlimentacaoEnum.LANCHE_DA_TARDE;
					break;
				case "LANCHE DA NOITE": 
					tipoEnum = TipoAlimentacaoEnum.LANCHE_DA_NOITE;
					break;
				default:
					tipoEnum = null;
					break;
				}

				ali.setTipo(tipoEnum);
				ali.setAlimentosConsumidos(rs.getString(3));
				Date dta = rs.getDate(4);
				ali.setDtaAlim(dta.toString());
				listaAlim.add(ali);
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
		return listaAlim;
	}
	


}
