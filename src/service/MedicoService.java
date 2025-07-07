package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.MedicoDAO;
import dao.PacienteDAO;
import entities.Medico;

public class MedicoService {
	
	public MedicoService() {
		
	}
	
	public void excluir(int crm) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new MedicoDAO(conn).excluir(crm);
	}
	
	public void cadastrar(Medico medico) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new MedicoDAO(conn).cadastrar(medico);
	}
	
	public void atualizar(Medico medico) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new MedicoDAO(conn).atualizar(medico);
	}
	
	public int buscarIdEndereco(int crm) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new MedicoDAO(conn).buscarIdEndereco(crm);
	}
	
	public List<Medico> buscarTodos() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new MedicoDAO(conn).buscarTodos();
	}
	
	public List<Medico> buscarComEndereco() throws SQLException, IOException{
		
		Connection conn = BancoDados.conectar();
		return new MedicoDAO(conn).buscarComEndereco();
	}
}
