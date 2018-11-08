import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private ElementController elControl = new ElementController();
	private JLabel lblMaxWeight;

	public MainFrame() {
			
		setTitle("Backpack");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
			
		//Question section
		JLabel lblQuestion = new JLabel("Escolha a opção");
		lblQuestion.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblQuestion);
			
		//separating label and first button
		contentPane.add(Box.createRigidArea(new Dimension(40,40)));
		
		//Button to generate objects
		JButton btnAdd = new JButton("Simular");
		btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnAdd);
		
		
		//separating label and first table
		contentPane.add(Box.createRigidArea(new Dimension(40,40)));
		
		//Backpack max weight label
		JLabel lblBack = new JLabel("Peso máximo da mochila");
		lblBack.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblBack.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBack);
		lblMaxWeight = new JLabel("");
		lblMaxWeight.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblMaxWeight.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblMaxWeight);
		
		//Table1 title
		JLabel table1Title = new JLabel("Tabela com todos os objetos");
		table1Title.setAlignmentX(Component.CENTER_ALIGNMENT);
		table1Title.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(table1Title);		
		//Table 1
		JTable tableObjects = new JTable();
	    tableObjects.setMaximumSize(new Dimension(500, 150 ));
		contentPane.add(tableObjects);
		
		//separating second table and first table
		contentPane.add(Box.createRigidArea(new Dimension(40,40)));
		
		//Table2 title
		JLabel table2Title = new JLabel("Tabela com todos os objetos");
		table2Title.setAlignmentX(Component.CENTER_ALIGNMENT);
		table2Title.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(table2Title);
		//Table 2
		JTable tableResult= new JTable();
		contentPane.add(tableResult);
		
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//get max weight
				int max_weight = elControl.getRandomInt(12) + 8;
				lblMaxWeight.setText(String.valueOf(max_weight));
				
				//get Objects
				elControl.generateRandomElements();
				int weights[] = elControl.getWeights();
				int values[] = 	elControl.getValues();	
				
				//get result
				int[][] results = elControl.generateResultsMatrix(max_weight);
				
				//show data
				tableObjects.setModel(getTableModelObjects(weights, values));
				tableResult.setModel(getTableModelMatrix(results, weights, max_weight));
				
				//show itens taken
				elControl.searchTaken(results, max_weight);
				showItensTaken(tableResult);
			}
		});

	}
	
	private DefaultTableModel getTableModelObjects(int[] weights, int[] values) {
		
		Vector<String> COLUMN_NAME_VECTOR = new Vector<String>(
				Arrays.asList(new String[] {"A", "B","C"})
		);
		
		Vector<Vector<String>> matrix = new Vector<Vector<String>>();
		
				
		for (int i = 0; i < weights.length + 1; i++) {
			Vector<String> row = new Vector<String>();
			for (int j = 0; j < 3; j++) {

				String rowText = " ";
				
				if(j==0) {
					if(i==0) {
						rowText = "Index";
					} else {
						rowText = String.valueOf(i);
					}
					
				} else if( j==1) {
					if(i==0) {
						rowText = "Valor";
					} else {
						rowText = String.valueOf(values[i-1]);
					}
	
				} else {
					if(i==0) {
						rowText = "Peso";
					} else {
						rowText = String.valueOf(weights[i-1]);
					}
				} 
            
  
                row.add(rowText);
			}
            matrix.add(row);
      
        }
	
		return new DefaultTableModel(matrix, COLUMN_NAME_VECTOR);
	}
	
	
	
	private DefaultTableModel getTableModelMatrix(int[][] results, int[] weights, int max_weight) {
		
		Vector<String> COLUMN_NAME_VECTOR = new Vector<String>();

		for(int i=0; i<max_weight + 2; i++) {
			COLUMN_NAME_VECTOR.add(String.valueOf(i));
		}

		Vector<Vector<String>> matrix = new Vector<Vector<String>>();
		
		for(int i=0; i<weights.length + 2; i++) {
			Vector<String> row = new Vector<String>();
			
			for(int j=0; j<max_weight + 2; j++) {
				String rowText = String.valueOf(results[i][j]) ;
                row.add(rowText);
			}
			
            matrix.add(row);
      
        }
	
		return new DefaultTableModel( matrix, COLUMN_NAME_VECTOR);
	}
	
	private void showItensTaken(JTable table) {
		ArrayList<Point> taken = elControl.getTaken();
		ArrayList<Point> checked = elControl.getChecked();
		
		table.setDefaultRenderer(Object.class, new TableCellRenderer(){
		    private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();

		            @Override
		            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		            	Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		            

		            	if(isInList(taken, row, column)) {  
		            	    c.setBackground(Color.GREEN);  
		            	} else if (isInList(checked, row, column)) {
		            		c.setBackground(Color.RED);
		            	} else {
		            		c.setBackground(Color.WHITE);
		            	}
		                return c;
		            }

		        });
		
	    
	    
	}
	
	private boolean isInList(ArrayList<Point> list, int row, int column) {
		 for(Point point: list) {
         	if (row == point.getX() && column == point.getY()) {
         		return true;
         	}
         }
		 return false;
	}

}

