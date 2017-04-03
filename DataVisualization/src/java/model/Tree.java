/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author poshidi
 * @modified Zhirun
 * @descrip 树节点
 * @time 2015-09-30
 */
public class Tree {

    String id;
    String name;
    Tree parent;
    //add time information
    String avgTime;
    String expertTime;
    String cohortAvgTime;
    String yourTime;
    String userIDs;

    private List<Tree> childs = new ArrayList<Tree>();

    public Tree() {
    }

    public Tree(String id, String name, Tree parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }

    public Tree(String id, String name, Tree parent, String avgTime, String expertTime, String cohortAvgTime, String yourTime, String userIDs) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
        this.avgTime = avgTime;
        this.expertTime = expertTime;
        this.cohortAvgTime = cohortAvgTime;
        this.yourTime = yourTime;
        this.userIDs = userIDs;
    }

    public boolean addChild(Tree Tree) {
        return this.childs.add(Tree);
    }

    public Iterator<Tree> getChildIterator() {
        return childs.iterator();
    }

    public List<Tree> getChilds() {
        return this.childs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tree getParent() {
        return parent;
    }

    public void setParent(Tree parent) {
        this.parent = parent;
    }

    public int level() {
        int result = 0;
        while (this.parent != null) {
            result++;
        }
        return result;
    }

    public boolean hasChilds() {
        return !this.childs.isEmpty();
    }

    public Tree getFirstLeaf() {
        if (this.hasChilds()) {
            return this.childs.get(0).getFirstLeaf();
        }
        return this;
    }

    public Tree getTopParent() {
        if (this.parent == null) {
            return this;
        } else {
            return this.parent.getTopParent();
        }
    }

    public List<Tree> getTreePath() {
        List<Tree> list = new ArrayList<Tree>();
        Tree tmp = this.getParent();
        while (tmp != null) {
            list.add(tmp);
            tmp = tmp.getParent();
        }
        return list;
    }

    public String getStringPath() {
        StringBuffer sb = new StringBuffer();
        List<Tree> tmp = this.getTreePath();
        for (int i = tmp.size() - 1; i >= 0; i--) {
            Tree Tree = tmp.get(i);
            sb.append(Tree.getName() + ".");
        }
        sb.append(this.getName());
        return sb.toString();
    }

    public String getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(String avgTime) {
        this.avgTime = avgTime;
    }

    public String getExpertTime() {
        return expertTime;
    }

    public void setExpertTime(String expertTime) {
        this.expertTime = expertTime;
    }

    public String getCohortAvgTime() {
        return cohortAvgTime;
    }

    public void setCohortAvgTime(String cohortAvgTime) {
        this.cohortAvgTime = cohortAvgTime;
    }

    public String getYourTime() {
        return yourTime;
    }

    public void setYourTime(String yourTime) {
        this.yourTime = yourTime;
    }

    public String getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(String userIDs) {
        this.userIDs = userIDs;
    }

}
