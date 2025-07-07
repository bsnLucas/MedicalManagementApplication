package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Endereco;
import entities.Especialidade;
import entities.Medico;

public class MedicoDAO {

	private Connection conn;
	
	public MedicoDAO(Connection conn) {
		
		this.conn = conn;
	}
	
	public int cadastrar(Medico medico) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("insert into medico (crm, nome, codigoEndereco, telefone, codigoEspecialidade) values (?, ?, ? , ?, ?)");
			
			st.setInt(1, medico.getCrm());
			st.setString(2, medico.getNome());
			st.setInt(3, medico.getEndereco().getId());
			st.setString(4, medico.getTelefone());
			st.setInt(5, medico.getEspecialidade().getId());
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int buscarIdEndereco(int crm) throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from medico join endereco on medico.codigoEndereco = endereco.id where medico.crm = ?");
			st.setInt(1, crm);
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
	
	public List<Medico> buscarComEndereco() throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			List<Medico> listaMedicos = new ArrayList<>();
			
			st = conn.prepareStatement("select * from medico, endereco, especialidade where medico.codigoEndereco = endereco.id and medico.codigoEspecialidade = especialidade.id");
			rs = st.executeQuery();
			
			while(rs.next()) {
				
				Medico medico = new Medico();
				
				medico.setCrm(rs.getInt("crm"));
				medico.setNome(rs.getString("nome"));
				medico.setTelefone(rs.getString("telefone"));
				
				Especialidade especialidade = new Especialidade();
				
				especialidade.setId(rs.getInt("id"));
				especialidade.setNome(rs.getString("nome"));
				
				Endereco endereco = new Endereco();
				
				endereco.setId(rs.getInt("id"));
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setNumero(rs.getInt("numero"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setUf(rs.getString("uf"));
				endereco.setCep(rs.getString("cep"));
				
				medico.setEspecialidade(especialidade);
				medico.setEndereco(endereco);
				
				listaMedicos.add(medico);
			}
			
			return listaMedicos;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public Medico buscarPorMedico(int crm) throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from medico where crm = ?");
			st.setInt(1, crm);
			rs = st.executeQuery();
			
			if(rs.next()) {
				
				Medico medico = new Medico();
				Endereco endereco = new Endereco();
				Especialidade especialidade = new Especialidade();
				
				medico.setCrm(rs.getInt("crm"));
				medico.setNome(rs.getString("nome"));
				medico.setTelefone(rs.getString("telefone"));
				endereco.setId(rs.getInt("codigoEndereco"));
				especialidade.setId(rs.getInt("codigoEspecialidade"));
				medico.setEndereco(endereco);
				medico.setEspecialidade(especialidade);
				
				return medico;
			}
			
			return null;
		
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<Medico> buscarTodos() throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from medico");
			rs = st.executeQuery();
			
			List<Medico> listaMedicos = new ArrayList<>();
			
			while(rs.next()) {
				
				Medico medico = new Medico();
				Endereco endereco = new Endereco();
				Especialidade especialidade = new Especialidade();
				
				
				medico.setCrm(rs.getInt("crm"));
				medico.setNome(rs.getString("nome"));
				endereco.setId(rs.getInt("codigoEndereco"));
				medico.setEndereco(endereco);
				medico.setTelefone(rs.getString("telefone"));
				especialidade.setId(rs.getInt("codigoEspecialidade"));
				medico.setEspecialidade(especialidade);
				
				listaMedicos.add(medico);
			}
			
			return listaMedicos;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
		
	}
	
	public int atualizar(Medico medico) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("update medico set telefone = ?, codigoEndereco = ? where crm = ?");
			
			st.setString(1, medico.getTelefone());
			st.setInt(2, medico.getEndereco().getId());
			st.setInt(3, medico.getCrm());
			
			return st.executeUpdate();
		
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(int crm) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("delete from medico where crm = ?");
			
			st.setInt(1, crm);
			
			return st.executeUpdate();
		
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
