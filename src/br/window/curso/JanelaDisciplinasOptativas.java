package br.window.curso;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import br.dao.Datasource;
import br.dao.DAOCursos;
import br.model.Disciplinas;

public class JanelaDisciplinasOptativas {

	private JFrame frame;
	JTable table = new JTable();

	/**
	 * Launch the application.
	 */

	public static void run(int id) {
		try {
			JanelaDisciplinasOptativas window = new JanelaDisciplinasOptativas(id);
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaDisciplinasOptativas(int id) {
		initialize(id);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int id) {
		int idCurso = id;
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds((d.width / 2) - 225, (d.height / 2) - 150, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 444, 200);
		scrollPane.setViewportView(table);

		frame.getContentPane().add(scrollPane);

		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setModel(
				new DefaultTableModel(new Object[][] { { null, null }, { null, null }, { null, null }, { null, null },
						{ null, null }, { null, null }, { null, null }, }, new String[] { "Disciplina Optativa" }) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 272139891352332087L;
					boolean[] columnEditables = new boolean[] { false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});

		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnFechar.setBounds(170, 227, 89, 23);
		btnFechar.setBackground(UIManager.getColor("Button.disabledShadow"));

		frame.getContentPane().add(btnFechar);
		updateTableAlunoCurso(idCurso);
	}

	private void updateTableAlunoCurso(int idCurso) {
		Datasource ds = new Datasource();
		new DAOCursos(ds);
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setNumRows(0);
		for (Object cs2 : DAOCursos.listarDisciplinasOptativas(idCurso)) {
			modelo.addRow(new Object[] { (((Disciplinas) cs2).getNome()) });
		}
	}

}
