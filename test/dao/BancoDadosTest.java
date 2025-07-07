package dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BancoDadosTest {
	
	@Disabled
	public void conectarTest() throws SQLException, IOException {
		
		Connection conn = BancoDados.conectar();	
		assertNotNull(conn);
	}
	
	@Test
	public void desconectarTest() throws SQLException, IOException {
		
		
		Connection conn = BancoDados.conectar();
		conn = BancoDados.desconectar();
		
		assertNull(conn);
	}
}
