package br.window.curso;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import br.dao.DAOCursos;
import br.dao.DAODepartamento;
import br.dao.DAODisciplinas;
import br.dao.DAOtblStatusCD;
import br.dao.Datasource;
import br.model.Curso;
import br.model.Departamento;
import br.model.Disciplinas;
import br.model.StatusCd;
import javax.swing.UIManager;

public class JanelaInserirCurso extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7889475273883412172L;
	private JFrame frame;
	private TextField textFieldNomedoCurso;
	private int i;

	private Datasource ds;
	private DAOCursos daoCurso;
	private JTable tablePrimario;
	private JTable tableSecundario;
	private Choice choiceDepartamento = new Choice();
	private DefaultTableModel modelo;
	private ArrayList<Integer> listCursoIdObrigatorioSecundario = new ArrayList<Integer>();
	private ArrayList<Integer> listCursoIdLivreTerciario = new ArrayList<Integer>();

	private ArrayList<Integer> listid = new ArrayList<Integer>();
	private ArrayList<String> listnomes = new ArrayList<String>();
	private JTable tableTerciario;

	/**
	 * Launch the application.
	 */

	public static void run() {
		try {
			JanelaInserirCurso window = new JanelaInserirCurso();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaInserirCurso() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Inserir Aluno");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frame.setBounds((d.width / 2) - 445, (d.height / 2) - 250, 910, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		preencherchoice();
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnFechar.setBounds(531, 418, 89, 23);
		frame.getContentPane().add(btnFechar);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ds = new Datasource();
				daoCurso = new DAOCursos(ds);
				Curso BDCurso = new Curso();
				StatusCd BDStatus = new StatusCd();
				try {
					BDCurso.setNome(textFieldNomedoCurso.getText());
					int index = choiceDepartamento.getSelectedIndex();
					if (choiceDepartamento.getSelectedItem().equals(listnomes.get(index))) {
						BDCurso.setIdDepartamento(listid.get(index));
					}
					Random R = new Random();
					int id = R.nextInt((200 - 100) + 1) + 100;
					BDCurso.setIdCurso(id);
					daoCurso.createCurso(BDCurso);
					int idstatus = R.nextInt((200 - 100) + 1) + 100;
					for (int i = 0; i < listCursoIdObrigatorioSecundario.size(); i++) {
						ds = null;
						ds = new Datasource();
						DAOtblStatusCD daoStatus = new DAOtblStatusCD(ds);
						BDStatus.setIdStatuscd(idstatus);
						BDStatus.setStatus("O");
						BDStatus.setIddisciplina(listCursoIdObrigatorioSecundario.get(i));
						BDStatus.setIdcurso(id);
						daoStatus.createStatus(BDStatus);
						ds = null;
					}
					for (int i = 0; i < listCursoIdLivreTerciario.size(); i++) {
						ds = null;
						ds = new Datasource();
						DAOtblStatusCD daoStatus = new DAOtblStatusCD(ds);
						BDStatus.setIdStatuscd(idstatus);
						BDStatus.setStatus("L");
						BDStatus.setIddisciplina(listCursoIdLivreTerciario.get(i));
						BDStatus.setIdcurso(id);
						daoStatus.createStatus(BDStatus);
						ds = null;
					}
					JOptionPane.showMessageDialog(frame, "Adicionado!", "Menssagem", 1);
					textFieldNomedoCurso.setText("");
					JanelaGerenciaCurso.initThreadAtualiza();
					frame.dispose();

				} catch (Exception a) {
					JOptionPane.showMessageDialog(frame, "Preencha todos os campos", "Erro!", 2);
					System.err.println(a.getMessage());
				}
			}
		});
		btnAdicionar.setBounds(283, 418, 89, 23);
		frame.getContentPane().add(btnAdicionar);

		textFieldNomedoCurso = new TextField();
		textFieldNomedoCurso.setBounds(162, 70, 374, 20);
		frame.getContentPane().add(textFieldNomedoCurso);
		textFieldNomedoCurso.setColumns(10);

		JLabel lblNomedoCurso = new JLabel("Nome do Curso");
		lblNomedoCurso.setBounds(59, 70, 206, 14);
		frame.getContentPane().add(lblNomedoCurso);

		JScrollPane scrollPanePrimario = new JScrollPane();
		scrollPanePrimario.setBounds(48, 246, 225, 150);
		frame.getContentPane().add(scrollPanePrimario);

		tablePrimario = new JTable();
		tablePrimario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				i = tablePrimario.getSelectedRow();
				System.out.println(i);
			}
		});
		tablePrimario.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tablePrimario.setBounds(0, 0, 439, 1);

		JScrollPane scrollPaneSecundario = new JScrollPane();
		scrollPaneSecundario.setBounds(339, 246, 225, 150);
		frame.getContentPane().add(scrollPaneSecundario);

		tableSecundario = new JTable();
		tableSecundario.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableSecundario.setBounds(0, 0, 439, 1);
		scrollPaneSecundario.setViewportView(tableSecundario);
		scrollPanePrimario.setViewportView(tablePrimario);

		tablePrimario.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tablePrimario
				.setModel(new DefaultTableModel(
						new Object[][] { { null, null }, { null, null }, { null, null }, { null, null }, { null, null },
								{ null, null }, { null, null }, { null, null }, },
						new String[] { "ID", "Mat\u00E9ria" }) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					@SuppressWarnings("rawtypes")
					Class[] columnTypes = new Class[] { Integer.class, Object.class };

					@SuppressWarnings({ "unchecked", "rawtypes" })
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}

					boolean[] columnEditables = new boolean[] { false, false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
		tablePrimario.getColumnModel().getColumn(0).setResizable(false);
		tablePrimario.getColumnModel().getColumn(0).setPreferredWidth(37);
		tablePrimario.getColumnModel().getColumn(1).setResizable(false);
		tablePrimario.getColumnModel().getColumn(1).setPreferredWidth(97);
		tableSecundario.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableSecundario
				.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "ID", "Matéria" }) {
					private static final long serialVersionUID = 1L;
					boolean[] canEdit = new boolean[] { false, false };

					public boolean isCellEditable(int rowIndex, int columnIndex) {
						return canEdit[columnIndex];
					}
				});

		JButton buttontoRight = new JButton(">");
		buttontoRight.setBackground(UIManager.getColor("Button.disabledShadow"));
		buttontoRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ds = new Datasource();
				new DAODisciplinas(ds);
				Object coluna = tablePrimario.getValueAt(tablePrimario.getSelectedRow(), 0);
				listCursoIdObrigatorioSecundario.add(Integer.parseInt(coluna.toString()));
				String S = tablePrimario.getValueAt(tablePrimario.getSelectedRow(), 0).toString();
				updateTableSecundario(Integer.parseInt(S));
				DefaultTableModel dtm = (DefaultTableModel) tablePrimario.getModel();
				dtm.removeRow(i);
				tablePrimario.setModel(dtm);
				System.out.println(listCursoIdObrigatorioSecundario);
			}
		});
		buttontoRight.setBounds(283, 279, 46, 23);
		frame.getContentPane().add(buttontoRight);

		JButton buttontoLeft = new JButton("<");
		buttontoLeft.setBackground(UIManager.getColor("Button.disabledShadow"));
		buttontoLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object coluna = tableSecundario.getValueAt(tableSecundario.getSelectedRow(), 0);
				int A = Integer.parseInt(coluna.toString());
				int index;
				updateTablePrimario(A);
				for (index = 0; index < listCursoIdObrigatorioSecundario.size(); index++) {
					int p = listCursoIdObrigatorioSecundario.get(index);

					if (p == A) {
						System.out.println(listCursoIdObrigatorioSecundario.get(index));
						// Remove.
						listCursoIdObrigatorioSecundario.remove(index);
						// Sai do loop.
						break;
					}
				}
				DefaultTableModel dtm = (DefaultTableModel) tableSecundario.getModel();
				dtm.removeRow(index);
				tableSecundario.setModel(dtm);
				System.out.println(listCursoIdObrigatorioSecundario);
			}
		});
		buttontoLeft.setBounds(283, 313, 46, 23);
		frame.getContentPane().add(buttontoLeft);

		choiceDepartamento.setBounds(162, 111, 315, 20);
		frame.getContentPane().add(choiceDepartamento);

		JLabel lblDepartamento = new JLabel("Departamento");
		lblDepartamento.setBounds(59, 111, 97, 14);
		frame.getContentPane().add(lblDepartamento);

		JScrollPane scrollPaneTerciario = new JScrollPane();
		scrollPaneTerciario.setBounds(632, 246, 231, 150);
		frame.getContentPane().add(scrollPaneTerciario);

		tableTerciario = new JTable();
		tableTerciario.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Mat\u00E9ria" }));
		tableTerciario.setBounds(0, 0, 1, 1);

		((JScrollPane) scrollPaneTerciario).setViewportView(tableTerciario);

		JButton buttonRight2 = new JButton(">");
		buttonRight2.setBackground(UIManager.getColor("Button.disabledShadow"));
		buttonRight2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object coluna = tableSecundario.getValueAt(tableSecundario.getSelectedRow(), 0);
				int A = Integer.parseInt(coluna.toString());
				int index;
				updateTableTerciario(A);
				for (index = 0; index < listCursoIdObrigatorioSecundario.size(); index++) {
					int p = listCursoIdObrigatorioSecundario.get(index);

					if (p == A) {
						System.out.println(listCursoIdObrigatorioSecundario.get(index));
						listCursoIdLivreTerciario.add(p);
						// Remove.
						listCursoIdObrigatorioSecundario.remove(index);
						// Sai do loop.
						break;
					}
				}
				DefaultTableModel dtm = (DefaultTableModel) tableSecundario.getModel();
				dtm.removeRow(index);
				tableSecundario.setModel(dtm);
				System.out.println("Secundario " + listCursoIdObrigatorioSecundario);
				System.out.println("Terciario " + listCursoIdLivreTerciario);
			}
		});
		buttonRight2.setBounds(574, 279, 46, 23);
		frame.getContentPane().add(buttonRight2);

		JButton buttonLeft2 = new JButton("<");
		buttonLeft2.setBackground(UIManager.getColor("Button.disabledShadow"));
		buttonLeft2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object coluna = tableTerciario.getValueAt(tableTerciario.getSelectedRow(), 0);
				int A = Integer.parseInt(coluna.toString());
				int index;
				updateTableSecundario(A);
				for (index = 0; index < listCursoIdLivreTerciario.size(); index++) {
					int p = listCursoIdLivreTerciario.get(index);

					if (p == A) {
						System.out.println(listCursoIdLivreTerciario.get(index));
						listCursoIdObrigatorioSecundario.add(p);
						// Remove.
						listCursoIdLivreTerciario.remove(index);
						// Sai do loop.
						break;
					}
				}
				DefaultTableModel dtm = (DefaultTableModel) tableTerciario.getModel();
				dtm.removeRow(index);
				tableTerciario.setModel(dtm);
				System.out.println("Secundario " + listCursoIdObrigatorioSecundario);
				System.out.println("Terciario " + listCursoIdLivreTerciario);
			}
		});
		buttonLeft2.setBounds(573, 313, 46, 23);
		frame.getContentPane().add(buttonLeft2);

		JLabel lblDisciplinasObrigatrias = new JLabel("Disciplinas Obrigat\u00F3rias");
		lblDisciplinasObrigatrias.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisciplinasObrigatrias.setBounds(339, 221, 225, 14);
		frame.getContentPane().add(lblDisciplinasObrigatrias);

		JLabel lblDisciplinasOptativas = new JLabel("Disciplinas Optativas");
		lblDisciplinasOptativas.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisciplinasOptativas.setBounds(632, 221, 231, 14);
		frame.getContentPane().add(lblDisciplinasOptativas);

		JLabel lblEscolhaAsDisciplinas = new JLabel("Escolha as Disciplinas do Curso");
		lblEscolhaAsDisciplinas.setHorizontalAlignment(SwingConstants.CENTER);
		lblEscolhaAsDisciplinas.setBounds(48, 221, 225, 14);
		frame.getContentPane().add(lblEscolhaAsDisciplinas);

		getUpdateTablePrimario();

	}

	public void getUpdateTablePrimario() {
		ds = new Datasource();
		new DAODisciplinas(ds);
		modelo = (DefaultTableModel) tablePrimario.getModel();
		modelo.setNumRows(0);
		for (Disciplinas BD2 : DAODisciplinas.listarDisciplinas()) {
			modelo.addRow(new Object[] { BD2.getIdDisciplinas(), BD2.getNome() });
		}
	}

	public void updateTablePrimario(int id) {
		ds = new Datasource();
		new DAODisciplinas(ds);
		modelo = (DefaultTableModel) tablePrimario.getModel();
		for (Disciplinas BD2 : DAODisciplinas.procurarDisciplinas(id)) {
			modelo.addRow(new Object[] { BD2.getIdDisciplinas(), BD2.getNome() });
		}
	}

	public void updateTableSecundario(int id) {
		ds = new Datasource();
		new DAODisciplinas(ds);
		modelo = (DefaultTableModel) tableSecundario.getModel();
		for (Disciplinas BD2 : DAODisciplinas.procurarDisciplinas(id)) {
			modelo.addRow(new Object[] { BD2.getIdDisciplinas(), BD2.getNome() });
		}
	}

	public void updateTableTerciario(int id) {
		ds = new Datasource();
		new DAODisciplinas(ds);
		modelo = (DefaultTableModel) tableTerciario.getModel();
		for (Disciplinas BD2 : DAODisciplinas.procurarDisciplinas(id)) {
			modelo.addRow(new Object[] { BD2.getIdDisciplinas(), BD2.getNome() });
		}
	}

	private void preencherchoice() {
		ds = new Datasource();
		new DAODepartamento(ds);
		new Departamento();

		for (Departamento dp : DAODepartamento.listarDepartamento()) {
			choiceDepartamento.add(dp.getNome());
			listid.add(dp.getIddepartamento());
			listnomes.add(dp.getNome());
		}
	}

}
