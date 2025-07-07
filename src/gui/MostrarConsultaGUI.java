package gui;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import entities.AgendaConsulta;
import service.AgendaConsultaService;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MostrarConsultaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private GestaoMedicaMenuGUI menuGui;
	private AgendaConsultaService agendaConsultaService;
	private JTable tblConsultas;

	public MostrarConsultaGUI(GestaoMedicaMenuGUI menuGui) {
		
		this.agendaConsultaService = new AgendaConsultaService();
		this.menuGui = menuGui;
		
		this.initComponents();
		this.buscarConsultas();
	}

	public void excluir() {
		
		try {
			
			int idConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o ID da consulta agendada que deseja excluir", "Excluir Consulta Agendada", JOptionPane.OK_CANCEL_OPTION));
			
			this.agendaConsultaService.excluir(idConsulta);
			fecharJanela();
			buscarConsultas();
		
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao excluir consulta agendada", "Erro Excluir Consulta", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void atualizar() {
		
		try {
			
			int idConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Insira o ID da consulta agendada que deseja alterar o status", "Alterar Consulta Agendada", JOptionPane.OK_CANCEL_OPTION));
			
			this.agendaConsultaService.atualizar(idConsulta);
			fecharJanela();
			buscarConsultas();
			
		} catch(Exception e) {
			
			JOptionPane.showMessageDialog(null, "Erro ao tentar alterar o status da consulta", "Erro Alterar Status Consulta", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarConsultas() {
		
		try {
			
			DefaultTableModel modelo = (DefaultTableModel) tblConsultas.getModel();
			modelo.fireTableDataChanged();
			modelo.setRowCount(0);
			
			List<AgendaConsulta> listaAgendaConsulta = this.agendaConsultaService.buscarTodos();
			
			for(AgendaConsulta agendaConsulta : listaAgendaConsulta) {
				
				modelo.addRow(new Object[] {
						
					agendaConsulta.getId(),
					agendaConsulta.getData(),
					agendaConsulta.getHorario(),
					agendaConsulta.getPaciente().getNome(),
					agendaConsulta.getMedico().getNome(),
					agendaConsulta.getStatus()
				});
			}
			
		} catch(Exception e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao buscar consultas agendadas no sistema", "Erro Buscar Consultas Agendadas", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void fecharJanela() {
		
		this.dispose();
		this.menuGui.setVisible(true);
	}
	
	public void initComponents() {
		setTitle("Consultas Agendadas");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 382);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnConsultas = new JPanel();
		pnConsultas.setBorder(new TitledBorder(null, "Consultas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnConsultas.setBounds(10, 10, 441, 292);
		contentPane.add(pnConsultas);
		
		tblConsultas = new JTable();
		tblConsultas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Data", "Hor\u00E1rio", "Paciente", "M\u00E9dico"
			}
		));
		tblConsultas.getColumnModel().getColumn(1).setPreferredWidth(71);
		tblConsultas.getColumnModel().getColumn(3).setPreferredWidth(94);
		tblConsultas.getColumnModel().getColumn(4).setPreferredWidth(110);
		pnConsultas.add(tblConsultas);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				atualizar();
			}
		});
		btnAlterar.setBounds(349, 312, 85, 21);
		contentPane.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				excluir();
			}
		});
		btnExcluir.setBounds(254, 312, 85, 21);
		contentPane.add(btnExcluir);

	}

}
