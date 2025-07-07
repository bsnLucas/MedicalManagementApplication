package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entities.Exame;
import service.ExameService;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastroExameGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private GestaoMedicaMenuGUI menuGui;
	private ExameService exameService;
	private JTextField txtNome;
	private JTextField txtPreco;
	private JTextField txtOrientacoes;
	
	
	public CadastroExameGUI(GestaoMedicaMenuGUI menuGui) {
		
		this.exameService = new ExameService();
		this.menuGui = menuGui;
		this.initComponents();
	}
	
	public void cadastrar() {
		
		try {
			
			Exame exame = new Exame();
			
			exame.setNome(this.txtNome.getText());
			exame.setPreco(Integer.parseInt(this.txtPreco.getText()));
			exame.setOrientacoes(this.txtOrientacoes.getText());
			
			this.exameService.cadastrar(exame);
			this.fecharJanela();
			
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar exame", "Erro Cadastrar Exame", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void limparCampos() {
		
		this.txtNome.setText("");
		this.txtOrientacoes.setText("");
		this.txtPreco.setText("");
	}
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
	}
	
	public void initComponents() {
		setTitle("Cadastro Exame");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome.setBounds(38, 38, 33, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtNome.setBounds(86, 36, 294, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblPreco = new JLabel("Preço");
		lblPreco.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPreco.setBounds(38, 88, 46, 14);
		contentPane.add(lblPreco);
		
		txtPreco = new JTextField();
		txtPreco.setBounds(86, 86, 61, 20);
		contentPane.add(txtPreco);
		txtPreco.setColumns(10);
		
		JLabel lblOrientacoes = new JLabel("Orientações");
		lblOrientacoes.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOrientacoes.setBounds(38, 135, 68, 14);
		contentPane.add(lblOrientacoes);
		
		txtOrientacoes = new JTextField();
		txtOrientacoes.setBounds(38, 159, 365, 160);
		contentPane.add(txtOrientacoes);
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrar();
			}
		});
		btnAplicar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAplicar.setBounds(244, 332, 97, 38);
		contentPane.add(btnAplicar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				limparCampos();
			}
		});
		btnLimpar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLimpar.setBounds(137, 332, 97, 38);
		contentPane.add(btnLimpar);

	}
}
