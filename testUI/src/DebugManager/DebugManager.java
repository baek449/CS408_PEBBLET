package DebugManager;

import java.util.ArrayList;

import manager.ComponentManager;
import manager.DefinitionManager;
import manager.RuleManager;

public class DebugManager {
	ArrayList<String> bug_list;
	
	DefinitionManager dm;
	RuleManager rm;
	ComponentManager cm;
	
	public DebugManager(){
		
	}
	
	public DebugManager(DefinitionManager dm_){//for def debug
		dm = dm_;
	}
	
	public ArrayList<String> get_bug_list(){
		return bug_list;
	}

}
