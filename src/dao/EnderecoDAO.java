package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Endereco;

public class EnderecoDAO {
	
	private Connection conn;
	
	public EnderecoDAO(Connection conn) {
		
		this.conn = conn;
	}

	public int cadastrar(Endereco endereco) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("insert into endereco (logradouro, numero, bairro, cidade, uf, cep) values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		
			st.setString(1, endereco.getLogradouro());
			st.setInt(2, endereco.getNumero());
			st.setString(3, endereco.getBairro());
			st.setString(4, endereco.getCidade());
			st.setString(5, endereco.getUf());
			st.setString(6, endereco.getCep());
			
			st.executeUpdate();
			
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()) {
				
				int idGerado = rs.getInt(1);
				endereco.setId(idGerado);
				return idGerado;
			}
			
			return -1;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Endereco> buscarTodos() throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from endereco");
			rs = st.executeQuery();
			
			List<Endereco> listaEnderecos = new ArrayList<>();
			
			while(rs.next()){
				
				Endereco endereco = new Endereco();
				
				endereco.setId(rs.getInt("id"));
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setNumero(rs.getInt("numero"));
				endereco.setCep(rs.getString("cidade"));
				endereco.setUf(rs.getString("uf"));
				endereco.setCep(rs.getString("cep"));
				
				listaEnderecos.add(endereco);
				
			}
			
			return listaEnderecos;
			
		} finally { 
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(Endereco endereco) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("update endereco set logradouro = ?, numero = ?, bairro = ?, cidade = ?, uf = ?, cep = ? where id = ?");
			
			st.setString(1, endereco.getLogradouro());
			st.setInt(2, endereco.getNumero());
			st.setString(3, endereco.getBairro());
			st.setString(4, endereco.getCidade());
			st.setString(5, endereco.getUf());
			st.setString(6, endereco.getCep());
			st.setInt(7, endereco.getId());
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(int id) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("delete from endereco where id = ?");
			
			st.setInt(1, id);
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
