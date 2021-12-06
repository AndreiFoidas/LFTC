package parser;

import domain.Pair;

import java.util.*;

public class OutputTree {
    private TreeNode root;

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode addSibling(TreeNode node, String info, int index, int level){
        if (node == null)
            return null;

        while (node.getRightSibling() != null)
            node = node.getRightSibling();

        TreeNode newSibling = new TreeNode(info);
        newSibling.setLevel(level);
        newSibling.setIndex(index);
        newSibling.setParent(node.getParent());

        node.setRightSibling(newSibling);
        return node.getRightSibling();
    }

    public TreeNode addChild(TreeNode node, String info, int index, int level){
        if (node == null)
            return null;

        if (node.getLeftChild() == null){
            TreeNode newChild = new TreeNode(info);
            newChild.setLevel(level);
            newChild.setIndex(index);
            newChild.setParent(node);

            node.setLeftChild(newChild);
            return node.getLeftChild();
        } else {
            return addSibling(node.getLeftChild(), info, index, level);
        }
    }

    public void createTreeFromSequence(Stack<Integer> inputSequence, Grammar grammar){
        TreeNode lastParent = null;
        int level = 0;
        int index = 0;
        boolean rootHasBeenSet = false;

        while(!inputSequence.empty()) {
            int productionIndex = inputSequence.pop();
            Pair<List<String>,List<String>> productionString = grammar.getProductionOrder().get(productionIndex);

            if (productionIndex == 0 && !rootHasBeenSet){
                this.root = new TreeNode(productionString.getFirst().get(0));
                this.root.setIndex(index);
                this.root.setLevel(level);
                index++;
                level++;
                lastParent = this.root;
                rootHasBeenSet = true;
            }

            for (String symbol: productionString.getSecond()){
                TreeNode node = addChild(lastParent, symbol, index, level);
                if(!inputSequence.empty()){
                    int nextProductionIndex = inputSequence.peek();
                    Pair<List<String>,List<String>> nextProductionString = grammar.getProductionOrder().get(nextProductionIndex);
                    if (symbol.equals(nextProductionString.getFirst().get(0)))
                        lastParent = node;
                }

                index++;
            }

            level++;
        }
    }

    public void printTree(TreeNode node){
        if (node == null)
            return;

        while (node != null) {
            System.out.println(node);
            if (node.getLeftChild() != null) {
                printTree(node.getLeftChild());
            }

            node = node.getRightSibling();
        }
    }
}
