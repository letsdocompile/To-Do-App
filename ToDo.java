import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
class Task {
	private String text;
	private boolean completed;

	public Task(String text) {
		this.text = text;
		this.completed = false;
	}

	public String getText() {
		return text;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
public class ToDo {

	private JFrame frmTodoApp;
	private JTextField tf;
	private DefaultListModel<Task> dm = new DefaultListModel<>();
	private int row = -1;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				ToDo window = new ToDo();
				window.frmTodoApp.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public ToDo() {
		initialize();
	}

	private void initialize() {
		frmTodoApp = new JFrame();
		frmTodoApp.getContentPane().setBackground(new Color(250, 235, 215));
		frmTodoApp.setTitle("To-do App");
		frmTodoApp.setBounds(100, 100, 445, 581);
		frmTodoApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTodoApp.getContentPane().setLayout(null);
		frmTodoApp.setResizable(false);

		JLabel title = new JLabel("TO-Do List");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		title.setBounds(165, 11, 96, 20);
		frmTodoApp.getContentPane().add(title);

		tf = new JTextField();
		tf.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tf.setBounds(13, 42, 287, 36);
		frmTodoApp.getContentPane().add(tf);
		tf.setColumns(10);

		JButton add = new JButton("Add Task");
		add.setFont(new Font("Tahoma", Font.BOLD, 12));
		add.setBounds(311, 43, 96, 36);
		frmTodoApp.getContentPane().add(add);

		JButton clrAll = new JButton("Clear All");
		clrAll.setFont(new Font("Tahoma", Font.BOLD, 12));
		clrAll.setBounds(271, 84, 136, 36);
		frmTodoApp.getContentPane().add(clrAll);

		JButton dlt = new JButton("X");
		dlt.setFont(new Font("Tahoma", Font.BOLD, 12));
		dlt.setBounds(135, 84, 126, 36);
		frmTodoApp.getContentPane().add(dlt);

		JButton check = new JButton("☑");
		check.setFont(new Font("Tahoma", Font.BOLD, 12));
		check.setBounds(13, 84, 112, 36);
		frmTodoApp.getContentPane().add(check);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 133, 337, 383);
		frmTodoApp.getContentPane().add(scrollPane);

		JList<Task> list = new JList<>();
		list.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(list);
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		list.setFixedCellHeight(30);
		list.setModel(dm);

		list.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);
				Task task = (Task) value;
				if (task.isCompleted()) {
					label.setText("<html><strike>" + task.getText() + "</strike></html>");
				} else {
					label.setText(task.getText());
				}
				return label;
			}
		});


		add.addActionListener(e -> {
			String task = tf.getText().trim();
			if (task.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Enter a Task");
				tf.requestFocus();
			} else {
				dm.addElement(new Task(task));
				tf.setText("");
			}
		});


		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				row = list.getSelectedIndex();
			}
		});


		check.addActionListener(e -> {
			if (row != -1) {
				Task selected = dm.get(row);
				selected.setCompleted(true);
				list.repaint();
			}
			else {
				JOptionPane.showMessageDialog(null, "Select a List");
			}
		});


		dlt.addActionListener(e -> {
			if (row != -1) {
				dm.remove(row);
				row = -1;
			}
			else {
				JOptionPane.showMessageDialog(null, "Select a List");
			}
		});


		clrAll.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(frmTodoApp, "Clear all tasks?", "Confirm",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				dm.clear();
			}
		});
	}
}
