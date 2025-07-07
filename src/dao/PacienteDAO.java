package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Endereco;
import entities.Paciente;

public class PacienteDAO {
	
	private Connection conn;
	
	public PacienteDAO(Connection conn) {
		
		this.conn = conn;
	}
	
	public int cadastrar(Paciente paciente) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("insert into paciente (nome, foto, dataNascimento, sexo, codigoEndereco, telefone, formaPagamento, cpf) values (?, ?, ?, ?, ?, ?, ?, ?)");
			
			st.setString(1, paciente.getNome());
			
			st.setBytes(2, paciente.getFoto());
			
			st.setDate(3, paciente.getDataNascimento());
			st.setString(4, paciente.getSexo());
			st.setInt(5, paciente.getEndereco().getId());
			st.setString(6, paciente.getTelefone());
			st.setString(7, paciente.getFormaPagamento());
			st.setString(8, paciente.getCpf());
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}

	public List<Paciente> buscarComEndereco() throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			List<Paciente> listaPacientes = new ArrayList<>();
			
			st = conn.prepareStatement("select * from paciente p join endereco e on p.codigoEndereco = e.id");
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				Paciente paciente = new Paciente();
				paciente.setCpf(rs.getString("cpf"));
				paciente.setNome(rs.getString("nome"));
				paciente.setFoto(rs.getBytes("foto"));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setDataNascimento(rs.getDate("dataNascimento"));
				paciente.setSexo(rs.getString("sexo"));
				paciente.setFormaPagamento(rs.getString("formaPagamento"));
				
				Endereco endereco = new Endereco();
		        endereco.setId(rs.getInt("id"));
		        endereco.setLogradouro(rs.getString("logradouro"));
		        endereco.setNumero(rs.getInt("numero"));
		        endereco.setBairro(rs.getString("bairro"));
		        endereco.setCidade(rs.getString("cidade"));
		        endereco.setUf(rs.getString("uf"));
		        endereco.setCep(rs.getString("cep"));
		        
		        paciente.setEndereco(endereco);
		        listaPacientes.add(paciente);
		        
			}
			
			return listaPacientes;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public int buscarIdEndereco(String cpf) throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from paciente join endereco on paciente.codigoEndereco = endereco.id where paciente.cpf = ?");
			st.setString(1, cpf);
			rs = st.executeQuery();
			
			if (rs.next()){
				
				return rs.getInt("codigoEndereco");
				
			} else {
				
				return -1;
			}

			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public Paciente buscarPorPaciente(String cpf) throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from paciente where cpf = ?");
			st.setString(1, cpf);
			rs = st.executeQuery();
			
			if(rs.next()) {
				
				Paciente paciente = new Paciente();
				Endereco endereco = new Endereco();
				
				paciente.setCpf(rs.getString("cpf"));
				paciente.setNome(rs.getString("nome"));
				paciente.setDataNascimento(rs.getDate("data"));
				paciente.setFoto(rs.getBytes("foto"));
				paciente.setSexo(rs.getString("sexo"));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setFormaPagamento(rs.getString("formaPagamento"));
				endereco.setId(rs.getInt("codigoEndereco"));
				paciente.setEndereco(endereco);
				
				return paciente;
			}
			
			return null;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Paciente> buscarTodos() throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from paciente");
			
			rs = st.executeQuery();
			
			List<Paciente> listaPacientes = new ArrayList<>();
			
			while(rs.next()) {
				
				Paciente paciente = new Paciente();
				Endereco endereco = new Endereco();
				
				paciente.setCpf(rs.getString("cpf"));
				paciente.setNome(rs.getString("nome"));
				paciente.setFoto(rs.getBytes("foto"));
				paciente.setDataNascimento(rs.getDate("dataNascimento"));
				paciente.setSexo(rs.getString("sexo"));
				endereco.setId(rs.getInt("codigoEndereco"));
				paciente.setEndereco(endereco);
				paciente.getEndereco().setId(rs.getInt("codigoEndereco"));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setFormaPagamento(rs.getString("formaPagamento"));
				
				listaPacientes.add(paciente);
			}
			
			return listaPacientes;
		
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(Paciente paciente) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("update paciente set telefone = ?, formaPagamento = ? where cpf = ?");
			
			st.setString(1, paciente.getTelefone());
			st.setString(2, paciente.getFormaPagamento());
			st.setString(3, paciente.getCpf());
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(String cpf) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("delete from paciente where cpf = ?");
			
			st.setString(1, cpf);
			
			return st.executeUpdate();
		
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
