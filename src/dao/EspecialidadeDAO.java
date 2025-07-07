package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;

import entities.Especialidade;

public class EspecialidadeDAO {
	
	private Connection conn;
	
	public EspecialidadeDAO(Connection conn) {
		
		this.conn = conn;
	}
	
	public int cadastrar(Especialidade especialidade) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("insert into especialidade (nome) values (?)");
			
			st.setString(1, especialidade.getNome());
			
			return st.executeUpdate();
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
	
	public List<Especialidade> buscarTodos() throws SQLException{
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement("select * from especialidade");
			rs = st.executeQuery();
			
			List<Especialidade> listaEspecialidade = new ArrayList<>();
			
			while(rs.next()) {
				
				Especialidade especialidade = new Especialidade();
				
				especialidade.setId(rs.getInt("id"));
				especialidade.setNome(rs.getString("nome"));
				
				listaEspecialidade.add(especialidade);
			}
			
			return listaEspecialidade;
			
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.finalizarResultSet(rs);
			BancoDados.desconectar();
		}
	}
	
	public int excluir(int id) throws SQLException {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement("delete from especialidade where id = ?");
			
			st.setInt(1, id);
			
			return st.executeUpdate();
		
		} finally {
			
			BancoDados.finalizarStatement(st);
			BancoDados.desconectar();
		}
	}
}
