package Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import DataStructure.LinkedList;

public class ItemInfo {
	
	public static final int PRODUCT_IMAGE_WIDTH = 64;
	public static final int PRODUCT_IMAGE_HEIGHT = 64;
	
	private final Vender vender;
	private final JPanel parent;	// ���Ǳ� Panel
	private final Beverage beverage;
	
	private JPanel info_panel;		// ������ Panel
	
	private JLabel item_name;
	private JLabel item_image;
	private JLabel item_price;
	private JButton item_buy_btn;
	
	public ItemInfo(Vender vender, JPanel parent, Beverage beverage) {
		this.vender = vender;
		this.parent = parent;
		this.beverage = beverage;
		init();
	}
	
	/* ���Ǳ� â �� ���� */
	private void init() {
		createInfoPanel();
		parent.add(info_panel);
	}
	
	/* ���� ���� �ݿ��Ͽ� ������ �ٽ� ǥ�� */
	public void update() {
		parent.remove(info_panel);
		createInfoPanel();
		parent.add(info_panel);
	}
	
	public static void updateAll(LinkedList<ItemInfo> infoes) {
		int size = infoes.size();
		Vender vender = null;
		for (int i=0; i<size; ++i) {
			ItemInfo info = infoes.get(i);
			info.update();
			vender = info.vender;
		}
		if (vender != null) {
			vender.revalidate();
			vender.repaint();			
		}
	}
	
	private JPanel createInfoPanel() {
		info_panel = new JPanel();
		info_panel.setLayout(new BoxLayout(info_panel, BoxLayout.Y_AXIS));	// ��ҵ� ���� �����ϱ� ����
		
		// ���� �̸�
		item_name = getNameLabel(beverage);
		
		// ���� �̹��� (�Է� �ݾ� ���� �� �̹��� border ����)
		item_image = getImageLabel(beverage);
		
		// ���� ����
		item_price = getPriceLabel(beverage);
		
		// ���� ��ư (ǰ�� ǥ�� ��� ����)
		item_buy_btn = getBuyButton(beverage);
		
		JComponent[] components = new JComponent[] {
			item_name, item_image, item_price, item_buy_btn	
		};
		
		for (JComponent component : components) {
			component.setAlignmentX(Component.CENTER_ALIGNMENT);
			info_panel.add(component);
		}
		
		return info_panel;
	}
	
	
	/* ���� ���� �޾� ���� �� ���� */
	private JLabel getNameLabel(Beverage b) {
		JLabel item_name = new JLabel();
		item_name.setText(b.getType());
		item_name.setHorizontalAlignment(SwingConstants.CENTER);
		item_name.setFont(Vender.DEFAUlT_FONT);
		return item_name;
	}
	
	/* ���� ���� �޾� �̹��� ���� */
	private JLabel getImageLabel(Beverage b) {
		JLabel item_image = new JLabel();
		
		if (beverage.isExist() && vender.canBuy(beverage)) {
			LineBorder border = new LineBorder(Color.red, 1, true);
			item_image.setBorder(border);
		}
		
		ImageIcon icon = getImageIcon(b.getPath());
		item_image.setIcon(icon);
		return item_image;
	}
	
	/* ���� ���� �޾� ���� �� ���� */
	private JLabel getPriceLabel(Beverage b) {
		JLabel item_price = new JLabel();
		String price_text = String.valueOf(b.getPrice()) + "��";
		item_price.setText(price_text);
		item_price.setHorizontalAlignment(SwingConstants.CENTER);
		item_price.setFont(Vender.DEFAUlT_FONT);
		return item_price;
	}
	
	/* ���� ���� �޾� ���� ��ư ���� */
	private JButton getBuyButton(Beverage b) {
		JButton item_buy_btn = new JButton();
		
		String text;
		if (beverage.isExist())
			text = "����";
		else
			text = "ǰ��";
		item_buy_btn.setText(text);
		
		item_buy_btn.setHorizontalAlignment(SwingConstants.CENTER);
		item_buy_btn.setFont(Vender.DEFAUlT_FONT);
		
		item_buy_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.printf("%s: %d��\n", b.getType(), b.getPrice());
				handleBuyButton();
			}
		});
		
		return item_buy_btn;
	}
	
	private void handleBuyButton() {
		if (beverage.isExist() && vender.canBuy(beverage)) {
			beverage.buy();
			vender.buy(beverage.getPrice());
			System.out.printf("%s �� �Ǹŵƾ�� %d�� ���Ҿ��\n", beverage.getType(), beverage.getAmount());
			updateAll(vender.getItemInfoes());
		}
	}
	
	/* ��ηκ��� ImageIcon �� ������. */
	private ImageIcon getImageIcon(String path) {
		path = String.format("../images/%s", path);
		URL url = this.getClass().getResource(path);
		
		ImageIcon icon = new ImageIcon(url);
		Image img = icon.getImage();
		
		img = img.getScaledInstance(PRODUCT_IMAGE_WIDTH, PRODUCT_IMAGE_HEIGHT, Image.SCALE_SMOOTH);	// ĭ�� ���� �̹��� ������ ����
		icon = new ImageIcon(img);
		
		return icon;
	}
}
