package br.window;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Label;
import java.awt.SystemColor;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import br.dao.DAOAluno;
import br.dao.DAOCursos;
import br.dao.Datasource;
import br.model.Aluno;
import br.model.Curso;
import br.window.aluno.JanelaEditarAluno;
import br.window.aluno.JanelaInserirAluno;
import br.window.curso.JanelaAlunosCurso;
import br.window.curso.JanelaDisciplinasObrigatorias;
import br.window.curso.JanelaDisciplinasOptativas;
import br.window.curso.JanelaGerenciaCurso;
import br.window.departamento.JanelaGerenciaDepartamento;

public class JanelaGerenciamentoAluno {
	private static JFrame frmGerenciamentoAluno = new JFrame();
	private TextField textFieldPesquisa = new TextField();

	private static JTable tabelaResultado = new JTable();

	public JScrollPane scrollPaneAlunos = new JScrollPane();
	public JScrollPane scrollPaneCursos = new JScrollPane();

	private Label lblPesquisar = new Label("Pesquisar");
	private JLabel lblFiltroCurso = new JLabel("Curso");
	private JLabel lblPesquisarNome = new JLabel("Pesquisar Nome");
	private JLabel lblFiltroTurno = new JLabel("Turno");
	private JLabel lblFiltrosDePesquisa = new JLabel("Filtros de Pesquisa");
	private Label lblLabelInicio = new Label("Inicio");
	private JLabel lblImage = new JLabel("");
	JLabel labelIdCurso = new JLabel("");
	private Choice choicePesquisarCurso = new Choice();;
	private static JButton btnRemover = new JButton("Remover");
	private static JButton btnEditar = new JButton("Editar");

	private JPanel panelPesquisar = new JPanel();
	private JPanel panelCursos = new JPanel();
	private JPanel panelMenu = new JPanel();

	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnInicio = new JMenu("Inicio");
	private JMenu mnAjuda = new JMenu("Ajuda");

	private JMenuItem mntmSair = new JMenuItem("Sair");
	private JMenuItem mntmSobre = new JMenuItem("Sobre...");

	private JCheckBox chckbxMatutino = new JCheckBox("Matutino");
	private JCheckBox chckbxVespertino = new JCheckBox("Vespertino");
	private JCheckBox chckbxNoturno = new JCheckBox("Noturno");

	private static Datasource ds;
	private static DAOAluno daoAluno;
	private Aluno BDAlunos;
	private int id = -1;
	private int idCurso = -1;
	private boolean atualizado, clickado1, clickado2, clickado3;

	private JPopupMenu popupMenuCursos = new JPopupMenu();
	private final JPopupMenu popupMenuAluno = new JPopupMenu();
	private Label lblFerramentas;
	private JTable tableCursos;
	private final JLabel lblSistemaAluno = new JLabel("Sistema Aluno");
	private final JMenuItem menuItem_3 = new JMenuItem("Editar");
	private final JMenuItem menuItem_4 = new JMenuItem("Remover");
	private static JButton btnDeslogar = new JButton("Deslogar");
	private JButton btnGerenciarCursos = new JButton("Gerenciar Cursos");
	private final JButton btnInserirAluno = new JButton("Inserir Aluno");
	private final JButton btnGerenciarDepartamento = new JButton("Gerenciar Departamentos");

	/**
	 * Launch the application.
	 */

