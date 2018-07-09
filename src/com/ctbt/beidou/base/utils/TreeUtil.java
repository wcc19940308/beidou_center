package com.ctbt.beidou.base.utils;

import java.util.ArrayList;
import java.util.List;

import com.ctbt.beidou.base.bo.TreeNode;

public class TreeUtil {

//	/**
//	 * 将无序的列表，整理成有层次包含关系的列表
//	 * @param list
//	 * @param topLevel
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<TreeNode> arrangeTree(List<TreeNode> list, int topLevel) throws Exception {
//		List<TreeNode> newList = new ArrayList<TreeNode>();
//		//查找level为1的节点，为一级树节点
//		for(int k = list.size() - 1; k >= 0; k--){
//			if(k >= list.size()){
//				continue;
//			}
//
//			TreeNode node = list.get(k);
//			if(node.getLevel() != null && node.getLevel() == topLevel){
//				list.remove(node);
//				List<TreeNode> childList = TreeUtil.findChild(node, list);
//				node.setChildList(childList);
//				newList.add(node);
//			}
//		}
//
//		return newList;
//	}
//
//	/**
//	 * 从列表中查找出 指定node的子节点们，递推调用，包括孩子的孩子们直到叶节点
//	 * @param node
//	 * @param list
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<TreeNode> findChild(TreeNode node, List<TreeNode> list) throws Exception {
//		List<TreeNode> childList = new ArrayList<TreeNode>();
//		for(int k = list.size() - 1; k >= 0; k--){
//			if(k >= list.size()) continue;//因为会递归调用，list的节点数会减少，而k不会随节点减少而同步变小，因此这里需要判断
//
//			TreeNode child = list.get(k);
//			if(node.getId().equals(child.getParent())){
//				//查找node的子节点
//				list.remove(child);
//				List<TreeNode> subList = TreeUtil.findChild(child, list);
//				child.setChildList(subList);
//				childList.add(child);
//			}
//		}
//
//		return childList;
//	}
//
//	/**
//	 * 根据提供的叶节点列表，从所有的列表中 整理出 包含所有叶节点 到根的一棵树
//	 * @param allList
//	 * @param leafList
//	 * @param topLevel
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<TreeNode> arrangeTree(List<TreeNode> allList, List<TreeNode> leafList, int topLevel) throws Exception {
//		List<TreeNode> forefathersList = new ArrayList<TreeNode>();
//
//		for(TreeNode leafNode : leafList){
//			forefathersList.addAll(findForefathers(allList, leafNode, topLevel));
//			forefathersList.add(leafNode);
//		}
//
//		List<TreeNode> list = arrangeTree(forefathersList, topLevel);
//
//		return list;
//	}
//
//	/**
//	 * 查找一个叶节点 到 根的所有节点,递归调用
//	 * @param allList
//	 * @param leafNode
//	 * @return
//	 * @throws Exception
//	 */
//	public static List<TreeNode> findForefathers(List<TreeNode> allList, TreeNode leafNode, int topLevl) throws Exception {
//		List<TreeNode> forefathersList = new ArrayList<TreeNode>();
//
//		for(int k = allList.size() - 1; k >= 0; k--){
//			if(k >= allList.size()) continue;// 因为会递归调用，list的节点数会减少，而k不会随节点减少而同步变小，因此这里需要判断
//
//			TreeNode node = allList.get(k);
//			if(node.getId().equals(leafNode.getParent())){
//				if(!forefathersList.contains(node)){
//					//还未添加过，则添加
//					forefathersList.add(node);//找到父亲
//
//					allList.remove(node);
//					if(node.getLevel() != null && node.getLevel() > topLevl){
//						//父亲不是跟节点，继续找父亲的祖先们
//						forefathersList.addAll(findForefathers(allList, node, topLevl));
//					}
//
//					break;
//				}
//			}
//		}
//
//		return forefathersList;
//	}
}
