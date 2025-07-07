package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.PacienteDAO;
import entities.Paciente;

public class PacienteService {

	public PacienteService() {
		
	}
	
	public void cadastrar(Paciente paciente, File foto) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		byte[] imagemBytes = new byte[(int) foto.length()];
		FileInputStream fis = new FileInputStream(foto);
		fis.read(imagemBytes);
		paciente.setFoto(imagemBytes);
		new PacienteDAO(conn).cadastrar(paciente);
	}
	
	public int buscarIdEndereco(String cpf) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new PacienteDAO(conn).buscarIdEndereco(cpf);
	}
	
	public void atualizar(Paciente paciente) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new PacienteDAO(conn).atualizar(paciente);
	}
	
	public void excluir(String cpf) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new PacienteDAO(conn).excluir(cpf);
	}
	
	public List<Paciente> buscarComEndereco() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		List<Paciente> listaPacientes = new PacienteDAO(conn).buscarComEndereco();
		return listaPacientes;	
	}
	
	public List<Paciente> buscarTodos() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		return new PacienteDAO(conn).buscarTodos();
		
	}
}