	public static void run() {
		try {
			new JanelaGerenciamentoAluno();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaGerenciamentoAluno() {
		initialize();
		atualizarTabelaAluno();
	}

	/**
	 * Preenche a tabela com dados e atualiza.
	 */
	public static void atualizarTabelaAluno() {
		ds = new Datasource();
		daoAluno = new DAOAluno(ds);
		DefaultTableModel modelo = (DefaultTableModel) tabelaResultado.getModel();
		modelo.setNumRows(0);
		for (Aluno BD2 : DAOAluno.listarAlunos()) {
			modelo.addRow(new Object[] { BD2.getId_aluno(), BD2.getNome(), BD2.getRg(), BD2.getCpf(),
					BD2.getDataNascimento(), BD2.getidCurso(), BD2.getNomeCurso() });
		}
	}

	public void updateTableCursos() {
		ds = new Datasource();
		new DAOCursos(ds);
		DefaultTableModel modelo = (DefaultTableModel) tableCursos.getModel();
		modelo.setNumRows(0);
		for (Curso cs2 : DAOCursos.listarCursos()) {
			modelo.addRow(new Object[] { cs2.getIdCurso(), cs2.getNome() });
		}
	}

	public void updateByCurso(int idCurso) {
		ds = new Datasource();
		new DAOAluno(ds);
		DefaultTableModel modelo = (DefaultTableModel) tabelaResultado.getModel();
		modelo.setNumRows(0);
		for (Aluno BD2 : daoAluno.listarPeloCurso(idCurso)) {
			modelo.addRow(new Object[] { BD2.getId_aluno(), BD2.getNome(), BD2.getRg(), BD2.getCpf(),
					BD2.getDataNascimento(), BD2.getidCurso(), BD2.getNomeCurso() });
		}
	}

	private void updateByName(String name) {
		ds = new Datasource();
		daoAluno = new DAOAluno(ds);
		BDAlunos = new Aluno();
		DefaultTableModel modelo = (DefaultTableModel) tabelaResultado.getModel();
		modelo.setNumRows(0);
		for (Aluno BD2 : daoAluno.listarPeloNome(name)) {
			modelo.addRow(new Object[] { BD2.getId_aluno(), BD2.getNome(), BD2.getDataNascimento(), BD2.getidCurso(),
					BD2.getNomeCurso() });
		}
	}

	private void preencerChoiceCursos() {
		ds = new Datasource();
		new DAOCursos(ds);
		choicePesquisarCurso.add("Nenhum");
		for (Curso C : DAOCursos.listarCursos()) {
			choicePesquisarCurso.add(C.getNome());
		}
	}

	public static void enableWindow(boolean b) {
		frmGerenciamentoAluno.setEnabled(b);
		atualizarTabelaAluno();
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frmGerenciamentoAluno.setBounds((d.width / 2) - 530, (d.height / 2) - 300, 1200, 600);
		frmGerenciamentoAluno.getContentPane().setBackground(Color.WHITE);
		frmGerenciamentoAluno.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmGerenciamentoAluno.setBackground(Color.BLACK);

		clickado1 = true;

		btnRemover.setVisible(true);
		btnEditar.setVisible(true);
		btnDeslogar.setVisible(true);
		frmGerenciamentoAluno.getContentPane().add(panelPesquisar);
		panelPesquisar.setVisible(false);

		panelPesquisar.setBorder(UIManager.getBorder("ToolTip.border"));
		panelPesquisar.setBackground(Color.WHITE);
		panelPesquisar.setBounds(900, 60, 290, 422);
		panelPesquisar.setLayout(null);

		lblFiltroCurso.setBounds(20, 111, 123, 14);
		panelPesquisar.add(lblFiltroCurso);

		lblFiltroTurno.setBounds(21, 169, 46, 14);

		panelPesquisar.add(lblFiltroTurno);

		chckbxMatutino.setBackground(Color.WHITE);
		chckbxMatutino.setBounds(20, 190, 97, 23);
		panelPesquisar.add(chckbxMatutino);

		chckbxVespertino.setBackground(Color.WHITE);
		chckbxVespertino.setBounds(20, 227, 97, 23);
		panelPesquisar.add(chckbxVespertino);

		chckbxNoturno.setBackground(Color.WHITE);
		chckbxNoturno.setForeground(Color.BLACK);
		chckbxNoturno.setBounds(20, 264, 97, 23);
		panelPesquisar.add(chckbxNoturno);
		textFieldPesquisa.setBounds(20, 38, 175, 20);
		panelPesquisar.add(textFieldPesquisa);
		panelCursos.setBorder(UIManager.getBorder("ToolTip.border"));
		panelCursos.setBackground(Color.WHITE);

		panelCursos.setVisible(true);
		textFieldPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String name = textFieldPesquisa.getText();
				if (name.equals("")) {
					atualizarTabelaAluno();
				} else {
					updateByName(name);
					if (e.getKeyChar() == KeyEvent.VK_ENTER) {
						if (name.equals("")) {
							atualizarTabelaAluno();
						} else {
							updateByName(name);
						}
					}
				}
			}
		});
		textFieldPesquisa.setColumns(10);
		lblPesquisarNome.setBounds(20, 18, 115, 14);
		panelPesquisar.add(lblPesquisarNome);
		lblFiltrosDePesquisa.setBounds(20, 86, 115, 14);
		panelPesquisar.add(lblFiltrosDePesquisa);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = textFieldPesquisa.getText();
				if (name.equals("")) {
					atualizarTabelaAluno();
				} else {
					updateByName(name);
				}

			}
		});
		btnPesquisar.setBounds(201, 38, 79, 23);
		panelPesquisar.add(btnPesquisar);
		choicePesquisarCurso.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (choicePesquisarCurso.getSelectedItem().equals("Nenhum")) {
					atualizarTabelaAluno();
				} else {
					ds = new Datasource();
					new DAOCursos(ds);
					String curso = choicePesquisarCurso.getSelectedItem();
					ArrayList<Curso> list = DAOCursos.listarCursos(curso);
					Curso C = (Curso) list.get(0);
					updateByCurso(C.getIdCurso());
				}
			}
		});

		choicePesquisarCurso.setBounds(20, 131, 226, 20);
		panelPesquisar.add(choicePesquisarCurso);

		labelIdCurso.setBounds(244, 137, 46, 14);
		panelPesquisar.add(labelIdCurso);

		panelCursos.setBounds(900, 60, 290, 422);
		frmGerenciamentoAluno.getContentPane().add(panelCursos);
		panelCursos.setLayout(null);

		scrollPaneCursos.setBounds(0, 0, 290, 422);
		panelCursos.add(scrollPaneCursos);

		tableCursos = new JTable();
		scrollPaneCursos.setColumnHeaderView(tableCursos);

		tableCursos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableCursos.setModel(new DefaultTableModel(new Object[][] { { null, null }, { null, null }, { null, null },
				{ null, null }, { null, null }, { null, null }, }, new String[] { "ID", "Curso" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableCursos.getColumnModel().getColumn(0).setResizable(false);
		tableCursos.getColumnModel().getColumn(0).setPreferredWidth(32);
		tableCursos.getColumnModel().getColumn(1).setPreferredWidth(190);
		tableCursos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenuCursos.show(tableCursos, e.getX(), e.getY());
					int col = tableCursos.columnAtPoint(e.getPoint());
					int row = tableCursos.rowAtPoint(e.getPoint());
					if (col != -1 && row != -1) {
						tableCursos.setColumnSelectionInterval(col, col);
						tableCursos.setRowSelectionInterval(row, row);
						Object coluna = tableCursos.getValueAt(tableCursos.getSelectedRow(), 0);
						idCurso = Integer.parseInt(coluna.toString());
					}
				}
				Object coluna = tableCursos.getValueAt(tableCursos.getSelectedRow(), 0);
				idCurso = Integer.parseInt(coluna.toString());
				System.out.println(idCurso);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (atualizado) {
					atualizarTabelaAluno();
					atualizado = false;
				}
			}
		});
		scrollPaneCursos.setViewportView(tableCursos);

		popupMenuCursos.setBounds(0, 0, 200, 50);

		JMenuItem menuItem = new JMenuItem("Ver Alunos");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JanelaAlunosCurso.run(idCurso);
			}
		});
		popupMenuCursos.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("Ver Disciplinas Obrigatórias");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JanelaDisciplinasObrigatorias.run(idCurso);
			}
		});
		popupMenuCursos.add(menuItem_1);

		JMenuItem menuItem_2 = new JMenuItem("Ver Disciplinas Opcionais");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JanelaDisciplinasOptativas.run(idCurso);
			}
		});
		popupMenuCursos.add(menuItem_2);

		addPopup(scrollPaneCursos, popupMenuCursos);
		JPanel panelFerramentas = new JPanel();
		panelFerramentas.setBackground(Color.WHITE);
		panelFerramentas.setBorder(UIManager.getBorder("ToolTip.border"));
		panelFerramentas.setForeground(Color.WHITE);
		panelFerramentas.setBounds(900, 60, 290, 422);
		frmGerenciamentoAluno.getContentPane().add(panelFerramentas);
		panelFerramentas.setVisible(false);

		panelFerramentas.setLayout(null);
		btnInserirAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JanelaInserirAluno.run();
			}
		});
		btnGerenciarCursos.setBounds(63, 71, 190, 24);
		panelFerramentas.add(btnGerenciarCursos);

		btnGerenciarCursos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JanelaGerenciaCurso.run();
			}
		});
		btnGerenciarCursos.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnInserirAluno.setBounds(63, 197, 190, 25);
		btnInserirAluno.setBackground(UIManager.getColor("Button.disabledShadow"));
		panelFerramentas.add(btnInserirAluno);
		btnGerenciarDepartamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JanelaGerenciaDepartamento.run();
			}
		});
		btnGerenciarDepartamento.setBounds(63, 136, 190, 25);
		btnGerenciarDepartamento.setBackground(UIManager.getColor("Button.disabledShadow"));

		panelFerramentas.add(btnGerenciarDepartamento);

		frmGerenciamentoAluno.getContentPane().add(btnEditar);
		frmGerenciamentoAluno.getContentPane().setLayout(null);
		frmGerenciamentoAluno.setResizable(false);
		frmGerenciamentoAluno.setTitle("Programa Aluno");
		frmGerenciamentoAluno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frmGerenciamentoAluno.getContentPane().add(btnRemover);
		frmGerenciamentoAluno.getContentPane().add(scrollPaneAlunos);
		frmGerenciamentoAluno.getContentPane().add(btnDeslogar);
		menuBar.setMargin(new Insets(2, 2, 2, 2));
		frmGerenciamentoAluno.setJMenuBar(menuBar);

		BDAlunos = new Aluno();
		if (JanelaPrincipalLogin.isAdministrator()) {
			scrollPaneCursos.add(popupMenuCursos);
		}

		popupMenuAluno.setBounds(0, 0, 97, 50);
		scrollPaneAlunos.add(popupMenuAluno);
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (id != -1) {
					JanelaEditarAluno.run(id);
					System.out.println("[Log] ID: " + id);
				} else {
					JOptionPane.showMessageDialog(frmGerenciamentoAluno, "Selecione uma pessoa", "Falha",
							JOptionPane.INFORMATION_MESSAGE);
				}
				atualizado = true;
			}
		});

		popupMenuAluno.add(menuItem_3);
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ds = new Datasource();
					daoAluno = new DAOAluno(ds);
					Object coluna = tabelaResultado.getValueAt(tabelaResultado.getSelectedRow(), 0);
					if (JOptionPane.showConfirmDialog(tabelaResultado,
							"Esta Ação não poderá ser desfeita! \n Deseja remover o Livro da Lista?", "Atenção!",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						int id = Integer.parseInt(coluna.toString());

						System.out.println("[Log] ID= " + id + JOptionPane.YES_OPTION);
						BDAlunos.setId_aluno(id);
						daoAluno.deletar(BDAlunos);
						atualizarTabelaAluno();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(tabelaResultado, "Selecione uma pessoa:");
				}
			}
		});

		popupMenuAluno.add(menuItem_4);

		if (tabelaResultado.getCellSelectionEnabled()) {
			btnRemover.setEnabled(true);
		}
		scrollPaneAlunos.setBounds(184, 60, 706, 422);
		btnEditar.setBounds(312, 497, 89, 23);
		btnRemover.setBounds(605, 496, 89, 24);
		btnRemover.setBackground(UIManager.getColor("Button.disabledShadow"));

		btnRemover.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				try {
					ds = new Datasource();
					daoAluno = new DAOAluno(ds);
					Object coluna = tabelaResultado.getValueAt(tabelaResultado.getSelectedRow(), 0);
					JOptionPane pane = new JOptionPane();
					if (pane.showConfirmDialog(tabelaResultado,
							"Esta Ação não poderá ser desfeita! \n Deseja remover o Livro da Lista?", "Atenção!",
							pane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

						int id = Integer.parseInt(coluna.toString());

						System.out.println("[Log] ID= " + id + pane.YES_OPTION);
						BDAlunos.setId_aluno(id);
						daoAluno.deletar(BDAlunos);
						atualizarTabelaAluno();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(tabelaResultado, "Selecione uma pessoa:");
				}
			}
		});
		tabelaResultado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenuAluno.show(tabelaResultado, e.getX(), e.getY());
					int col = tabelaResultado.columnAtPoint(e.getPoint());
					int row = tabelaResultado.rowAtPoint(e.getPoint());
					if (col != -1 && row != -1) {
						tabelaResultado.setColumnSelectionInterval(col, col);
						tabelaResultado.setRowSelectionInterval(row, row);
					}
				}
				Object coluna = tabelaResultado.getValueAt(tabelaResultado.getSelectedRow(), 0);
				id = Integer.parseInt(coluna.toString());
				System.out.println(id);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (atualizado) {
					atualizarTabelaAluno();
					atualizado = false;
				}
			}
		});

		tabelaResultado.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tabelaResultado.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, { null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null }, },
				new String[] { "ID", "Nome", "RG", "CPF", "Idade", "Id do Curso", "Nome do Curso" }));
		tabelaResultado.getColumnModel().getColumn(0).setResizable(false);
		tabelaResultado.getColumnModel().getColumn(0).setPreferredWidth(34);
		tabelaResultado.getColumnModel().getColumn(1).setPreferredWidth(105);
		tabelaResultado.getColumnModel().getColumn(2).setPreferredWidth(100);
		tabelaResultado.getColumnModel().getColumn(3).setPreferredWidth(100);
		tabelaResultado.getColumnModel().getColumn(5).setPreferredWidth(70);
		tabelaResultado.getColumnModel().getColumn(6).setPreferredWidth(100);
		scrollPaneAlunos.setViewportView(tabelaResultado);

		btnEditar.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnDeslogar.setBackground(UIManager.getColor("Button.disabledShadow"));

		btnDeslogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipalLogin.admLogout();
				JanelaPrincipalLogin.loginAsADM();
				frmGerenciamentoAluno.dispose();
			}
		});
		btnDeslogar.setBounds(1095, 11, 89, 23);
		frmGerenciamentoAluno.getContentPane().add(panelMenu);

		panelMenu.setBorder(new LineBorder(new Color(1, 1, 1)));
		panelMenu.setBackground(Color.WHITE);
		panelMenu.setBounds(10, 60, 164, 479);
		panelMenu.setLayout(null);

		lblLabelInicio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLabelInicio.setAlignment(Label.CENTER);
		lblLabelInicio.setBackground(UIManager.getColor("activeCaption"));
		lblLabelInicio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (clickado1) {

				} else {
					lblLabelInicio.setBackground(UIManager.getColor("activeCaption"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (clickado1) {

				} else {
					lblLabelInicio.setBackground(Color.WHITE);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				panelPesquisar.setVisible(false);
				panelCursos.setVisible(true);
				panelFerramentas.setVisible(false);

				clickado1 = true;
				clickado2 = false;
				clickado3 = false;
				lblLabelInicio.setBackground(UIManager.getColor("activeCaption"));
				lblFerramentas.setBackground(SystemColor.window);
				lblPesquisar.setBackground(SystemColor.window);
				updateTableCursos();
			}
		});
		lblLabelInicio.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 20));
		lblLabelInicio.setBounds(1, 38, 162, 41);
		panelMenu.add(lblLabelInicio);

		lblPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblPesquisar.setBackground(SystemColor.text);
		lblPesquisar.setAlignment(Label.CENTER);
		lblPesquisar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickado1 = false;
				clickado2 = true;
				clickado3 = false;
				panelPesquisar.setVisible(true);
				panelCursos.setVisible(false);
				panelFerramentas.setVisible(false);
				lblPesquisar.setBackground(UIManager.getColor("activeCaption"));
				lblLabelInicio.setBackground(SystemColor.window);
				lblFerramentas.setBackground(SystemColor.window);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (clickado2) {

				} else {
					lblPesquisar.setBackground(SystemColor.text);
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (clickado2) {

				} else {
					lblPesquisar.setBackground(UIManager.getColor("activeCaption"));
				}

			}
		});
		lblPesquisar.setFont(new Font("Yu Gothic Light", Font.PLAIN, 20));
		lblPesquisar.setBounds(1, 91, 162, 41);
		panelMenu.add(lblPesquisar);

		lblFerramentas = new Label("Ferramentas");
		lblFerramentas.setAlignment(Label.CENTER);
		lblFerramentas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblFerramentas.setBackground(SystemColor.window);
		lblFerramentas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clickado1 = false;
				clickado2 = false;
				clickado3 = true;

				panelFerramentas.setVisible(true);
				panelPesquisar.setVisible(false);
				panelCursos.setVisible(false);
				lblFerramentas.setBackground(UIManager.getColor("activeCaption"));
				lblLabelInicio.setBackground(SystemColor.window);
				lblPesquisar.setBackground(SystemColor.window);

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (clickado3) {

				} else {
					lblFerramentas.setBackground(UIManager.getColor("activeCaption"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (clickado3) {

				} else {
					lblFerramentas.setBackground(SystemColor.window);
				}
			}
		});
		lblFerramentas.setFont(new Font("Yu Gothic Light", Font.PLAIN, 20));
		lblFerramentas.setBounds(1, 140, 162, 37);
		panelMenu.add(lblFerramentas);
		lblImage.setBounds(54, 298, 89, 144);
		panelMenu.add(lblImage);

		lblImage.setIcon(new ImageIcon(JanelaGerenciamentoAluno.class.getResource("/src/resources/aluno.png")));
		lblSistemaAluno.setBounds(30, 298, 133, 29);
		panelMenu.add(lblSistemaAluno);
		lblSistemaAluno.setFont(new Font("Sitka Banner", Font.PLAIN, 20));

		menuBar.add(mnInicio);
		mntmSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmGerenciamentoAluno.dispose();
			}
		});

		mnInicio.add(mntmSair);

		menuBar.add(mnAjuda);
		mntmSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JanelaSobre.run();
			}
		});

		mnAjuda.add(mntmSobre);

		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (id != -1) {
					JanelaEditarAluno.run(id);
					System.out.println("[Log] ID: " + id);
				} else {
					JOptionPane.showMessageDialog(frmGerenciamentoAluno, "Selecione uma pessoa", "Falha",
							JOptionPane.INFORMATION_MESSAGE);
				}
				atualizado = true;
			}
		});
		frmGerenciamentoAluno.setVisible(true);
		updateTableCursos();
		preencerChoiceCursos();
	}
}
