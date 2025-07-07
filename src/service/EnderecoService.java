package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.BancoDados;
import dao.EnderecoDAO;
import entities.Endereco;

public class EnderecoService {
	
	public EnderecoService() {
		
	}
	
	public void cadastrar(Endereco endereco) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new EnderecoDAO(conn).cadastrar(endereco);
	}
	
	public void excluir(int id) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new EnderecoDAO(conn).excluir(id);
	}
	
	public void atualizar(Endereco endereco) throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();
		new EnderecoDAO(conn).atualizar(endereco);
	}
	
	public List<String> buscarUf() {
		
		List<String> listaUf = new ArrayList<>();
		
		listaUf.add("PR");
		listaUf.add("SC");
		listaUf.add("RS");
		listaUf.add("RJ");
		listaUf.add("SP");
		listaUf.add("MG");
		listaUf.add("MT");
		listaUf.add("AC");
		listaUf.add("AL");
		listaUf.add("AP");
		listaUf.add("AM");
		listaUf.add("BA");
		listaUf.add("CE");
		listaUf.add("ES");
		listaUf.add("GO");
		listaUf.add("MA");
		listaUf.add("MT");
		listaUf.add("MS");
		listaUf.add("PA");
		listaUf.add("PB");
		listaUf.add("PE");
		listaUf.add("PI");
		listaUf.add("RN");
		listaUf.add("RO");
		listaUf.add("RR");
		listaUf.add("SE");
		listaUf.add("TO");
		listaUf.add("DF");
		
		return listaUf;
	}
}
