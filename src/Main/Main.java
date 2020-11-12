package Main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import DataStructure.Queue;

public class Main extends JFrame {
	
	// 음료 목록 리스트 나중에 큐로 구현해야됨
	private List<Beverage> beverages;
	
	public static final int PRODUCT_IMAGE_WIDTH = 64;
	public static final int PRODUCT_IMAGE_HEIGHT = 64;
	
	private static final int PRODUCT_START_X = 12;
	private static final int PRODUCT_START_Y = 60;
	public static final int PRODUCT_GAP = 10;
	
	public static final Font DEFAUlT_FONT = new Font("맑은 고딕", Font.PLAIN, 12);
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	public Main() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 402, 490);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 12, 374, 32);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("\uC790\uD310\uAE30");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
//		Stack<Beverage> bs = new Stack<>();
//		bs.push(new Beverage("chamdasu.jpg", "물", 450));
//		bs.push(new Beverage("chamdasu.jpg", "물2", 450));
//		System.out.println(bs.pop().getType());
//		System.out.println(bs.pop().getType());
		
		Queue<Beverage> bq = new Queue<>();
		bq.enqueue(new Beverage("chamdasu.jpg", "물", 450));
		bq.enqueue(new Beverage("chamdasu.jpg", "물2", 450));
		System.out.println(bq.dequeue().getType());
		System.out.println(bq.dequeue().getType());
		
		init();
		
		JLabel item_name;
		JLabel item_price;
		JLabel item_image;
		
		final int ITEM_LABEL_HEIGHT = 15;
		
		for (int i=0; i<beverages.size(); ++i) {
			Beverage b = beverages.get(i);
			JLabel before;
			
			int item_x = (i+1) * 12 + (i * PRODUCT_IMAGE_WIDTH);
			int item_y = PRODUCT_START_Y;
			
			// 음료 이름
			item_name = new JLabel();
			item_name.setText(b.getType());
			item_name.setHorizontalAlignment(SwingConstants.CENTER);
			item_name.setFont(DEFAUlT_FONT);
			item_name.setBounds(item_x, item_y, PRODUCT_IMAGE_WIDTH, ITEM_LABEL_HEIGHT);
			contentPane.add(item_name);
			
			before = item_name;
			item_y += getGap(before);
			
			// 음료 이미지 (이미지가 기본에 어두웠다가 금액 충족 시 밝아짐)
			item_image = new JLabel();
			item_image.setIcon(b.getIcon());
			item_image.setFont(DEFAUlT_FONT);
			item_image.setBounds(item_x, item_y, PRODUCT_IMAGE_WIDTH, PRODUCT_IMAGE_HEIGHT);
			contentPane.add(item_image);
			
			before = item_image;
			item_y += getGap(before);
			
			// 음료 가격
			item_price = new JLabel();
			String price_text = String.valueOf(b.getPrice()) + "원";
			item_price.setText(price_text);
			item_price.setHorizontalAlignment(SwingConstants.CENTER);
			item_price.setFont(DEFAUlT_FONT);
			item_price.setBounds(item_x, item_y, PRODUCT_IMAGE_WIDTH, ITEM_LABEL_HEIGHT);
			contentPane.add(item_price);
			
			before = item_price;
			item_y += getGap(before);
			
			// 구매 버튼 (품절 표시 기능 포함)
			JButton item_buy_btn = new JButton("구매");
			item_buy_btn.setHorizontalAlignment(SwingConstants.CENTER);
			item_buy_btn.setFont(DEFAUlT_FONT);
			item_buy_btn.setBounds(item_x, item_y, PRODUCT_IMAGE_WIDTH, ITEM_LABEL_HEIGHT);
			item_buy_btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.printf("%s: %d원\n", b.getType(), b.getPrice());
				}
			});
			contentPane.add(item_buy_btn);
			
		}
		
		setVisible(true);
	}
	
	private void init() {
		beverages = new ArrayList<>();
		Beverage b;
		String[] paths = new String[] {
			"chamdasu.jpg", "coffee.jpg", "pocari.jpg", "classy_coffee.jpg", "cola.jpg"	
		};
		String[] types = new String[] {
				"물", "커피", "이온음료", "고급커피", "탄산음료"
		};
		Integer[] prices = new Integer[] {
				450, 500, 550, 700, 750
		};
		for (int i=0; i<types.length; ++i) {
			b = new Beverage(paths[i], types[i], prices[i]);
			beverages.add(b);
		}
	}
	
	private int getGap(JLabel before) {
		System.out.println(before.getHeight());
		return PRODUCT_GAP + before.getHeight();
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main main_window = new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
