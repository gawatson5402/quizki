package com.haxwell.apps.questions.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.haxwell.apps.questions.constants.AutocompletionConstants;
import com.haxwell.apps.questions.entities.EntityWithIDAndTextValuePairBehavior;
import com.haxwell.apps.questions.entities.Topic;
import com.haxwell.apps.questions.managers.EntityWithIDAndTextValuePairManager;
import com.haxwell.apps.questions.managers.TopicManager;

public class TopicUtil extends AbstractQuestionAttributeUtil {
	
	private static Logger log = LogManager.getLogger();

	static TopicUtil instance = null;
	
	private TopicUtil() {
		
	}
	
	public static TopicUtil getInstance() {
		if (instance == null)
			instance = new TopicUtil();
		
		return instance;
	}
	
	protected String getKeyToAutocompleteEntriesInTheRequest() {
		return "topicsAutocompleteEntries";
	}

	protected String getKeyToDeletedAutocompleteEntriesInTheRequest() {
		return "topicsDeletedAutocompleteEntries";
	}
	
	protected String getJSONArrayKey() {
		return "topics";
	}
	
	protected long getAutocompletionConstant() {
		return AutocompletionConstants.TOPICS;
	}

	protected EntityWithIDAndTextValuePairManager getManager() {
		return TopicManager.getInstance();
	}
	
	protected EntityWithIDAndTextValuePairBehavior getEntityViaManager(String text) {
		return getManager().getByText(text);
	}
	
	protected EntityWithIDAndTextValuePairBehavior getEntityViaManager(int id) {
		return getManager().getById(id);
	}
	
	protected EntityWithIDAndTextValuePairBehavior getNewEntity() {
		return new Topic();
	}
	
	public Set<Topic> getTopicsFromJSONString(String str) {
		log.entry();
		
		Collection<EntityWithIDAndTextValuePairBehavior> coll = new ArrayList<>();
		
		log.debug("getting topics from json string: " + str);
		
		getObjectsFromJSONString(str, coll);
		
		Set<Topic> rtn = new HashSet<>();
		
		for (EntityWithIDAndTextValuePairBehavior entity : coll) {
			Topic t = new Topic();
			
			t.setId(entity.getId());
			t.setText(entity.getText());
			
			rtn.add(t);
		}
		
		log.exit();
		
		return rtn;
	}
}
