package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.AgendaExameDAO;
import dao.BancoDados;
import entities.AgendaExame;

public class AgendaExameService {
	
	public AgendaExameService(){
		
		
	}
	
	public void excluir(int idExame) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new AgendaExameDAO(conn).excluir(idExame);
	}
	
	public void atualizar(int idExame) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new AgendaExameDAO(conn).atualizar(idExame);
	}
	
	public void cadastrar(AgendaExame agendaExame) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new AgendaExameDAO(conn).cadastrar(agendaExame);
	}
	
	public boolean horarioDisponivel(AgendaExame agendaExame) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new AgendaExameDAO(conn).horarioDisponivel(agendaExame);
	}
	
	public List<AgendaExame> buscarExameAgendado() throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		return new AgendaExameDAO(conn).buscarTodos();
	}
	
	public List<AgendaExame> buscarPorExame(String nome) throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		return new AgendaExameDAO(conn).buscarPorExame(nome);
	}
	
	public List<AgendaExame> buscarExameAgendadoPorCpf(String cpf) throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		return new AgendaExameDAO(conn).buscarPorPaciente(cpf);
	}
	
	
}
