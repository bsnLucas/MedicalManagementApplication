package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.AgendaConsulta;
import entities.AgendaExame;
import entities.Medico;
import entities.Paciente;

public class AgendaConsultaDAO {
	
	private Connection conn;
	
	public AgendaConsultaDAO(Connection conn) {
		
		this.conn = conn;
	}
	
	public int cadastrar(AgendaConsulta agendaConsulta) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("insert into agenda_consulta (codigoPaciente, codigoMedico, horario, data, status) values (?, ?, ?, ?, ?)");
			
			st.setString(1, agendaConsulta.getPaciente().getCpf());
			st.setInt(2, agendaConsulta.getMedico().getCrm());
			st.setTime(3, agendaConsulta.getHorario());
			st.setDate(4, agendaConsulta.getData());
			st.setString(5, agendaConsulta.getStatus());
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public boolean horarioDisponivel(AgendaConsulta agendaConsulta) throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select count(*) from agenda_consulta where data = ? and horario = ?");
			st.setDate(1, agendaConsulta.getData());
			st.setTime(2, agendaConsulta.getHorario());
			rs = st.executeQuery();
			
			if(rs.next()) {
				
				int count = rs.getInt(1);
				return count == 0;
			}
			
			return false;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<AgendaConsulta> buscarPorPaciente(String cpf) throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from agenda_consulta, paciente, medico where paciente.cpf = ? and agenda_consulta.codigoPaciente = paciente.cpf and agenda_consulta.codigoMedico = medico.crm");
			st.setString(1, cpf);
			rs = st.executeQuery();
			
			List<AgendaConsulta> listaAgendaConsulta = new ArrayList<>();
			
			while(rs.next()) {
				
				AgendaConsulta agendaConsulta = new AgendaConsulta();
				
				agendaConsulta.setId(rs.getInt("id"));
				agendaConsulta.setData(rs.getDate("data"));
				agendaConsulta.setHorario(rs.getTime("horario"));
				agendaConsulta.setStatus(rs.getString("status"));
				
				Paciente paciente = new Paciente();
				
				paciente.setCpf(rs.getString("cpf"));
				paciente.setNome(rs.getString("paciente.nome"));
				paciente.setFoto(rs.getBytes("foto"));
				paciente.setDataNascimento(rs.getDate("dataNascimento"));
				paciente.setSexo(rs.getString("sexo"));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setFormaPagamento(rs.getString("formaPagamento"));
				
				Medico medico = new Medico();
				
				medico.setCrm(rs.getInt("crm"));
				medico.setNome(rs.getString("medico.nome"));
				medico.setTelefone(rs.getString("telefone"));
				
				agendaConsulta.setMedico(medico);
				agendaConsulta.setPaciente(paciente);
				
				listaAgendaConsulta.add(agendaConsulta);
			}
			
			return listaAgendaConsulta;
			
		} finally { 
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<AgendaConsulta> buscarPorMedico(int crm) throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from agenda_consulta, paciente, medico where medico.crm = ? and agenda_consulta.codigoPaciente = paciente.cpf and agenda_consulta.codigoMedico = medico.crm");
			st.setInt(1, crm);
			rs = st.executeQuery();
			
			List<AgendaConsulta> listaAgendaConsulta = new ArrayList<>();
			
			while(rs.next()) {
				
				AgendaConsulta agendaConsulta = new AgendaConsulta();
				
				agendaConsulta.setId(rs.getInt("id"));
				agendaConsulta.setData(rs.getDate("data"));
				agendaConsulta.setHorario(rs.getTime("horario"));
				agendaConsulta.setStatus(rs.getString("status"));
				
				Paciente paciente = new Paciente();
				
				paciente.setCpf(rs.getString("cpf"));
				paciente.setNome(rs.getString("paciente.nome"));
				paciente.setFoto(rs.getBytes("foto"));
				paciente.setDataNascimento(rs.getDate("dataNascimento"));
				paciente.setSexo(rs.getString("sexo"));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setFormaPagamento(rs.getString("formaPagamento"));
				
				Medico medico = new Medico();
				
				medico.setCrm(rs.getInt("crm"));
				medico.setNome(rs.getString("medico.nome"));
				medico.setTelefone(rs.getString("telefone"));
				
				agendaConsulta.setMedico(medico);
				agendaConsulta.setPaciente(paciente);
				
				listaAgendaConsulta.add(agendaConsulta);
			}
			
			return listaAgendaConsulta;
			
		} finally { 
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public List<AgendaConsulta> buscarTodos() throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from agenda_consulta, paciente, medico where agenda_consulta.codigoPaciente = paciente.cpf and agenda_consulta.codigoMedico = medico.crm");
			rs = st.executeQuery();
			
			List<AgendaConsulta> listaAgendaConsulta = new ArrayList<>();
			
			while(rs.next()) {
				
				AgendaConsulta agendaConsulta = new AgendaConsulta();
				
				agendaConsulta.setId(rs.getInt("id"));
				agendaConsulta.setData(rs.getDate("data"));
				agendaConsulta.setHorario(rs.getTime("horario"));
				agendaConsulta.setStatus(rs.getString("status"));
				
				Paciente paciente = new Paciente();
				
				paciente.setCpf(rs.getString("cpf"));
				paciente.setNome(rs.getString("paciente.nome"));
				paciente.setFoto(rs.getBytes("foto"));
				paciente.setDataNascimento(rs.getDate("dataNascimento"));
				paciente.setSexo(rs.getString("sexo"));
				paciente.setTelefone(rs.getString("telefone"));
				paciente.setFormaPagamento(rs.getString("formaPagamento"));
				
				Medico medico = new Medico();
				
				medico.setCrm(rs.getInt("crm"));
				medico.setNome(rs.getString("medico.nome"));
				medico.setTelefone(rs.getString("telefone"));
				
				agendaConsulta.setMedico(medico);
				agendaConsulta.setPaciente(paciente);
				
				listaAgendaConsulta.add(agendaConsulta);
			}
			
			return listaAgendaConsulta;
		
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(int idConsulta) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("update agendaConsulta set status = ? where id = ?");
			
			st.setString(1, "Realizada");
			st.setInt(2, idConsulta);
			
			return st.executeUpdate();
		
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
	
	public int excluir(int id) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("delete from agendaConsulta where id = ?");
			
			st.setInt(1, id);
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
