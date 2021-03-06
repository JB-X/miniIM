package client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.alibaba.fastjson.JSON;

import client.ClientManage;
import json.client.session.RemoveFriend;
import json.util.JSONNameandString;

public class GUIRmFriend extends JFrame {

	private static final long serialVersionUID = -6894148656302454256L;
	
	public GUIRmFriend(final DefaultMutableTreeNode root) throws HeadlessException {
		super();

		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int height = screenSize.height;
		int width = screenSize.width;
		setSize(width/8, height/8);
		setLocation(width/8*3, height/8*3);
		setResizable(false);
		setLayout(new BorderLayout());
		setTitle("Add Friend");
		
		JPanel center = new JPanel();
		add(center,BorderLayout.CENTER);
		center.setLayout(new GridLayout(2,2));
		JLabel group = new JLabel("group",JLabel.CENTER);
		center.add(group);
		final JComboBox<String> groupname = new JComboBox<String>();
		
		int len = root.getChildCount();
		for(int i  = 0; i < len ; i++){
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) (root.getChildAt(i));
			groupname.addItem((String)(node.getUserObject()));
		}
		
		center.add(groupname);
		JLabel name = new JLabel("name",JLabel.CENTER);
		center.add(name);
		final JComboBox<String> friendname = new JComboBox<String>();
		center.add(friendname);
		DefaultMutableTreeNode firstchild = (DefaultMutableTreeNode)root.getChildAt(0);
		if(firstchild!=null){
			int length = firstchild.getChildCount();
			for(int i = 0 ; i < length;i++){
				DefaultMutableTreeNode fn = (DefaultMutableTreeNode) firstchild.getChildAt(i);
				friendname.addItem((String)(fn.getUserObject()));
			}
		}
		groupname.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {

				if(e.getStateChange()==ItemEvent.SELECTED){
					friendname.removeAllItems();
					int len = root.getChildCount();
					for(int i  = 0; i < len ; i++){
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) (root.getChildAt(i));
						if(e.getItem().equals((String)(node.getUserObject()))){
							int length = node.getChildCount();
							for(int j = 0 ; j < length;j++){
								DefaultMutableTreeNode n = (DefaultMutableTreeNode) (node.getChildAt(j));
								friendname.addItem((String)(n.getUserObject()));
							}
						}
					}
				}
				friendname.updateUI();

			}
			
		});
		
		JButton enter = new JButton("Enter");
		add(enter,BorderLayout.SOUTH);
		enter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveFriend mf = new RemoveFriend();
				mf.setName((String)(friendname.getSelectedItem()));
				
				JSONNameandString json = new JSONNameandString();
				json.setJSONName(RemoveFriend.class.getName());
				json.setJSONStr(JSON.toJSONString(mf));
				ClientManage.sendJSONNameandString(json);
				dispose();
			}
		});
		
		setVisible(true);
	}
	

}
