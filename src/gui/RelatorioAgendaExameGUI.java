package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import entities.AgendaExame;
import entities.Exame;
import service.AgendaExameService;
import service.ExameService;

public class RelatorioAgendaExameGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblExames;
	private JComboBox cbExames;
	private JTable tblExamesAgendados;
	private JButton btnGerarRelatorio;
	
	private AgendaExameService agendaExameService;
	private ExameService exameService;
	private GestaoMedicaMenuGUI menuGui;

	public RelatorioAgendaExameGUI(GestaoMedicaMenuGUI menuGui) {
		
		this.menuGui = menuGui;
		this.agendaExameService = new AgendaExameService();
		this.exameService = new ExameService();
		
		this.initComponents();
		this.buscarExamesAgendados();
		this.buscarExame();
	}
	
	public void buscarExame() {
		
		try {
			
			List<Exame> listaExames = this.exameService.buscarTodos();
			
			for(Exame exame : listaExames) {
				
				this.cbExames.addItem(exame);
			}
		
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao buscar exames", "Erro Buscar Exames", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarExamesAgendados() {

		try {

			DefaultTableModel modelo = (DefaultTableModel) tblExamesAgendados.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);

			List<AgendaExame> listaAgendaExame = this.agendaExameService.buscarExameAgendado();

			System.out.println();

			for (AgendaExame agendaExame : listaAgendaExame) {

				modelo.addRow(new Object[] {

					agendaExame.getCodigoAgendamento(), agendaExame.getData(), agendaExame.getHorario(),
					agendaExame.getExame().getNome(), agendaExame.getPaciente().getNome(),
					agendaExame.getMedico().getNome(), agendaExame.getPreco(), agendaExame.getStatus() 
					
				});
			}

		} catch (Exception e) {

			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível encontrar os exames agendados",
					"Erro Buscar Exames Agendados", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void gerarRelatorio() {
		
		try {
			
			Exame exame = (Exame) this.cbExames.getSelectedItem();
			
			String nomeExame = exame.getNome();

			File file = new File("RelatorioAgendaMedico_" + nomeExame + ".txt");
			BufferedWriter documento = new BufferedWriter(new FileWriter(file));

			documento.write("========================================\n");
			documento.write("Relatório do Exame: " + nomeExame + "\n\n");

			List<AgendaExame> listaAgendaExames = this.agendaExameService.buscarPorExame(nomeExame);
			
			for (AgendaExame agendaExame : listaAgendaExames) {
				
				documento.write("ID do Exame: " + agendaExame.getCodigoAgendamento() + "\n");
				documento.write("Nome do Paciente: " + agendaExame.getPaciente().getNome() + "\n");
				documento.write("Nome do Médico: " + agendaExame.getMedico().getNome() +"\n");
				documento.write("Data: " + agendaExame.getData() + "\n");
				documento.write("Horário: " + agendaExame.getHorario() + "\n");
				documento.write("Preço do Exame: R$" + String.format("%.2f", agendaExame.getPreco()) + "\n");
				documento.write("Status: " + agendaExame.getStatus() + "\n");
				documento.write("========================================\n");
			
			}
			
			documento.close();
			
			JOptionPane.showMessageDialog(null, "Relatório de Exames foi criado com sucesso em:" + file.getAbsolutePath(), "Relatório Exame Criado", JOptionPane.DEFAULT_OPTION);
			
			this.fecharJanela();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível gerar o relatório de exames", "Erro Gerar Relatório", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
	}
	
	public void initComponents() {
		setTitle("Gerar Relatório Agenda Exame");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 495, 365);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		lblExames = new JLabel("Exames");
		lblExames.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblExames.setBounds(43, 32, 53, 16);
		contentPane.add(lblExames);
		
		cbExames = new JComboBox();
		cbExames.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbExames.setBounds(106, 28, 297, 24);
		contentPane.add(cbExames);
		
		JPanel pnExames = new JPanel();
		pnExames.setBorder(new TitledBorder(null, "Consultas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnExames.setBounds(21, 68, 436, 215);
		contentPane.add(pnExames);
		
		tblExamesAgendados = new JTable();
		tblExamesAgendados.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Data", "Hor\u00E1rio", "Paciente", "M\u00E9dico"
			}
		));
		tblExamesAgendados.getColumnModel().getColumn(1).setPreferredWidth(71);
		tblExamesAgendados.getColumnModel().getColumn(3).setPreferredWidth(94);
		tblExamesAgendados.getColumnModel().getColumn(4).setPreferredWidth(110);
		pnExames.add(tblExamesAgendados);
		
		btnGerarRelatorio = new JButton("Gerar Relatório");
		btnGerarRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				gerarRelatorio();
			}
		});
		btnGerarRelatorio.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnGerarRelatorio.setBounds(326, 293, 119, 21);
		contentPane.add(btnGerarRelatorio);
	}

}
