package br.window.departamento;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.dao.Datasource;
import br.dao.DAODepartamento;
import br.model.Departamento;

public class JanelaGerenciaDepartamento {

	private JFrame frame;
	private JTable table;
	Datasource ds;
	DAODepartamento daoDepartamento;
	static Atualiza A;

	/**
	 * Launch the application.
	 */

			public static void run() {
				try {
					JanelaGerenciaDepartamento window = new JanelaGerenciaDepartamento();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


	/**
	 * Create the application.
	 */
	public JanelaGerenciaDepartamento() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frame.setBounds((d.width / 2) - 315, (d.height / 2) - 200, 630, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		A =  new Atualiza();
		initThread();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 11, 372, 322);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"ID", "Nome", "Descri\u00E7\u00E3o"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnInserirDepartamento = new JButton("Inserir Departamento");
		btnInserirDepartamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JanelaInserirDepartamento.run();
			}
		});
		btnInserirDepartamento.setBounds(424, 255, 163, 23);
		frame.getContentPane().add(btnInserirDepartamento);
		
		JButton btnRemoverDepartamento = new JButton("Remover Departamento");
		btnRemoverDepartamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean execute =  true;
			try {
				ds =  new Datasource();
				daoDepartamento =  new  DAODepartamento(ds);
				if (JOptionPane.showConfirmDialog(table,
						"Esta Ação não poderá ser desfeita! \n Deseja remover o Departamento?", "Atenção!",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {					
					Departamento D =  new Departamento();
					Object obj =  table.getValueAt(table.getSelectedRow(), 0);
					D.setIddepartamento(Integer.parseInt(obj.toString()));
					daoDepartamento.delete(D);
					
				}
				execute = true;
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(scrollPane, "Erro", "Erro ao Remover", JOptionPane.ERROR_MESSAGE);
				execute = false;
			}finally {	
				if (execute) {
					initThread();					
				}
			}	
			}
		});
		btnRemoverDepartamento.setBounds(424, 289, 163, 23);
		frame.getContentPane().add(btnRemoverDepartamento);
	}
	
	public static void initThread() {
		Thread threadA = new Thread(A);
		threadA.start();
	}
	
	public class Atualiza implements Runnable {
	    public void run () {
	    		ds = new Datasource();
	    		daoDepartamento = new DAODepartamento(ds);
	    		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
	    		modelo.setNumRows(0);
	    		for (Departamento BD2 : DAODepartamento.listarDepartamento()) {
	    			modelo.addRow(new Object[] { BD2.getIddepartamento(), BD2.getNome(), BD2.getDescricao()});
	    		}
	    }
	}
}
