package gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import entities.Especialidade;
import service.EspecialidadeService;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastrarEspecialidadeGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	
	private EspecialidadeService especialidadeService;
	private GestaoMedicaMenuGUI menuGui;
	
	public CadastrarEspecialidadeGUI(GestaoMedicaMenuGUI menuGui) {
		
		this.initComponents();
		this.especialidadeService = new EspecialidadeService();
		this.menuGui = menuGui;
	}
	
	public void cadastrar() {
		
		try {
			
			Especialidade especialidade = new Especialidade();
			
			especialidade.setNome(this.txtNome.getText());
			
			this.especialidadeService.cadastrar(especialidade);
			this.fecharJanela();
			
		} catch(Exception e) {
			
			e.getStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar especialidade", "Erro Cadastro Especialidade", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
		
	}

	public void initComponents() {
		setTitle("Cadastro Especialidade");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 310, 172);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNome.setBounds(36, 36, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtNome.setBounds(81, 34, 174, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cadastrar();
			}
		});
		btnAplicar.setBounds(93, 82, 113, 40);
		contentPane.add(btnAplicar);

	}

}
