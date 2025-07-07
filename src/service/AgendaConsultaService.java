package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.AgendaConsultaDAO;
import dao.AgendaExameDAO;
import dao.BancoDados;
import entities.AgendaConsulta;
import entities.AgendaExame;

public class AgendaConsultaService {

	public AgendaConsultaService() {
		
	}
	
	public void cadastrar(AgendaConsulta agendaConsulta) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new AgendaConsultaDAO(conn).cadastrar(agendaConsulta);
	}
	
	public List<AgendaConsulta> buscarTodos() throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		return new AgendaConsultaDAO(conn).buscarTodos();
	}
	
	public List<AgendaConsulta> buscarPorMedico(int crm) throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		return new AgendaConsultaDAO(conn).buscarPorMedico(crm);
	}
	
	public void excluir(int idConsulta) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new AgendaConsultaDAO(conn).excluir(idConsulta);
	}
	
	public void atualizar(int idConsulta) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new AgendaConsultaDAO(conn).atualizar(idConsulta);
	}
	
	public boolean horarioDisponivel(AgendaConsulta agendaConsulta) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new AgendaConsultaDAO(conn).horarioDisponivel(agendaConsulta);
	}
	
	public List<AgendaConsulta> buscarPorPaciente(String cpf) throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		return new AgendaConsultaDAO(conn).buscarPorPaciente(cpf);
	}
}
