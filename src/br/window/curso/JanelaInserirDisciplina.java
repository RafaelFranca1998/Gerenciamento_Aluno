package br.window.curso;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.dao.Datasource;
import br.dao.DAOAluno;
import br.dao.DAODisciplinas;
import br.model.Disciplinas;
import javax.swing.UIManager;

public class JanelaInserirDisciplina extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7889475273883412172L;
	private JFrame frame;
	private TextField textFieldNomedoCurso;
	int i;

	Datasource ds;
	DAOAluno daoAluno;
	DAODisciplinas daoDisciplinas;

	/**
	 * Launch the application.
	 */

	public static void run() {
		try {
			JanelaInserirDisciplina window = new JanelaInserirDisciplina();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaInserirDisciplina() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Inserir Disciplina");
		frame.setResizable(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frame.setBounds((d.width / 2) - 300, (d.height / 2) - 150, 434, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnFechar.setBounds(293, 117, 89, 23);
		frame.getContentPane().add(btnFechar);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ds = new Datasource();
				daoDisciplinas = new DAODisciplinas(ds);
				Disciplinas BDcurso = new Disciplinas();
				try {

					BDcurso.setNome(textFieldNomedoCurso.getText());
					daoDisciplinas.create(BDcurso);
					JOptionPane.showMessageDialog(frame, "Adicionado!", "Menssagem", 1);
					textFieldNomedoCurso.setText("");
				} catch (Exception a) {
					JOptionPane.showMessageDialog(frame, "Preencha todos os campos", "Erro!", 2);
					System.err.println(a.getMessage());
				}
			}
		});
		btnAdicionar.setBounds(127, 117, 89, 23);
		frame.getContentPane().add(btnAdicionar);

		textFieldNomedoCurso = new TextField();
		textFieldNomedoCurso.setBounds(127, 55, 281, 20);
		frame.getContentPane().add(textFieldNomedoCurso);
		textFieldNomedoCurso.setColumns(10);

		JLabel lblNomedoCurso = new JLabel("Nome da Disciplina");
		lblNomedoCurso.setBounds(10, 55, 206, 14);
		frame.getContentPane().add(lblNomedoCurso);

	}
}
