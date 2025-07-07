package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Exame;

public class ExameDAO {
	
	private Connection conn;
	
	public ExameDAO(Connection conn) {
		
		this.conn = conn;
	}
	
	public int cadastrar(Exame exame) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("insert into exame (nome, preco, orientacoes) values (?, ?, ?)");
			
			st.setString(1, exame.getNome());
			st.setDouble(2, exame.getPreco());
			st.setString(3, exame.getOrientacoes());
			
			return st.executeUpdate();
		
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public List<Exame> buscarTodos() throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from exame");
			rs = st.executeQuery();
			
			List<Exame> listaExames = new ArrayList<>();
			
			while(rs.next()){
				
				Exame exame = new Exame();
				
				exame.setId(rs.getInt("id"));
				exame.setNome(rs.getString("nome"));
				exame.setPreco(rs.getDouble("preco"));
				exame.setOrientacoes(rs.getString("orientacoes"));
				
				listaExames.add(exame);
			}
			
			return listaExames;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public int atualizar(Exame exame) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("update from exame set nome = ?, preco = ?, orientacoes = ? where id = ?");
			
			st.setString(1, exame.getNome());
			st.setDouble(2, exame.getPreco());
			st.setString(3, exame.getOrientacoes());
			st.setInt(4, exame.getId());
			
			return st.executeUpdate();
		
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(int id) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("delete from exame where id = ?");
			
			st.setInt(1, id);
			
			return st.executeUpdate();
	
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
		
	}
}
