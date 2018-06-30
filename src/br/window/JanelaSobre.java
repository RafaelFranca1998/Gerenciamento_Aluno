package br.window;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.ImageIcon;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class JanelaSobre {

	private JFrame frame = new JFrame("Programa Aluno 2.0");

	/**
	 * Launch the application.
	 */

	public static void run() {
		try {
			JanelaSobre window = new JanelaSobre();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public JanelaSobre() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);

		frame.setBounds((d.width / 2) - 250, (d.height / 2) - 170, 500, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblSobre = new JLabel("");
		lblSobre.setIcon(new ImageIcon(JanelaSobre.class.getResource("/src/resources/sobre.png")));
		lblSobre.setBounds(388, 33, 56, 70);
		frame.getContentPane().add(lblSobre);

		JLabel lblSobre2 = new JLabel("");
		lblSobre2.setIcon(new ImageIcon(JanelaSobre.class.getResource("/src/resources/aluno.png")));
		lblSobre2.setBounds(347, 33, 67, 100);
		frame.getContentPane().add(lblSobre2);

		JLabel lblVersion = new JLabel("Version 2.0.0");
		lblVersion.setBounds(100, 49, 112, 47);
		frame.getContentPane().add(lblVersion);

		JLabel lblNome = new JLabel("Programa Aluno");
		lblNome.setFont(new Font("Sitka Banner", Font.PLAIN, 20));
		lblNome.setBounds(90, 33, 134, 35);
		frame.getContentPane().add(lblNome);

		JLabel lblCreatedBy = new JLabel("Created By:");
		lblCreatedBy.setBounds(77, 119, 75, 14);
		frame.getContentPane().add(lblCreatedBy);

		JLabel lblcreator1 = new JLabel("-Vitor Cruz");
		lblcreator1.setBounds(149, 119, 103, 14);
		frame.getContentPane().add(lblcreator1);

		JLabel lblcreator2 = new JLabel("-Rafael Fran\u00E7a");
		lblcreator2.setBounds(149, 138, 103, 14);
		frame.getContentPane().add(lblcreator2);

		JLabel lblCreator3 = new JLabel("-Washington Neto");
		lblCreator3.setBounds(149, 159, 103, 14);
		frame.getContentPane().add(lblCreator3);

		JButton btnFechar = new JButton("Fechar");

		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnFechar.setBounds(182, 250, 89, 23);
		btnFechar.setBackground(UIManager.getColor("Button.disabledShadow"));
		frame.getContentPane().add(btnFechar);
		
		JLabel lblCreator4 = new JLabel("-Leonardo Luiz Oliveira");
		lblCreator4.setBounds(149, 178, 147, 14);
		frame.getContentPane().add(lblCreator4);
		
		JLabel lblCreator5 = new JLabel("-Felipe Araujo");
		lblCreator5.setBounds(149, 196, 103, 14);
		frame.getContentPane().add(lblCreator5);
	}
}
