package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.AgendaExame;
import entities.Exame;
import entities.Medico;
import entities.Paciente;

public class AgendaExameDAO {
	
	private Connection conn;
	
	public AgendaExameDAO(Connection conn) {
		
		this.conn = conn;
	}
	
	public int cadastrar(AgendaExame agendaExame) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("insert into agenda_exame (codigoExame, codigoPaciente, codigoMedico, data, horario, preco, status) values (?, ?, ?, ?, ?, ?, ?)");
			
			st.setInt(1, agendaExame.getExame().getId());
			st.setString(2, agendaExame.getPaciente().getCpf());
			st.setInt(3, agendaExame.getMedico().getCrm());
			st.setDate(4, agendaExame.getData());
			st.setTime(5, agendaExame.getHorario());
			st.setDouble(6, agendaExame.getPreco());
			st.setString(7, agendaExame.getStatus());
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public boolean horarioDisponivel(AgendaExame agendaExame) throws SQLException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select count(*) from agenda_exame where data = ? and horario = ?");
			st.setDate(1, agendaExame.getData());
			st.setTime(2, agendaExame.getHorario());
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
	
	public List<AgendaExame> buscarPorPaciente(String cpf) throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from agenda_exame, exame, paciente, medico where paciente.cpf = ? and agenda_exame.codigoExame = exame.id and agenda_exame.codigoPaciente = paciente.cpf and agenda_exame.codigoMedico = medico.crm");
			st.setString(1, cpf);
			rs = st.executeQuery();
			
			List<AgendaExame> listaAgendaExames = new ArrayList<>();
			
			while(rs.next()) {
				
				AgendaExame agendaExame = new AgendaExame();
				
				agendaExame.setCodigoAgendamento(rs.getInt("codigoAgendamento"));
				agendaExame.setData(rs.getDate("data"));
				agendaExame.setHorario(rs.getTime("horario"));
				agendaExame.setPreco(rs.getDouble("preco"));
				agendaExame.setStatus(rs.getString("status"));
				
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

				Exame exame = new Exame();
				
				exame.setId(rs.getInt("id"));
				exame.setNome(rs.getString("nome"));
				exame.setPreco(rs.getDouble("preco"));
				exame.setOrientacoes(rs.getString("orientacoes"));
				
				agendaExame.setMedico(medico);
				agendaExame.setPaciente(paciente);
				agendaExame.setExame(exame);
				
				listaAgendaExames.add(agendaExame);
			}
		
			return listaAgendaExames;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
		
	}
	
	
	public List<AgendaExame> buscarPorExame(String nome) throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from agenda_exame, exame, paciente, medico where exame.nome = ? and agenda_exame.codigoExame = exame.id and agenda_exame.codigoPaciente = paciente.cpf and agenda_exame.codigoMedico = medico.crm");
			st.setString(1, nome);
			rs = st.executeQuery();
			
			List<AgendaExame> listaAgendaExames = new ArrayList<>();
			
			while(rs.next()) {
				
				AgendaExame agendaExame = new AgendaExame();
				
				agendaExame.setCodigoAgendamento(rs.getInt("codigoAgendamento"));
				agendaExame.setData(rs.getDate("data"));
				agendaExame.setHorario(rs.getTime("horario"));
				agendaExame.setPreco(rs.getDouble("preco"));
				agendaExame.setStatus(rs.getString("status"));
				
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

				Exame exame = new Exame();
				
				exame.setId(rs.getInt("id"));
				exame.setNome(rs.getString("nome"));
				exame.setPreco(rs.getDouble("preco"));
				exame.setOrientacoes(rs.getString("orientacoes"));
				
				agendaExame.setMedico(medico);
				agendaExame.setPaciente(paciente);
				agendaExame.setExame(exame);
				
				listaAgendaExames.add(agendaExame);
			}
		
			return listaAgendaExames;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
		
	}
	
	public List<AgendaExame> buscarTodos() throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from agenda_exame, exame, paciente, medico where agenda_exame.codigoExame = exame.id and agenda_exame.codigoPaciente = paciente.cpf and agenda_exame.codigoMedico = medico.crm");
			rs = st.executeQuery();
			
			List<AgendaExame> listaAgendaExames = new ArrayList<>();
			
			while(rs.next()){
				
				AgendaExame agendaExame = new AgendaExame();
				
				agendaExame.setCodigoAgendamento(rs.getInt("codigoAgendamento"));
				agendaExame.setData(rs.getDate("data"));
				agendaExame.setHorario(rs.getTime("horario"));
				agendaExame.setPreco(rs.getDouble("preco"));
				agendaExame.setStatus(rs.getString("status"));
				
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

				Exame exame = new Exame();
				
				exame.setId(rs.getInt("id"));
				exame.setNome(rs.getString("nome"));
				exame.setPreco(rs.getDouble("preco"));
				exame.setOrientacoes(rs.getString("orientacoes"));
				
				agendaExame.setMedico(medico);
				agendaExame.setPaciente(paciente);
				agendaExame.setExame(exame);
				
				listaAgendaExames.add(agendaExame);
			}
			
			return listaAgendaExames;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(int id) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("update agenda_exame set status = ? where codigoAgendamento = ?");

			st.setString(1, "Realizado");
			st.setInt(2, id);
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(int id) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("delete from agenda_exame where codigoAgendamento = ?");
			
			st.setInt(1, id);
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
